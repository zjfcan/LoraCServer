package com.guina.loratracker.service;

import java.util.ArrayList;
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
import javax.swing.tree.TreeNode;

import org.jboss.logging.Logger;

import com.guina.loratracker.model.Gateway;
import com.guina.loratracker.model.GatewayNode;
import com.guina.loratracker.model.LoraTreeNode;
import com.guina.loratracker.model.MoteNode;
import com.guina.loratracker.model.UpLinkData;

@Singleton
public class LoraDataManager
{
	private Logger logger = Logger.getLogger(this.getClass());

	private Map<String, Set<String>> gatewayEuiToMoteEui = new ConcurrentHashMap<>();
	private Map<String, Set<String>> moteEuiToMoteReportTime = new ConcurrentHashMap<>();
	private Map<String, UpLinkData> reportTimeToUplinkData = new ConcurrentHashMap<>();

	// Gateway EUI to Gateway
	private Map<String, Gateway> euiToGateways = new ConcurrentHashMap<>();

	/**
	 * Default constructor.
	 */
	public LoraDataManager()
	{
	}

//	@Lock(LockType.READ)
	public Map<String, Set<String>> getGatewayEuiToMoteEui()
	{
		return Collections.unmodifiableMap(new HashMap<String, Set<String>>(gatewayEuiToMoteEui));
	}

	@Lock(LockType.WRITE)
	public void setGatewayEuiToMoteEui(Map<String, Set<String>> gatewayEuiToMoteEui)
	{
		this.gatewayEuiToMoteEui = gatewayEuiToMoteEui;
	}

//	@Lock(LockType.READ)
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

//	@Lock(LockType.READ)
	public Map<String, UpLinkData> getReportTimeToUplinkData()
	{
		return Collections.unmodifiableMap(new HashMap<String, UpLinkData>(reportTimeToUplinkData));
	}

	@Lock(LockType.WRITE)
	public void setReportTimeToUplinkData(Map<String, UpLinkData> reportTimeToUplinkData)
	{
		this.reportTimeToUplinkData = reportTimeToUplinkData;
	}

	//@Lock(LockType.READ)
	public Map<String, Gateway> getEuiToGateways()
	{
		return Collections.unmodifiableMap(new HashMap<String, Gateway>(euiToGateways));
	}

	@Lock(LockType.WRITE)
	public void setEuiToGateways(Map<String, Gateway> gateways)
	{
		this.euiToGateways = gateways;
	}

	// ********To be replaced*********************
	private Map<String, GatewayNode> gatewaysCache = new HashMap<String, GatewayNode>();
	private Map<String, MoteNode> motesCache = new HashMap<String, MoteNode>();

	public List<LoraTreeNode> buildTree()
	{
		logger.info("Building tree with map size: "+gatewayEuiToMoteEui.size());
		List<LoraTreeNode> rootNodes = new ArrayList<LoraTreeNode>();
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

			for (String moteEuiHex : gateWayEntry.getValue())
			{
				Set<String> moteReportTimes = moteEuiToMoteReportTime.get(moteEuiHex);
				// for(String reportGatewayTime:moteReportTimes)
				// {
				// UpLinkData upLinkData = reportTimeToUplinkData.get(reportGatewayTime);
				// }

				MoteNode moteNode = motesCache.get(moteEuiHex);
				if (moteNode == null)
				{
					moteNode = new MoteNode();
					moteNode.setName(moteEuiHex);
					moteNode.setParent(gwNode);
					gwNode.getMotes().add(moteNode);
					motesCache.put(moteEuiHex, moteNode);
				}

				moteNode.setMoteHistory(moteReportTimes);
			}
		}

		return rootNodes;
	}

	@Lock(LockType.WRITE)
	public void putEuiToGateways(String longToHex, Gateway gateway)
	{
		euiToGateways.put(longToHex, gateway);
	}

	@Lock(LockType.WRITE)
	public void putGatewayEuiToMoteEui(String gatewayEuiHex, Set<String> moteEuis)
	{
		gatewayEuiToMoteEui.put(gatewayEuiHex, moteEuis);
	}

	@Lock(LockType.WRITE)
	public void putReportTimeToUplinkData(String sentGatewayTime, UpLinkData userMote)
	{
		reportTimeToUplinkData.put(sentGatewayTime, userMote);
	}

	@Lock(LockType.WRITE)
	public void putMoteEuiToMoteReportTime(String moteEuiHex, Set<String> moteReportTimes)
	{
		moteEuiToMoteReportTime.put(moteEuiHex, moteReportTimes);
	}
}