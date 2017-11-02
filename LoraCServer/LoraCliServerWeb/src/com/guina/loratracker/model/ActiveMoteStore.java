package com.guina.loratracker.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ActiveMoteStore implements Serializable
{
	private static final long serialVersionUID = -6104614488313481878L;
	
	@Id
	@GeneratedValue
	private long id;
	private String DevEUI;
	private long DevAddr;
	private long NetID;
	private String APPEUI;
	private String NwkSKey;
	private String APPSKey;
	private long DevNounce;
	private long AppNounce;
	private long DwnSeqNo;
	private Date SysTime;

	public ActiveMoteStore()
	{
		super();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getDevEUI()
	{
		return DevEUI;
	}

	public void setDevEUI(String devEUI)
	{
		DevEUI = devEUI;
	}

	public long getDevAddr()
	{
		return DevAddr;
	}

	public void setDevAddr(long devAddr)
	{
		DevAddr = devAddr;
	}

	public long getNetID()
	{
		return NetID;
	}

	public void setNetID(long netID)
	{
		NetID = netID;
	}

	public String getAPPEUI()
	{
		return APPEUI;
	}

	public void setAPPEUI(String aPPEUI)
	{
		APPEUI = aPPEUI;
	}

	public String getNwkSKey()
	{
		return NwkSKey;
	}

	public void setNwkSKey(String nwkSKey)
	{
		NwkSKey = nwkSKey;
	}

	public String getAPPSKey()
	{
		return APPSKey;
	}

	public void setAPPSKey(String aPPSKey)
	{
		APPSKey = aPPSKey;
	}

	public long getDevNounce()
	{
		return DevNounce;
	}

	public void setDevNounce(long devNounce)
	{
		DevNounce = devNounce;
	}

	public long getAppNounce()
	{
		return AppNounce;
	}

	public void setAppNounce(long appNounce)
	{
		AppNounce = appNounce;
	}

	public long getDwnSeqNo()
	{
		return DwnSeqNo;
	}

	public void setDwnSeqNo(long dwnSeqNo)
	{
		DwnSeqNo = dwnSeqNo;
	}

	public Date getSysTime()
	{
		return SysTime;
	}

	public void setSysTime(Date sysTime)
	{
		SysTime = sysTime;
	}

}
