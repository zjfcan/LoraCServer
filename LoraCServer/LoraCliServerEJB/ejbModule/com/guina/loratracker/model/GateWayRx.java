package com.guina.loratracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateWayRx
{
	@JsonProperty(required = true)
	private long eui;
	@JsonProperty(required = true)
	private String time;// UTC
	@JsonProperty(required = true)
	private boolean timeFromGateway;
	@JsonProperty(required = true)
	private long chan;
	@JsonProperty(required = true)
	private int rfch;
	@JsonProperty(required = true)
	private float rssi;
	@JsonProperty(required = true)
	private float lsnr;

	public long getEui()
	{
		return eui;
	}

	public void setEui(long eui)
	{
		this.eui = eui;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public boolean isTimeFromGateway()
	{
		return timeFromGateway;
	}

	public void setTimeFromGateway(boolean timeFromGateway)
	{
		this.timeFromGateway = timeFromGateway;
	}

	public long getChan()
	{
		return chan;
	}

	public void setChan(long chan)
	{
		this.chan = chan;
	}

	public int getRfch()
	{
		return rfch;
	}

	public void setRfch(int rfch)
	{
		this.rfch = rfch;
	}

	public float getRssi()
	{
		return rssi;
	}

	public void setRssi(float rssi)
	{
		this.rssi = rssi;
	}

	public float getLsnr()
	{
		return lsnr;
	}

	public void setLsnr(float lsnr)
	{
		this.lsnr = lsnr;
	}

}
