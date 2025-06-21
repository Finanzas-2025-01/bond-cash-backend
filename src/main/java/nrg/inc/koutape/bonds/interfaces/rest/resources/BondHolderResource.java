package nrg.inc.koutape.bonds.interfaces.rest.resources;

public record BondHolderResource(
        Long id,
        String username,
        String name,
        String surname,
        String email
) {
}
