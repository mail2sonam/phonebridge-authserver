package com.phonebridge.iam.db.v1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonebridge.iam.db.v1.repository.AccountAssocRepository;
import com.phonebridge.iam.db.v1.repository.AccountRepository;
import com.phonebridge.iam.db.v1.row.DBRowAccount;
import com.phonebridge.iam.exception.InvalidAccountException;
import com.phonebridge.iam.exception.InvalidParameterException;
import com.phonebridge.iam.exception.RecordNotFoundException;
import com.phonebridge.iam.row.AccountRow;
import com.phonebridge.iam.service.IAccountService;
import com.phonebridge.iam.utils.CommonUtils;

@Service
public class AccountDBServiceImpl implements IAccountService {
	private static final String NO_RECORDS_FOUND = "Record Not Found";

	@Autowired
	AccountRepository accRepo; // Account repository reference
	
	@Autowired
	AccountAssocRepository accAssocRepo;

	/**
	 * To get the list of accounts.
	 */
	
	  @Override 
	  public List<AccountRow> findAll() { 
		  List<DBRowAccount> accountList = accRepo.findAll(); 
		  return Collections.emptyList(); //dbAccountMapper.dbRowAccountLstToAccountRowLst(accountList); 
	  }
	 

	/**
	 * To get account details by account id
	 * 
	 * @param accountId
	 * @return
	 */
	@Override
	public AccountRow findById(String accountId) {
		if (CommonUtils.isBlankOrNull(accountId)) {
			throw new InvalidParameterException("invalid accountId : " + accountId);
		}

		Optional<DBRowAccount> account = accRepo.findById(accountId);
		if (!account.isPresent()) {
			throw new InvalidAccountException("Invalid Account id");
		}
		return account.get().toAccountRow();
	}

	/**
	 * To get account details by account id
	 * 
	 * @param accountId
	 * @return
	 */
	@Override
	public AccountRow findByAccountName(String accountName) {
		if (CommonUtils.isBlankOrNull(accountName)) {
			throw new InvalidParameterException("invalid accountId : " + accountName);
		}
		Optional<DBRowAccount> account = accRepo.findByAccountName(accountName);
		if (!account.isPresent()) {
			throw new InvalidAccountException("Invalid AccountName");
		}
		return account.get().toAccountRow();
	}

	/**
	 * To create account
	 * 
	 * @param accountRow
	 * @return
	 */
	@Override
	public AccountRow createAccount(AccountRow accountRow) {
		if (CommonUtils.isBlankOrNull(accountRow)) {
			throw new InvalidParameterException("invalid accountDto : " + accountRow);
		}

		DBRowAccount dbAccount = null;//dbAccountMapper.accountRowToAccountDBRow(accountRow);
		DBRowAccount accountPersisted = accRepo.save(dbAccount);
		return null;//dbAccountMapper.dbRowAccountToAccountRow(accountPersisted);
	}

	/**
	 * To update the account
	 * 
	 * @param accountId
	 * @param accountRow
	 * @return AccountDTO
	 */
	@Override
	public AccountRow updateAccount(AccountRow accountRow) {

		if (CommonUtils.isBlankOrNull(accountRow)) {
			throw new InvalidParameterException("invalid accountDto : " + accountRow);
		}
		if (CommonUtils.isBlankOrNull(accountRow.getAccountName())) {
			throw new InvalidParameterException("invalid accountId : " + accountRow.getAccountName());
		}

		DBRowAccount accountPassed = null;//dbAccountMapper.accountRowToAccountDBRow(accountRow);
		DBRowAccount result = accRepo.save(accountPassed);
		if (null == result) {
			throw new RecordNotFoundException(NO_RECORDS_FOUND);
		}
		return null;//dbAccountMapper.dbRowAccountToAccountRow(result);
	}

	@Override
	public void deleteAccountByAccountId(String accountId) {
		// TODO Auto-generated method stub
	}


	/**
	 * Check if the requested user has permissions to do perform action on the requested account 
	 * @param account
	 * @param requestedByUserName
	 * @return true/false
	 */
	@Override
	public boolean isUserHasPermissionToOperateOnAccountUsers(AccountRow account, String requestedByAccName) {
		if(requestedByAccName==null) {
			return false;
		}
		if(requestedByAccName.startsWith(account.getAccountName())) {
			return true;
		}
		return accAssocRepo.findByChildAccountName(account.getAccountName()).contains(requestedByAccName);
	}

}
