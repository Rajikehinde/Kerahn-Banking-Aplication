package com.kerahnBankingApplication.kerahnBankingApplication.repository;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles,Long> {
}
