package com.guina.loratracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoteTx
{
	@JsonProperty(required = true)
	private float freq;
	@JsonProperty(required = true)
	private String modu;
	@JsonProperty(required = true)
	private String datr;
	@JsonProperty(required = false)
	private String codr;
	@JsonProperty(required = true)
	private boolean adr;

	public float getFreq()
	{
		return freq;
	}

	public void setFreq(float freq)
	{
		this.freq = freq;
	}

	public String getModu()
	{
		return modu;
	}

	public void setModu(String modu)
	{
		this.modu = modu;
	}

	public String getDatr()
	{
		return datr;
	}

	public void setDatr(String datr)
	{
		this.datr = datr;
	}

	public String getCodr()
	{
		return codr;
	}

	public void setCodr(String codr)
	{
		this.codr = codr;
	}

	public boolean isAdr()
	{
		return adr;
	}

	public void setAdr(boolean adr)
	{
		this.adr = adr;
	}

}
