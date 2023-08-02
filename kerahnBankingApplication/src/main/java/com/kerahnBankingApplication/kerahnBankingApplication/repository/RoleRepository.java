package com.kerahnBankingApplication.kerahnBankingApplication.repository;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRoleName(String roleName);
}
