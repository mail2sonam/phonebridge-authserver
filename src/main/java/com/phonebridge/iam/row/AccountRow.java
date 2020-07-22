package com.phonebridge.iam.row;

import java.util.List;

import lombok.Data;

@Data
public class AccountRow extends BasePojo{
	
	private String accountName;
	
	private Boolean isLocked;
	
	private Boolean isDeleted;
	
	private List<AccountRow> parentAccounts;
	
	private List<AccountRow> childAccounts;
}
