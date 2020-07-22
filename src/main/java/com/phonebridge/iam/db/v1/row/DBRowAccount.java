package com.phonebridge.iam.db.v1.row;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.phonebridge.iam.row.AccountRow;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="account")
public class DBRowAccount extends AuditBaseEntity{

	@Id
	private String accountName;
	
	private Boolean isLocked;
	
	private Boolean isDeleted;
	
	public AccountRow toAccountRow() {
		AccountRow res=new AccountRow();
		BeanUtils.copyProperties(this, res);
		return res;
	}
}
