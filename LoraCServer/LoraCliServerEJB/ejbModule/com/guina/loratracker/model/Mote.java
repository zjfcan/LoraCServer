package com.guina.loratracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mote
{
	@JsonProperty(required = true)
	private long eui;
	private boolean seqNoReq;
	private int seqnogrant;
	private boolean app;
	private boolean sentStatus;
	private short msgSent;
	private short ackRx;

	public long getEui()
	{
		return eui;
	}

	public void setEui(long eui)
	{
		this.eui = eui;
	}

	public boolean isSeqNoReq()
	{
		return seqNoReq;
	}

	public void setSeqNoReq(boolean seqNoReq)
	{
		this.seqNoReq = seqNoReq;
	}

	public int getSeqnogrant()
	{
		return seqnogrant;
	}

	public void setSeqnogrant(int seqnogrant)
	{
		this.seqnogrant = seqnogrant;
	}

	public boolean isApp()
	{
		return app;
	}

	public void setApp(boolean app)
	{
		this.app = app;
	}

	public boolean isSentStatus()
	{
		return sentStatus;
	}

	public void setSentStatus(boolean sentStatus)
	{
		this.sentStatus = sentStatus;
	}

	public short getMsgSent()
	{
		return msgSent;
	}

	public void setMsgSent(short msgSent)
	{
		this.msgSent = msgSent;
	}

	public short getAckRx()
	{
		return ackRx;
	}

	public void setAckRx(short ackRx)
	{
		this.ackRx = ackRx;
	}

	@Override
	public String toString()
	{
		return "Mote [eui=" + eui + ", seqNoReq=" + seqNoReq + ", seqnogrant=" + seqnogrant
						+ ", app=" + app + ", sentStatus=" + sentStatus + ", msgSent=" + msgSent
						+ ", ackRx=" + ackRx + "]";
	}
}
