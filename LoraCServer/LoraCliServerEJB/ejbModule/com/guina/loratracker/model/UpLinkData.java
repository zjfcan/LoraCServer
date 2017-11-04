package com.guina.loratracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpLinkData
{
	@JsonProperty(required = true)
	private long moteEui;
	@JsonProperty(required = true)
	private String dir;
	private int token;
	private boolean confirmed;
	private UserData userData;
	private List<GateWayRx> gwrx;
	private int loraClass;
	private int sessionID;
	private int TxUTCtime;
	

	public long getMoteEui()
	{
		return moteEui;
	}

	public void setMoteEui(long moteEui)
	{
		this.moteEui = moteEui;
	}

	public String getDir()
	{
		return dir;
	}

	public void setDir(String dir)
	{
		this.dir = dir;
	}

	public int getToken()
	{
		return token;
	}

	public void setToken(int token)
	{
		this.token = token;
	}

	public boolean isConfirmed()
	{
		return confirmed;
	}

	public void setConfirmed(boolean confirmed)
	{
		this.confirmed = confirmed;
	}

	public UserData getUserData()
	{
		return userData;
	}

	public void setUserData(UserData userData)
	{
		this.userData = userData;
	}

	public List<GateWayRx> getGwrx()
	{
		return gwrx;
	}

	public void setGwrx(List<GateWayRx> gwrx)
	{
		this.gwrx = gwrx;
	}

	public int getLoraClass()
	{
		return loraClass;
	}

	public void setLoraClass(int loraClass)
	{
		this.loraClass = loraClass;
	}

	public int getSessionID()
	{
		return sessionID;
	}

	public void setSessionID(int sessionID)
	{
		this.sessionID = sessionID;
	}

	public int getTxUTCtime()
	{
		return TxUTCtime;
	}

	public void setTxUTCtime(int txUTCtime)
	{
		TxUTCtime = txUTCtime;
	}
}
