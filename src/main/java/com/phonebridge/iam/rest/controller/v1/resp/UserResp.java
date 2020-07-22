package com.phonebridge.iam.rest.controller.v1.resp;

import lombok.Data;

@Data
public class UserResp {

	private String id;
	
	private String accountId;
	
	private String accountName;

	private String userName;

	private String grantedAuthorities;

}
