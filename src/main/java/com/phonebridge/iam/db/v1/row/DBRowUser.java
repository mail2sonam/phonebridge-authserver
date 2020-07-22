package com.phonebridge.iam.db.v1.row;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.phonebridge.iam.row.UserRow;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class DBRowUser extends AuditBaseEntity{
	
	@Id
	private String username;
	
	private String password;
	
	private Boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "username")
	private List<DBRowAuthorities> authorities;
	
	public UserRow toUserRow() {
		UserRow userRow = new UserRow();
		BeanUtils.copyProperties(this, userRow);
		return userRow;
	}
	
	public DBRowUser(UserRow userRow){
		BeanUtils.copyProperties(userRow,this);
	}
}
