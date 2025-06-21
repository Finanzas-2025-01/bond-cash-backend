package nrg.inc.koutape.bonds.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.HireBondCommand;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondByIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHolderByUsernameQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetIssuerByUsernameQuery;
import nrg.inc.koutape.bonds.domain.services.BondCommandService;
import nrg.inc.koutape.bonds.domain.services.BondHolderQueryService;
import nrg.inc.koutape.bonds.domain.services.BondQueryService;
import nrg.inc.koutape.bonds.domain.services.IssuerQueryService;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondResource;
import nrg.inc.koutape.bonds.interfaces.rest.resources.CreateBondResource;
import nrg.inc.koutape.bonds.interfaces.rest.resources.HiredBondResource;
import nrg.inc.koutape.bonds.interfaces.rest.transform.BondResourceFromEntityAssembler;
import nrg.inc.koutape.bonds.interfaces.rest.transform.CreateBondCommandFromResourceAssembler;
import nrg.inc.koutape.bonds.interfaces.rest.transform.HiredBondResourceFromEntityAssembler;
import nrg.inc.koutape.iam.domain.services.UserQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/bonds")
@Tag(name = "Bond", description = "Bond management API")
public class BondController {
    private final BondCommandService bondCommandService;
    private final BondQueryService bondQueryService;
    private final BondHolderQueryService bondHolderQueryService;
    private final IssuerQueryService issuerQueryService;
    public BondController(BondCommandService bondCommandService, BondQueryService bondQueryService, BondHolderQueryService bondHolderQueryService, IssuerQueryService issuerQueryService) {
        this.bondCommandService = bondCommandService;
        this.bondQueryService = bondQueryService;
        this.bondHolderQueryService = bondHolderQueryService;
        this.issuerQueryService = issuerQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a bond", description = "Create a bond")
    public ResponseEntity<BondResource> createBond(@RequestBody CreateBondResource createBondResource, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var issuer = issuerQueryService.handle(new GetIssuerByUsernameQuery(username));
        if (issuer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var issuerId = issuer.get().getId();
        var createdBond = CreateBondCommandFromResourceAssembler.toCommandFromResource(createBondResource,issuerId);
        var bond = bondCommandService.handle(createdBond);
        if (bond.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(bond.get());

        return new ResponseEntity<>(bondResource, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{bondId}/hire/")
    @Operation(summary = "Hire a bond", description = "Hire a bond by bondId and bondHolderId")
    public ResponseEntity<HiredBondResource> hireBond(@PathVariable Long bondId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var bondHolder = bondHolderQueryService.handle(new GetBondHolderByUsernameQuery(username));
        if (bondHolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondHolderId = bondHolder.get().getId();
        var hireBondCommand = new HireBondCommand(bondId, bondHolderId);
        var bond = bondCommandService.handle(hireBondCommand);
        if (bond.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var hiredBondResource = HiredBondResourceFromEntityAssembler.toResourceFromEntity(bond.get(), bondHolder.get());
        return ResponseEntity.ok(hiredBondResource);
    }

    @GetMapping("/{bondId}")
    @Operation(summary = "Get bond by ID", description = "Retrieve a bond by its ID")
    public ResponseEntity<BondResource> getBondById(@PathVariable Long bondId) {
        var getBondByIdQuery = new GetBondByIdQuery(bondId);
        var bond = bondQueryService.handle(getBondByIdQuery);
        if (bond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(bond.get());
        return ResponseEntity.ok(bondResource);
    }
}
