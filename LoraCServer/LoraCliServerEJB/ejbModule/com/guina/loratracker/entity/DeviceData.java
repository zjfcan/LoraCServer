package com.guina.loratracker.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DeviceData implements Serializable
{
	private static final long serialVersionUID = -8783580684066030679L;
	
	@Id
	@GeneratedValue
	private long id;
	private String EUI;
	private String HType;
	private Date Time;
	private String Node;
	private String GateWay;
	private String RFchan;
	private String Chan;
	private float Freq;
	private String BW;
	private String ADR;
	private String SF;
	private float RSSI;
	private float SNR;
	private int Port;
	private Date SysTime;

	public DeviceData()
	{
		super();
	}

	public String getEUI()
	{
		return EUI;
	}

	public void setEUI(String eUI)
	{
		EUI = eUI;
	}

	public String getHType()
	{
		return HType;
	}

	public void setHType(String hType)
	{
		HType = hType;
	}

	public Date getTime()
	{
		return Time;
	}

	public void setTime(Date time)
	{
		Time = time;
	}

	public String getNode()
	{
		return Node;
	}

	public void setNode(String node)
	{
		Node = node;
	}

	public String getGateWay()
	{
		return GateWay;
	}

	public void setGateWay(String gateWay)
	{
		GateWay = gateWay;
	}

	public String getRFchan()
	{
		return RFchan;
	}

	public void setRFchan(String rFchan)
	{
		RFchan = rFchan;
	}

	public String getChan()
	{
		return Chan;
	}

	public void setChan(String chan)
	{
		Chan = chan;
	}

	public float getFreq()
	{
		return Freq;
	}

	public void setFreq(float freq)
	{
		Freq = freq;
	}

	public String getBW()
	{
		return BW;
	}

	public void setBW(String bW)
	{
		BW = bW;
	}

	public String getADR()
	{
		return ADR;
	}

	public void setADR(String aDR)
	{
		ADR = aDR;
	}

	public String getSF()
	{
		return SF;
	}

	public void setSF(String sF)
	{
		SF = sF;
	}

	public float getRSSI()
	{
		return RSSI;
	}

	public void setRSSI(float rSSI)
	{
		RSSI = rSSI;
	}

	public float getSNR()
	{
		return SNR;
	}

	public void setSNR(float sNR)
	{
		SNR = sNR;
	}

	public int getPort()
	{
		return Port;
	}

	public void setPort(int port)
	{
		Port = port;
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
