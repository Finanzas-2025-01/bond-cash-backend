package nrg.inc.koutape.bonds.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsByIssuerIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllHiredBondsByIssuerIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetIssuerByUsernameQuery;
import nrg.inc.koutape.bonds.domain.services.IssuerQueryService;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondResource;
import nrg.inc.koutape.bonds.interfaces.rest.resources.HiredBondResource;
import nrg.inc.koutape.bonds.interfaces.rest.transform.BondResourceFromEntityAssembler;
import nrg.inc.koutape.bonds.interfaces.rest.transform.HiredBondResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/issuers")
@Tag(name = "Issuer", description = "Issuer management API")
public class IssuerController {
    private final IssuerQueryService issuerQueryService;

    public IssuerController(IssuerQueryService issuerQueryService) {
        this.issuerQueryService = issuerQueryService;
    }

    @GetMapping(value = "/bonds")
    @Operation(summary = "Get all bonds issued by the issuer", description = "Retrieve all bonds issued by the issuer")
    public ResponseEntity<List<BondResource>> getAllBondsByIssuer(@AuthenticationPrincipal UserDetails userDetails) {
        String issuerUsername = userDetails.getUsername();
        var issuer = issuerQueryService.handle(new GetIssuerByUsernameQuery(issuerUsername));
        if (issuer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var issuerId = issuer.get().getId();
        var bonds = issuerQueryService.handle(new GetAllBondsByIssuerIdQuery(issuerId));
        var bondResources = bonds.stream()
                .map(BondResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bondResources);
    }

    @GetMapping(value = "/bonds/hired")
    @Operation(summary = "Get all bonds issued by the issuer", description = "Retrieve all bonds issued by the issuer")
    public ResponseEntity<List<HiredBondResource>> getAllHiredBondsByIssuer(@AuthenticationPrincipal UserDetails userDetails) {
        String issuerUsername = userDetails.getUsername();
        var issuer = issuerQueryService.handle(new GetIssuerByUsernameQuery(issuerUsername));
        if (issuer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var issuerId = issuer.get().getId();
        var bonds = issuerQueryService.handle(new GetAllHiredBondsByIssuerIdQuery(issuerId));
        var bondResources = bonds.stream()
                .map(bond -> HiredBondResourceFromEntityAssembler.toResourceFromEntity(bond, bond.getBondholder()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bondResources);
    }
}
