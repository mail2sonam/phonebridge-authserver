package com.phonebridge.iam.rest.controller.v1.resp;

import lombok.Data;

@Data
public class AccountResp {

	private String id;

	private String accountName;
	
	private String parentAccountId;
	
}
