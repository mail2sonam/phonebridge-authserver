package com.phonebridge.iam.db.v1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phonebridge.iam.db.v1.repository.AuthoritiesRepository;
import com.phonebridge.iam.db.v1.repository.UserRepository;
import com.phonebridge.iam.db.v1.row.DBRowAuthorities;
import com.phonebridge.iam.db.v1.row.DBRowUser;
import com.phonebridge.iam.exception.InvalidAccountException;
import com.phonebridge.iam.exception.InvalidParameterException;
import com.phonebridge.iam.exception.RecordNotFoundException;
import com.phonebridge.iam.exception.UserAlreadyExistsException;
import com.phonebridge.iam.row.AccountRow;
import com.phonebridge.iam.row.UserRow;
import com.phonebridge.iam.service.IAccountService;
import com.phonebridge.iam.service.IUserService;
import com.phonebridge.iam.utils.CommonUtils;

@Service
public class UserDBServicev1Impl implements IUserService {

	private static final String NO_RECORDS_FOUND = "Record Not Found";
	private static final String STR_ROLE_PREFIX="ROLE_";
	
	@Autowired
	private UserRepository userRepository; // User repository

	@Autowired
	private IAccountService accountService; // Account Repository

	@Autowired
	private AuthoritiesRepository userAuthRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * To create user
	 * @param userRow
	 * @return created user details
	 */
	@Transactional
	@Override
	public UserRow createUser(UserRow userRow,String requestedByUserName){
		if (CommonUtils.isBlankOrNull(userRow)) {
			throw new InvalidParameterException("invalid userDto : " + userRow);
		}
		userRow.setUsername(userRow.getAccount().getAccountName()+"::"+userRow.getUsername());
		if (userRepository.findByUsername(userRow.getUsername()) != null) {
			throw new UserAlreadyExistsException("User id is not available.");
		}
		userRow.setPassword(passwordEncoder.encode(userRow.getPassword()));
		AccountRow accRow = accountService.findByAccountName(userRow.getAccount().getAccountName());
		return Optional.ofNullable(accRow)
			.map(a -> {
				if(!accountService.isUserHasPermissionToOperateOnAccountUsers(userRow.getAccount()
						,CommonUtils.getAccNameFromUserName(requestedByUserName))) {
					throw new InvalidAccountException("Requested Account Not Authorised");
				}
				userRow.setAccount(a);
				userRow.setEnabled(true);
				DBRowUser userPersisted = userRepository.save(new DBRowUser(userRow));
				for(String eachAuth:userRow.getGrantedAuthorities()) {
					DBRowAuthorities dbAuth = new DBRowAuthorities();
					dbAuth.setAuthority(STR_ROLE_PREFIX+eachAuth);
					dbAuth.setUsername(userRow.getUsername());
					userAuthRepo.save(dbAuth);
				}
				return userPersisted.toUserRow();		
				})
			.orElseThrow(() -> new InvalidAccountException("Account not Found"));
	}


	/**
	 * To Update user details
	 * @param accountId
	 * @param userId
	 * @param userRow
	 * @return updated user details
	 */
	@Override
	public UserRow updateUser(UserRow userRow) {

		if (CommonUtils.isBlankOrNull(userRow)) {
			throw new InvalidParameterException("invalid userDto : " + userRow);
		}
		if (CommonUtils.isBlankOrNull(userRow.getAccount().getAccountName())) {
			throw new InvalidParameterException("invalid accountName : " + userRow.getAccount().getAccountName());
		}

		DBRowUser fetchedDbUser = null;//userRepository.findByIdAndAccountId(userRow.getId(), userRow.getAccountId());
		if (fetchedDbUser == null) {
			throw new RecordNotFoundException(NO_RECORDS_FOUND);
		}

		//DBRowUser givenDbUser = dbUserMapper.userRowtoDBUserRow(userRow);
		//DBRowUser result = userRepository.save(givenDbUser);
		return null;//dbUserMapper.dbUserRowtoUserRow(result);
	}

	/**
	 * To delete a user
	 *
	 * @param id
	 * @return details of the deleted user
	 *//*
		 * @Override public void deleteUserByAccountIdAndUserId(ObjectId accountId,
		 * String userId) { if (CommonUtils.isBlankOrNull(accountId)) { throw new
		 * InvalidParameterException("invalid accountId : " + accountId); }
		 * 
		 * if (CommonUtils.isBlankOrNull(userId)) { throw new
		 * InvalidParameterException("invalid userId : " + userId); }
		 * 
		 * User userReturned = userRepository.findByAccountIdAndUserName(accountId,
		 * userId); if (userReturned == null) { throw new
		 * RecordNotFoundException(NO_RECORDS_FOUND); }
		 * 
		 * userRepository.delete(userReturned); }
		 */

	/**
	 * To get the list of users.
	 */
	@Override
	public List<UserRow> findAll() {
		List<DBRowUser> userList = userRepository.findAll();
		//return dbUserMapper.dbUserRowLstToUserRowList(userList);
		return Collections.emptyList();
	}

	/**
	 * To get the list of users by Account Id.
	 */
	@Override
	public List<UserRow> findAllByAccountId(String accountId) {
		if (CommonUtils.isBlankOrNull(accountId)) {
			throw new InvalidParameterException("invalid accountId : " + accountId);
		}

		//List<DBRowUser> dbUserList = userRepository.findByAccountId(accountId);
		//return dbUserMapper.dbUserRowLstToUserRowList(dbUserList);
		return Collections.emptyList();
	}

	public UserRow getUserByUserNameAndAccountName(String userName, String accountName) {
		return null;//DBUserUtils.convertDBRowUserToUser(userRepository.findByAccountNameAndUserName(accountName, userName));
	}

}
