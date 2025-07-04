package nrg.inc.koutape.iam.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.commands.CreateIssuerCommand;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondHolderCommand;
import nrg.inc.koutape.bonds.domain.services.IssuerCommandService;
import nrg.inc.koutape.bonds.domain.services.BondHolderCommandService;
import nrg.inc.koutape.iam.application.internal.outboundservices.hashing.HashingService;
import nrg.inc.koutape.iam.application.internal.outboundservices.tokens.TokenService;
import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.iam.domain.model.commands.CreateUserIssuerCommand;
import nrg.inc.koutape.iam.domain.model.commands.CreateUserBondHolderCommand;
import nrg.inc.koutape.iam.domain.model.commands.SignInCommand;
import nrg.inc.koutape.iam.domain.model.commands.SignUpCommand;
import nrg.inc.koutape.iam.domain.services.UserCommandService;
import nrg.inc.koutape.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import nrg.inc.koutape.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

  private final UserRepository userRepository;
  private final HashingService hashingService;
  private final TokenService tokenService;
  private final BondHolderCommandService investorCommandService;
  private final IssuerCommandService bondHolderCommandService;
  private final RoleRepository roleRepository;

  public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService,
                                TokenService tokenService, BondHolderCommandService investorCommandService, IssuerCommandService bondHolderCommandService, RoleRepository roleRepository) {

    this.userRepository = userRepository;
    this.hashingService = hashingService;
    this.tokenService = tokenService;
      this.investorCommandService = investorCommandService;
      this.bondHolderCommandService = bondHolderCommandService;
      this.roleRepository = roleRepository;
  }

  /**
   * Handle the sign-in command
   * <p>
   *     This method handles the {@link SignInCommand} command and returns the user and the token.
   * </p>
   * @param command the sign-in command containing the username and password
   * @return and optional containing the user matching the username and the generated token
   * @throws RuntimeException if the user is not found or the password is invalid
   */
  @Override
  public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
    var user = userRepository.findByUsername(command.username());
    if (user.isEmpty())
      throw new RuntimeException("User not found");
    if (!hashingService.matches(command.password(), user.get().getPassword()))
      throw new RuntimeException("Invalid password");

    var token = tokenService.generateToken(user.get().getUsername());
    return Optional.of(ImmutablePair.of(user.get(), token));
  }

  /**
   * Handle the sign-up command
   * <p>
   *     This method handles the {@link SignUpCommand} command and returns the user.
   * </p>
   * @param command the sign-up command containing the username and password
   * @return the created user
   */
  @Override
  public Optional<User> handle(SignUpCommand command) {
    if (userRepository.existsByUsername(command.username()))
      throw new RuntimeException("Username already exists");
    var roles = command.roles().stream()
        .map(role ->
            roleRepository.findByName(role.getName())
                .orElseThrow(() -> new RuntimeException("Role name not found")))
        .toList();
    var user = new User(
            command.username(),
            command.name(),
            command.surname(),
            command.email(),
            hashingService.encode(command.password()),
            roles);
    userRepository.save(user);
    return userRepository.findByUsername(command.username());
  }

  @Override
  public Optional<User> handle(CreateUserBondHolderCommand command) {
    var userId = command.userId();
    if (userRepository.findById(userId).isEmpty()) {
      throw new RuntimeException("User not found");
    }
    var user = userRepository.findById(userId).get();
    var investor = investorCommandService.handle(new CreateBondHolderCommand());
    if (investor.isEmpty()) {
      throw new RuntimeException("Bond holder creation failed");
    }
    user.setBondHolder(investor.get());
    investor.get().setUser(user);
    try{
     var savedUser = userRepository.save(user);
      return Optional.of(savedUser);
    } catch (Exception e) {
      throw new RuntimeException("User creation failed: " + e.getMessage());
    }
  }

  @Override
  public Optional<User> handle(CreateUserIssuerCommand command) {
    var userId = command.userId();
    if (userRepository.findById(userId).isEmpty()) {
      throw new RuntimeException("User not found");
    }
    var user = userRepository.findById(userId).get();
    var bondHolder = bondHolderCommandService.handle(new CreateIssuerCommand());
    if (bondHolder.isEmpty()) {
      throw new RuntimeException("Issuer creation failed");
    }
    user.setIssuer(bondHolder.get());
    bondHolder.get().setUser(user);
    try {
      var savedUser = userRepository.save(user);
      return Optional.of(savedUser);
    } catch (Exception e) {
      throw new RuntimeException("User creation failed: " + e.getMessage());
    }
  }
}
