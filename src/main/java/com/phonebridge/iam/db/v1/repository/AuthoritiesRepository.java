package com.phonebridge.iam.db.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phonebridge.iam.db.v1.row.DBRowAuthorities;

@Repository
public interface AuthoritiesRepository extends JpaRepository<DBRowAuthorities, String> {
		
}
