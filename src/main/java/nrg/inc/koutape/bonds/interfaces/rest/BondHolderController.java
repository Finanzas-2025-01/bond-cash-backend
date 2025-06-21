package nrg.inc.koutape.bonds.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsByBondHolderIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHolderByUsernameQuery;
import nrg.inc.koutape.bonds.domain.services.BondHolderQueryService;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondHolderResource;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondResource;
import nrg.inc.koutape.bonds.interfaces.rest.transform.BondResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/bond-holders")
@Tag(name = "Bond Holders", description = "Bond Holder management API")
public class BondHolderController {
    private final BondHolderQueryService bondHolderQueryService;

    public BondHolderController(BondHolderQueryService bondHolderQueryService) {
        this.bondHolderQueryService = bondHolderQueryService;
    }

    @GetMapping(value = "/bonds")
    @Operation(summary = "Get all bonds", description = "Retrieve all bonds of a bond holder")
    public ResponseEntity<List<BondResource>> getAllBondHolders(@AuthenticationPrincipal UserDetails userdetails) {
        String username = userdetails.getUsername();
        var bondHolder = bondHolderQueryService.handle(new GetBondHolderByUsernameQuery(username));
        if (bondHolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondHolderId = bondHolder.get().getId();
        var bondHolders = this.bondHolderQueryService.handle(new GetAllBondsByBondHolderIdQuery(bondHolderId));
        var bondHolderResources = bondHolders.stream()
                .map(BondResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bondHolderResources);
    }
}
