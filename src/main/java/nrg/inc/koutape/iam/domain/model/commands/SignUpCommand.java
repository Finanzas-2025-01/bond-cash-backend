package nrg.inc.koutape.iam.domain.model.commands;

import nrg.inc.koutape.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
