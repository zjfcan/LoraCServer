package com.guina.loratracker.model;

import java.io.Serializable;

public class LoraLoginJsn implements Serializable
{
	private static final long serialVersionUID = 5658285666649553543L;
	
	private LoginAccount account;
	
	public LoginAccount getAccount()
	{
		return account;
	}

	public void setAccount(LoginAccount account)
	{
		this.account = account;
	}

	@Override
	public String toString()
	{
		return "LoraLoginJsn [account=" + account + "]";
	}
}
