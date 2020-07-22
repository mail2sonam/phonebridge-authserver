package com.phonebridge.iam.rest.controller.v1.req;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.phonebridge.iam.row.AccountRow;
import com.phonebridge.iam.row.UserRow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Phone Bridge user data transaction object
 *
 * @author Vinoth Manoharan
 * @version 1.0
 * @since 06-March-2020
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
public class NewUserReq extends BaseRequest{

	// accountname - homedecors 
	@JsonProperty("accountName")
	private String accountName;

	// username - sonamuthu
	@JsonProperty("userName")
	private String username;

	// password
	@Size(message = "password size should be between 3 and 30", min = 3, max = 30)
	@JsonProperty("password")
	private String password;

	// privilege
	@JsonProperty("grantedAuthorities")
	@Valid
	private List<String> grantedAuthorities;

	public UserRow toUserRow() {
		UserRow uRow=new UserRow();
		BeanUtils.copyProperties(this, uRow);
		AccountRow aRow = new AccountRow();
		aRow.setAccountName(this.accountName);
		uRow.setAccount(aRow);
		return uRow;
	}
	
}
