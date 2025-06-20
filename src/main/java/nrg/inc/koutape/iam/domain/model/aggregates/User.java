package nrg.inc.koutape.iam.domain.model.aggregates;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.aggregates.Investor;
import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

  @NotBlank
  @Size(max = 50)
  @Column(unique = true)
  private String username;

  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(	name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @Nullable
  @Setter
  @OneToOne
  @JoinColumn(name = "bond_holder_id")
  private BondHolder bondHolder;

  @Nullable
  @Setter
  @OneToOne
  @JoinColumn(name = "investor_id")
  private Investor investor;

  public User() {
    this.roles = new HashSet<>();
  }

  public User(
          String username,
          String name,
          String surname,
          String email,
          String password) {
    this.username = username;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.roles = new HashSet<>();
  }

  public User(String username,
              String name,
              String surname,
              String email,
              String password,
              List<Role> roles) {
    this(
            username,
            name,
            surname,
            email,
            password);
    addRoles(roles);
  }

  /**
   * Add a role to the user
   * @param role the role to add
   * @return the user with the added role
   */
  public User addRole(Role role) {
    this.roles.add(role);
    return this;
  }

  /**
   * Add a list of roles to the user
   * @param roles the list of roles to add
   * @return the user with the added roles
   */
  public User addRoles(List<Role> roles) {
    var validatedRoleSet = Role.validateRoleSet(roles);
    this.roles.addAll(validatedRoleSet);
    return this;
  }
}