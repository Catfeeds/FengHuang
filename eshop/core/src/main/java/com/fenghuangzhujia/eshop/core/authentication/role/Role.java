package com.fenghuangzhujia.eshop.core.authentication.role;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fenghuangzhujia.eshop.core.authentication.authority.AbstractAuthority;
import com.fenghuangzhujia.eshop.core.user.User;
import com.fenghuangzhujia.foundation.core.entity.UUIDBaseModel;

@Entity
@Table(name="fhzj_role")
public class Role extends UUIDBaseModel {
	
	private String name;
	private String description;
	private Set<AbstractAuthority> authorities;
	private Set<User> users;
	
	@Column(unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	
	@ManyToMany
	@JoinTable
	public Set<AbstractAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<AbstractAuthority> authorities) {
		this.authorities = authorities;
	}
	
	@ManyToMany
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}	
	
	//数据传输属性
	private String[] userids;
	private String[] authorityids;

	@Transient
	public String[] getUserids() {
		return userids;
	}
	public void setUserids(String[] userids) {
		this.userids = userids;
	}
	
	@Transient
	public String[] getAuthorityids() {
		return authorityids;
	}
	public void setAuthorityids(String[] authorityids) {
		this.authorityids = authorityids;
	}
}
