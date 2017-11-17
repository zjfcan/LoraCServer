package com.guina.loratracker.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

import com.guina.loratracker.model.ActiveMoteNode;
import com.guina.loratracker.model.Gateway;
import com.guina.loratracker.model.GatewayNode;
import com.guina.loratracker.model.LoraTreeNode;
import com.guina.loratracker.model.Mote;
import com.guina.loratracker.model.MoteNode;
import com.guina.loratracker.model.UpLinkData;
import com.guina.loratracker.util.EncodeUtil;

@Singleton
public class LoraDataManager
{
	private Logger logger = Logger.getLogger(this.getClass());

	private Map<String, Set<String>> gatewayEuiToMoteEui = new ConcurrentHashMap<>();
	private Map<String, Set<String>> moteEuiToMoteReportTime = new ConcurrentHashMap<>();
	private Map<String, UpLinkData> reportTimeToUplinkData = new ConcurrentHashMap<>();

	// Gateway EUI to Gateway
	private Map<String, Gateway> euiToGateways = new ConcurrentHashMap<>();

	// Mote EUI to Motes
	private Map<String, Mote> euiToMotes = new ConcurrentHashMap<>();

	/**
	 * Default constructor.
	 */
	public LoraDataManager()
	{
	}

	// @Lock(LockType.READ)
	public Map<String, Set<String>> getGatewayEuiToMoteEui()
	{
		return Collections.unmodifiableMap(new HashMap<String, Set<String>>(gatewayEuiToMoteEui));
	}

	@Lock(LockType.WRITE)
	public void setGatewayEuiToMoteEui(Map<String, Set<String>> gatewayEuiToMoteEui)
	{
		this.gatewayEuiToMoteEui = gatewayEuiToMoteEui;
	}

	// @Lock(LockType.READ)
	public Map<String, Set<String>> getMoteEuiToMoteReportTime()
	{
		return Collections
						.unmodifiableMap(new HashMap<String, Set<String>>(moteEuiToMoteReportTime));
	}

	@Lock(LockType.WRITE)
	public void setMoteEuiToMoteReportTime(Map<String, Set<String>> moteEuiToMoteReportTime)
	{
		this.moteEuiToMoteReportTime = moteEuiToMoteReportTime;
	}

	// @Lock(LockType.READ)
	public Map<String, UpLinkData> getReportTimeToUplinkData()
	{
		return Collections.unmodifiableMap(new HashMap<String, UpLinkData>(reportTimeToUplinkData));
	}

	@Lock(LockType.WRITE)
	public void setReportTimeToUplinkData(Map<String, UpLinkData> reportTimeToUplinkData)
	{
		this.reportTimeToUplinkData = reportTimeToUplinkData;
	}

	// @Lock(LockType.READ)
	public Map<String, Gateway> getEuiToGateways()
	{
		return Collections.unmodifiableMap(new HashMap<String, Gateway>(euiToGateways));
	}

	@Lock(LockType.WRITE)
	public void setEuiToGateways(Map<String, Gateway> gateways)
	{
		this.euiToGateways = gateways;
	}

	public Map<String, Mote> getEuiToMotes()
	{
		return euiToMotes;
	}

	public void setEuiToMotes(Map<String, Mote> euiToMotes)
	{
		this.euiToMotes = euiToMotes;
	}

	// ********To be replaced*********************
	private Map<String, GatewayNode> gatewaysCache = new HashMap<>();
	private Map<String, MoteNode> motesCache = new HashMap<>();
	private Map<String, ActiveMoteNode> activeMotesCache = new HashMap<>();

	public List<LoraTreeNode> buildTree(List<LoraTreeNode> rootNodes)
	{
		logger.info("Building tree with map size: " + gatewayEuiToMoteEui.size());
		if(rootNodes.size()==0)
		{
			rootNodes.addAll(gatewaysCache.values());
		}
		
		for (Entry<String, Set<String>> gateWayEntry : gatewayEuiToMoteEui.entrySet())
		{
			String gatewayEuiHex = gateWayEntry.getKey();
			Gateway gateway = euiToGateways.get(gatewayEuiHex);
			if (gateway == null)
			{
				logger.warn("Received gateway that has not registered with coordinates.");
				continue;
			}

			GatewayNode gwNode = gatewaysCache.get(gatewayEuiHex);
			if (gwNode == null)
			{
				gwNode = new GatewayNode();
				gwNode.setName(gatewayEuiHex);
				gatewaysCache.put(gatewayEuiHex, gwNode);
				rootNodes.add(gwNode);
			}
			gwNode.setGateway(gateway);

			logger.info("Add Gateway " + gatewayEuiHex);
			for (String moteEuiHex : gateWayEntry.getValue())
			{
				MoteNode moteNode = motesCache.get(moteEuiHex);
				if (moteNode == null)
				{
					moteNode = new MoteNode();
					moteNode.setName(moteEuiHex);
					moteNode.setParent(gwNode);
					gwNode.getMotes().add(moteNode);
					motesCache.put(moteEuiHex, moteNode);
				}
				logger.info("Add Mote " + moteEuiHex);

				if ("3353011811818605".equals(moteEuiHex))
				{
					Set<String> moteReportTimes = moteEuiToMoteReportTime.get(moteEuiHex);
					logger.info("Found our Mote " + moteEuiHex + " with times number = "
									+ moteReportTimes.size());
					for (String reportGatewayTime : moteReportTimes)
					{
						// TODO to prevent null from being stored
						if (reportGatewayTime == null)
						{
							continue;
						}
						UpLinkData upLinkData = reportTimeToUplinkData.get(reportGatewayTime);

						ActiveMoteNode activeMoteNode = activeMotesCache.get(reportGatewayTime);
						if (activeMoteNode == null)
						{
							activeMoteNode = new ActiveMoteNode();
							activeMoteNode.setName(moteEuiHex);
							activeMoteNode.setParent(moteNode);
							activeMoteNode.setTime(reportGatewayTime);
							activeMotesCache.put(reportGatewayTime, activeMoteNode);
						}

						String payload = upLinkData.getUserData().getPayload();
						byte[] payLoadBytes = EncodeUtil.base64Decode(payload);

//						logger.info("Mote " + moteEuiHex + " at " + reportGatewayTime + ": payload="
//										+ payload + " with length = " + payLoadBytes.length);
//						StringBuilder sb = new StringBuilder();
//						for (byte aByte : payLoadBytes)
//						{
//							sb.append(String.format("%02X ", aByte));
//						}
//						logger.info("Decoded payload: " + sb.toString());

						if (payLoadBytes.length > 13)
						{
							float lat = getLatitudeFromEv302Payload(payLoadBytes);
							logger.info("Mote " + moteEuiHex + " at " + reportGatewayTime + ": lat="
											+ String.valueOf(lat) + " and Hex = "
											+ String.format("%02X %02X %02X ", payLoadBytes[8],
															payLoadBytes[9], payLoadBytes[10]));

							activeMoteNode.setLat(lat);

							float lng = getLongitudeFromEv302Payload(payLoadBytes);
							logger.info("Mote " + moteEuiHex + " at " + reportGatewayTime + ": lng="
											+ String.valueOf(lng) + " and Hex = "
											+ String.format("%02X %02X %02X ", payLoadBytes[11],
															payLoadBytes[12], payLoadBytes[13]));

							activeMoteNode.setLng(lng);
						}

						moteNode.getTrace().add(activeMoteNode);
					}
				}
			}
		}

		return rootNodes;
	}

	private float getLongitudeFromEv302Payload(byte[] payLoadBytes)
	{
		int lngHex = 0xff << 24 | ((payLoadBytes[11] & 0xff) << 16)
						| ((payLoadBytes[12] & 0xff) << 8) | (payLoadBytes[13] & 0xff);
		float lng = -(0xffffffff - lngHex + 1) * 180f / 0x800000;
		return lng;
	}

	private float getLatitudeFromEv302Payload(byte[] payLoadBytes)
	{
		int latHex = 0x00 << 24 | ((payLoadBytes[8] & 0xff) << 16) | ((payLoadBytes[9] & 0xff) << 8)
						| (payLoadBytes[10] & 0xff);
		float lat = latHex * 90f / 0x800000;
		return lat;
	}

	@Lock(LockType.WRITE)
	public void putEuiToGateways(String gatewayEuiHex, Gateway gateway)
	{
		if (gatewayEuiHex != null && gateway != null)
		{
			euiToGateways.put(gatewayEuiHex, gateway);
		}
	}

	@Lock(LockType.WRITE)
	public void putGatewayEuiToMoteEui(String gatewayEuiHex, Set<String> moteEuis)
	{
		if (gatewayEuiHex != null && moteEuis != null)
		{
			gatewayEuiToMoteEui.put(gatewayEuiHex, moteEuis);
		}
	}

	@Lock(LockType.WRITE)
	public void putReportTimeToUplinkData(String sentGatewayTime, UpLinkData userMote)
	{
		if (sentGatewayTime != null && userMote != null)
		{
			reportTimeToUplinkData.put(sentGatewayTime, userMote);
		}
	}

	@Lock(LockType.WRITE)
	public void putMoteEuiToMoteReportTime(String moteEuiHex, Set<String> moteReportTimes)
	{
		if (moteEuiHex != null && moteReportTimes != null)
		{
			moteEuiToMoteReportTime.put(moteEuiHex, moteReportTimes);
		}
	}

	@Lock(LockType.WRITE)
	public void putEuiToMote(String moteEui, Mote mote)
	{
		if (moteEui != null && mote != null)
		{
			euiToMotes.put(moteEui, mote);
		}
	}
}