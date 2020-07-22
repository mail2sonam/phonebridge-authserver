package com.phonebridge.iam.service;

import java.util.List;

import com.phonebridge.iam.row.AccountRow;

public interface IAccountService {

	
	/**
	 * To get the list of accounts.
	 * 
	 * @return AccountDTO list
	 */
	List<AccountRow> findAll();
	
	/**
	 * To get account details by account id
	 * 
	 * @param accountId
	 * @return AccountDTO
	 */
	AccountRow findById(String id);
	
	/**
	 * To create account
	 * 
	 * @param accountDto
	 * @return AccountDTO
	 */
	AccountRow createAccount(AccountRow accountDto);
	
	/**
	 * To update the account
	 * 
	 * @param accountId
	 * @param accountDto
	 * @return AccountDTO
	 */
	AccountRow updateAccount(AccountRow accountDto);

	/**
	 * To delete account by account id
	 * 	
	 * @param accountId
	 */
	void deleteAccountByAccountId(String accountId);

	AccountRow findByAccountName(String accountName);

	/**
	 * Check if the requested user has permissions to do perform action on the requested account 
	 * @param account
	 * @param requestedByUserName
	 * @return true/false
	 */
	boolean isUserHasPermissionToOperateOnAccountUsers(AccountRow account, String requestedByAccName);
}
