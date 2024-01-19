package tamir.ma.tamir.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tamir.ma.tamir.entity.Role;
import tamir.ma.tamir.entity.User;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}