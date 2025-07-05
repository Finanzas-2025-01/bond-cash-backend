package nrg.inc.koutape.bonds.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.koutape.bonds.domain.model.commands.GenerateCashFlowsByBondIdCommand;
import nrg.inc.koutape.bonds.domain.model.commands.HireBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.UpdateGracePeriodByPeriodNumberAndBondIdCommand;
import nrg.inc.koutape.bonds.domain.model.queries.*;
import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;
import nrg.inc.koutape.bonds.domain.services.BondCommandService;
import nrg.inc.koutape.bonds.domain.services.BondHolderQueryService;
import nrg.inc.koutape.bonds.domain.services.BondQueryService;
import nrg.inc.koutape.bonds.domain.services.IssuerQueryService;
import nrg.inc.koutape.bonds.interfaces.rest.resources.*;
import nrg.inc.koutape.bonds.interfaces.rest.transform.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        var generateCashFlowsCommand = new GenerateCashFlowsByBondIdCommand(bond.get().getId());
        bondCommandService.handle(generateCashFlowsCommand);

        if (bond.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(bond.get());

        return new ResponseEntity<>(bondResource, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{bondId}/hire")
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
        var generateCashFlowsCommand = new GenerateCashFlowsByBondIdCommand(bond.get().getId());
        bondCommandService.handle(generateCashFlowsCommand);
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

    @GetMapping
    @Operation(summary = "Get all bonds", description = "Retrieve all bonds")
    public ResponseEntity<List<BondResource>> getAllBonds() {
        var bonds = bondQueryService.handle(new GetAllBondsQuery());
        var bondResources = bonds.stream()
                .map(BondResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(bondResources);
    }

    @GetMapping("/{bondId}/cashFlows")
    @Operation(summary = "Get cash flows by bond ID", description = "Retrieve cash flows for a specific bond by its ID")
    public ResponseEntity<List<CashFlowResource>> getCashFlowsByBondId(@PathVariable Long bondId) {
        var bond = bondQueryService.handle(new GetBondByIdQuery(bondId));
        if (bond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        /*
        var generateCashFlowsCommand = new GenerateCashFlowsByBondIdCommand(bondId);
        bondCommandService.handle(generateCashFlowsCommand);
        */

        var cashFlows = bondQueryService.handle(new GetCashFlowsByBondIdQuery(bondId));

        var cashFlowResources = cashFlows.stream()
                .map(CashFlowResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(cashFlowResources);
    }

    @PatchMapping(value = "/{bondId}/cashFlows/{periodNumber}/gracePeriod")
    @Operation(summary = "Update grace period by period number and bond ID", description = "Update the grace period for a specific cash flow period of a bond")
    public ResponseEntity<Void> updateGracePeriodByPeriodNumberAndBondId(
            @PathVariable Long bondId,
            @PathVariable Integer periodNumber,
            @RequestBody GracePeriod gracePeriod) {
        var command = new UpdateGracePeriodByPeriodNumberAndBondIdCommand(bondId, periodNumber, gracePeriod);
        bondCommandService.handle(command);
        var generateCashFlowsCommand = new GenerateCashFlowsByBondIdCommand(bondId);
        bondCommandService.handle(generateCashFlowsCommand);
        return ResponseEntity.noContent().build();
    }

    /*
    @GetMapping("{bondId}/holders")
    @Operation(summary = "Get bond holders by bond ID", description = "Retrieve all bond holders for a specific bond by its ID")
    public ResponseEntity<List<BondHolderResource>> getBondHoldersByBondId(@PathVariable Long bondId) {
        var bond = bondQueryService.handle(new GetBondByIdQuery(bondId));
        if (bond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondHolders = bondQueryService.handle(new GetBondHoldersByBondIdQuery(bondId));
        var bondHolderResources = bondHolders.stream()
                .map(BondHolderResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(bondHolderResources);
    }
    */

    @PutMapping("/{bondId}")
    @Operation(summary = "Update bond", description = "Update a bond by its ID")
    public ResponseEntity<BondResource> updateBond(
            @PathVariable Long bondId,
            @RequestBody UpdateBondResource updateBondResource) {
        var updateBondCommand = UpdateBondCommandFromResourceAssembler.toCommandFromResource(updateBondResource, bondId);
        bondCommandService.handle(updateBondCommand);
        var updatedBond = bondQueryService.handle(new GetBondByIdQuery(bondId));
        if (updatedBond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var generateCashFlowsCommand = new GenerateCashFlowsByBondIdCommand(updatedBond.get().getId());
        bondCommandService.handle(generateCashFlowsCommand);
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(updatedBond.get());
        return ResponseEntity.ok(bondResource);
    }
}
