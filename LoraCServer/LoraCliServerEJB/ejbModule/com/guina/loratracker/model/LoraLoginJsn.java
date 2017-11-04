package com.guina.loratracker.model;

import java.io.Serializable;

public class LoraLoginJsn implements Serializable
{
	private static final long serialVersionUID = -2113765134775339672L;
	private LoginAccount account;

	public LoginAccount getAccount()
	{
		return account;
	}

	public void setAccount(LoginAccount account)
	{
		this.account = account;
	}
}
