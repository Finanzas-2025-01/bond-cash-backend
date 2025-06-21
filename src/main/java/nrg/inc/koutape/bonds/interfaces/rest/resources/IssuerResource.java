package nrg.inc.koutape.bonds.interfaces.rest.resources;

public record IssuerResource(
        Long id,
        String username,
        String name,
        String surname,
        String email
) {
}
