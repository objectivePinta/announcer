package net.uranus.astra.auth.model.repository;

import net.uranus.astra.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import net.uranus.astra.auth.model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
