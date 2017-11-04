package com.guina.loratracker.model;

import java.io.Serializable;

public class LoginAccount implements Serializable
{
	private static final long serialVersionUID = 5712624075339104437L;
	private String user;
	private long APPEUI;
	private String customer;
	private String pwd;
	private boolean DwnData;
	private boolean accept;
	private boolean serverAccept;
	private String serverUser;
	private String serverPwd;
	private String serverDB;

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public long getAPPEUI()
	{
		return APPEUI;
	}

	public void setAPPEUI(long aPPEUI)
	{
		APPEUI = aPPEUI;
	}

	public String getCustomer()
	{
		return customer;
	}

	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public boolean isDwnData()
	{
		return DwnData;
	}

	public void setDwnData(boolean dwnData)
	{
		DwnData = dwnData;
	}

	public boolean isAccept()
	{
		return accept;
	}

	public void setAccept(boolean accept)
	{
		this.accept = accept;
	}

	public boolean isServerAccept()
	{
		return serverAccept;
	}

	public void setServerAccept(boolean serverAccept)
	{
		this.serverAccept = serverAccept;
	}

	public String getServerUser()
	{
		return serverUser;
	}

	public void setServerUser(String serverUser)
	{
		this.serverUser = serverUser;
	}

	public String getServerPwd()
	{
		return serverPwd;
	}

	public void setServerPwd(String serverPwd)
	{
		this.serverPwd = serverPwd;
	}

	public String getServerDB()
	{
		return serverDB;
	}

	public void setServerDB(String serverDB)
	{
		this.serverDB = serverDB;
	}

	@Override
	public String toString()
	{
		return "LoginAccount [user=" + user + ", APPEUI=" + APPEUI + ", customer=" + customer
						+ ", pwd=" + pwd + ", DwnData=" + DwnData + ", accept=" + accept
						+ ", serverAccept=" + serverAccept + ", serverUser=" + serverUser
						+ ", serverPwd=" + serverPwd + ", serverDB=" + serverDB + "]";
	}
}
