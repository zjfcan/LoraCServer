package com.guina.loratracker.model;

public class Gateway
{
	private long EUI;
	private long APPEUI;
	private long protocolVersion;
	private long gatewayProtocol;
	private long sessionID;
	private String IPAddr;
	private byte PortPULL;
	private byte Port;
	private long protocolversion;
	private GatewayStatus Status;
	private String SysTime;
	
//	{"gateway":{"EUI":721177472077463560,"APPEUI":0,"protocolversion":1,"gatewayprotocol":0,"sessionID":0,"IPAddr":"183.129.47.89",
//		"PortPULL":4500,"Port":4500,"Status":
//		{"time":"1970-01-07 18:47:47 GMT","lati":0.0,"Long":0.0,"alti":0,"rxnb":1,"rxok":1,"rxfw":0,"ackr":3,"dwnb":0,"txnb":0},
//		"SysTime":"2017-11-03T09:19:44.7474912+08:00"}}
}
