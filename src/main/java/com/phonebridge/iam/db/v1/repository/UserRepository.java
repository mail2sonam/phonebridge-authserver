package com.phonebridge.iam.db.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phonebridge.iam.db.v1.row.DBRowUser;

/**
 * Phone Bridge user repository
 *
 * @author Vinoth Manoharan
 * @version 1.0
 * @since 06-March-2020
 */

@Repository
public interface UserRepository extends JpaRepository<DBRowUser, String> {

	/**
	 * To get user by UserName
	 * 
	 * @param userName
	 * @return
	 */
	DBRowUser findByUsername(String userName);


}
