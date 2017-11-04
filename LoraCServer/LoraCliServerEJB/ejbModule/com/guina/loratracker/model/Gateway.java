package com.guina.loratracker.model;

public class Gateway
{
	// {"gateway":{"EUI":721177472077463560,"APPEUI":0,"protocolversion":1,"gatewayprotocol":0,"sessionID":0,"IPAddr":"183.129.47.89",
	// "PortPULL":4500,"Port":4500,"Status":
	// {"time":"1970-01-07 18:47:47 GMT","lati":0.0,"Long":0.0,"alti":0,"rxnb":1,"rxok":1,"rxfw":0,"ackr":3,"dwnb":0,"txnb":0},
	// "SysTime":"2017-11-03T09:19:44.7474912+08:00"}}

	private long EUI;
	private long APPEUI;
	private long protocolVersion;
	private long gatewayProtocol;
	private long sessionID;
	private String IPAddr;
	private int PortPULL;
	private int Port;
	private long protocolversion;
	private GatewayStatus Status;
	private String SysTime;

	public long getEUI()
	{
		return EUI;
	}

	public void setEUI(long eUI)
	{
		EUI = eUI;
	}

	public long getAPPEUI()
	{
		return APPEUI;
	}

	public void setAPPEUI(long aPPEUI)
	{
		APPEUI = aPPEUI;
	}

	public long getProtocolVersion()
	{
		return protocolVersion;
	}

	public void setProtocolVersion(long protocolVersion)
	{
		this.protocolVersion = protocolVersion;
	}

	public long getGatewayProtocol()
	{
		return gatewayProtocol;
	}

	public void setGatewayProtocol(long gatewayProtocol)
	{
		this.gatewayProtocol = gatewayProtocol;
	}

	public long getSessionID()
	{
		return sessionID;
	}

	public void setSessionID(long sessionID)
	{
		this.sessionID = sessionID;
	}

	public String getIPAddr()
	{
		return IPAddr;
	}

	public void setIPAddr(String iPAddr)
	{
		IPAddr = iPAddr;
	}

	public int getPortPULL()
	{
		return PortPULL;
	}

	public void setPortPULL(int portPULL)
	{
		PortPULL = portPULL;
	}

	public int getPort()
	{
		return Port;
	}

	public void setPort(int port)
	{
		Port = port;
	}

	public long getProtocolversion()
	{
		return protocolversion;
	}

	public void setProtocolversion(long protocolversion)
	{
		this.protocolversion = protocolversion;
	}

	public GatewayStatus getStatus()
	{
		return Status;
	}

	public void setStatus(GatewayStatus status)
	{
		Status = status;
	}

	public String getSysTime()
	{
		return SysTime;
	}

	public void setSysTime(String sysTime)
	{
		SysTime = sysTime;
	}
}
