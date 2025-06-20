package nrg.inc.koutape.iam.domain.services;

import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.iam.domain.model.commands.CreateUserBondHolderCommand;
import nrg.inc.koutape.iam.domain.model.commands.CreateUserInvestorCommand;
import nrg.inc.koutape.iam.domain.model.commands.SignInCommand;
import nrg.inc.koutape.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
  Optional<User> handle(SignUpCommand command);
  Optional<User> handle(CreateUserInvestorCommand command);
  Optional<User> handle(CreateUserBondHolderCommand command);
}
