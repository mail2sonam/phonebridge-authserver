package com.phonebridge.iam.db.v1.row;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="account_assoc")
public class DBRowAccountAssoc extends AuditBaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String id;
	
	private String parentAccountName;
	
	private Boolean childAccountName;
		
}
