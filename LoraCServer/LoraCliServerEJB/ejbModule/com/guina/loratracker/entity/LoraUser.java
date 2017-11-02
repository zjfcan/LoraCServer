package com.guina.loratracker.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoraUser implements Serializable
{
	private static final long serialVersionUID = -2307370913834271865L;

	@Id
	private String username;
	private String userRole;
	private String userGroup;
	private String password;

	public LoraUser()
	{
		super();
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserRole()
	{
		return this.userRole;
	}

	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}

	public String getUserGroup()
	{
		return this.userGroup;
	}

	public void setUserGroup(String userGroup)
	{
		this.userGroup = userGroup;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "LoraUser{username:" + username + ", userRole:" + userRole + ", userGroup:"
						+ userGroup + "}";
	}
}
