package com.phonebridge.iam.row;

import java.util.List;

import lombok.Data;

@Data
public class UserRow  extends BasePojo{
	private AccountRow account;
	private String username;
	private String password;
	private List<String> grantedAuthorities;
	private Boolean enabled;
}