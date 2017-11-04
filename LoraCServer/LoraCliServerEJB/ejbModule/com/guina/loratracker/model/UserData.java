package com.guina.loratracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData
{
	@JsonProperty(required = true)
	private int seqno;
	@JsonProperty(required = true)
	private byte port;
	@JsonProperty(required = true)
	private String payload;
	@JsonProperty(required = true)
	private MoteTx motetx;

	public int getSeqno()
	{
		return seqno;
	}

	public void setSeqno(int seqno)
	{
		this.seqno = seqno;
	}

	public byte getPort()
	{
		return port;
	}

	public void setPort(byte port)
	{
		this.port = port;
	}

	public String getPayload()
	{
		return payload;
	}

	public void setPayload(String payload)
	{
		this.payload = payload;
	}

	public MoteTx getMotetx()
	{
		return motetx;
	}

	public void setMotetx(MoteTx motetx)
	{
		this.motetx = motetx;
	}
}
