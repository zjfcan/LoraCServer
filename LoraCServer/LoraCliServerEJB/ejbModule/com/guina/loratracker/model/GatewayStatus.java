package com.guina.loratracker.model;

public class GatewayStatus
{
//	{"time":"1970-01-07 18:47:47 GMT","lati":0.0,"Long":0.0,"alti":0,"rxnb":1,"rxok":1,"rxfw":0,"ackr":3,"dwnb":0,"txnb":0},
	
	private String time;
	private float lati;
	private float Long;
	private float alti;
	private long rxnb;
	private long rxok;
	private long rxfw;
	private long ackr;
	private long dwnb;
	private long txnb;
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public float getLati()
	{
		return lati;
	}
	public void setLati(float lati)
	{
		this.lati = lati;
	}
	public float getLong()
	{
		return Long;
	}
	public void setLong(float l)
	{
		Long = l;
	}
	public float getAlti()
	{
		return alti;
	}
	public void setAlti(float alti)
	{
		this.alti = alti;
	}
	public long getRxnb()
	{
		return rxnb;
	}
	public void setRxnb(long rxnb)
	{
		this.rxnb = rxnb;
	}
	public long getRxok()
	{
		return rxok;
	}
	public void setRxok(long rxok)
	{
		this.rxok = rxok;
	}
	public long getRxfw()
	{
		return rxfw;
	}
	public void setRxfw(long rxfw)
	{
		this.rxfw = rxfw;
	}
	public long getAckr()
	{
		return ackr;
	}
	public void setAckr(long ackr)
	{
		this.ackr = ackr;
	}
	public long getDwnb()
	{
		return dwnb;
	}
	public void setDwnb(long dwnb)
	{
		this.dwnb = dwnb;
	}
	public long getTxnb()
	{
		return txnb;
	}
	public void setTxnb(long txnb)
	{
		this.txnb = txnb;
	}
}
