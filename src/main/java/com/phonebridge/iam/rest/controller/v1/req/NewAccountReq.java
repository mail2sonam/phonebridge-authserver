package com.phonebridge.iam.rest.controller.v1.req;

import java.util.Collections;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.phonebridge.iam.row.AccountRow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phone Bridge account data transaction object
 *
 * @author Vinoth Manoharan
 * @version 1.0
 * @since 08-March-2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("account")
public class NewAccountReq {

	@NotEmpty
	@Size(message="accountName size should be between 3 and 50",min=3,max=50)
	@JsonProperty("accountName")
	private String accountName;
	
	@NotEmpty
	@Size(message="accountName size should be between 3 and 50",min=3,max=50)
	private String parentAccountName;

	
	public AccountRow toAccountRow() {
		AccountRow ac= new AccountRow();
		ac.setAccountName(accountName);
		AccountRow parentac= new AccountRow();
		parentac.setAccountName(parentAccountName);
		ac.setParentAccounts(Collections.singletonList(parentac));
		return ac;
	}
}
