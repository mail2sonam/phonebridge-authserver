package com.phonebridge.iam.db.v1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phonebridge.iam.db.v1.row.DBRowAccountAssoc;

@Repository
public interface AccountAssocRepository extends JpaRepository<DBRowAccountAssoc, String> {
	
	public List<AccountAssocRepository> findByParentAccountName(String parentAccountName);
	
	public List<AccountAssocRepository> findByChildAccountName(String childAccountName);
	
}
