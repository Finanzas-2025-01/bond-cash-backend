package nrg.inc.koutape.iam.domain.services;


import nrg.inc.koutape.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
