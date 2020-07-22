package com.phonebridge.iam.db.v1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phonebridge.iam.db.v1.row.DBRowAccount;

/**
 * Phone Bridge account repository
 *
 * @author Vinoth Manoharan
 * @version 1.0
 * @since 06-March-2020
 */

@Repository
public interface AccountRepository extends JpaRepository<DBRowAccount, String> {
	
	public Optional<DBRowAccount> findByAccountName(String accountName);
	
}
