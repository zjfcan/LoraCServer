package com.guina.loratracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.guina.loratracker.util.JsonUtils;

public class MoteNode extends NamedNode implements LoraTreeNode
{
	private static final long serialVersionUID = -8509649794114670371L;
	private GatewayNode parent;
	private Mote mote;
	private List<ActiveMoteNode> trace = new ArrayList<>();

	public MoteNode()
	{
		super();
		this.setType("Mote");
	}

	@Override
	public TreeNode getChildAt(int childIndex)
	{
		return trace.get(childIndex);
	}

	@Override
	public int getChildCount()
	{
		return trace.size();
	}

	@Override
	public TreeNode getParent()
	{
		return parent;
	}

	public void setParent(GatewayNode gateway)
	{
		this.parent = gateway;
	}

	@Override
	public int getIndex(TreeNode node)
	{
		return trace.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren()
	{
		return true;
	}

	@Override
	public boolean isLeaf()
	{
		return trace.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children()
	{
		return Collections.enumeration(trace);
	}

	public Mote getMote()
	{
		return mote;
	}

	public void setMote(Mote mote)
	{
		this.mote = mote;
	}

	public List<ActiveMoteNode> getTrace()
	{
		return trace;
	}

	public void setTrace(List<ActiveMoteNode> trace)
	{
		this.trace = trace;
	}

	@Override
	public String getDisplayContents()
	{
		return JsonUtils.convertJsonToString(getGoogleMapMarker());
	}

	@Override
	public GoogleMapMarker getGoogleMapMarker()
	{
		float lat = 0.0f;
		float lng = 0.0f;
		ActiveMoteNode activeMoteNode = (trace == null || trace.isEmpty()) ? null : trace.get(0);
		if (activeMoteNode != null)
		{
			lat = activeMoteNode.getLat();
			lng = activeMoteNode.getLng();
		}
		GoogleMapPosition markerPosition = new GoogleMapPosition(lat, lng);
		String contents = getType() + "=" + getName() + "<br/>" + lat + "," + lng;

		return new GoogleMapMarker(markerPosition, "http://maps.google.com/mapfiles/ms/micons/blue-pushpin.png", contents,
						getName());

	}
}
