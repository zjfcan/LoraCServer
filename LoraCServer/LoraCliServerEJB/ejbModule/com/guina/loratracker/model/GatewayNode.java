package com.guina.loratracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.guina.loratracker.util.JsonUtils;

public class GatewayNode extends NamedNode implements LoraTreeNode
{
	private static final long serialVersionUID = -3064424630910450896L;

	private Gateway gateway;
	private List<MoteNode> motes = new ArrayList<>();

	public GatewayNode()
	{
		this.setType("Gateway");
	}

	public TreeNode getChildAt(int childIndex)
	{
		return motes.get(childIndex);
	}

	public int getChildCount()
	{
		return motes.size();
	}

	public TreeNode getParent()
	{
		return null;
	}

	public int getIndex(TreeNode node)
	{
		return motes.indexOf(node);
	}

	public boolean getAllowsChildren()
	{
		return true;
	}

	public boolean isLeaf()
	{
		return motes.isEmpty();
	}

	public Enumeration<MoteNode> children()
	{
		return Collections.enumeration(motes);
	}

	public List<MoteNode> getMotes()
	{
		return motes;
	}

	public void setMotes(List<MoteNode> motes)
	{
		this.motes = motes;
	}

	public Gateway getGateway()
	{
		return gateway;
	}

	public void setGateway(Gateway gateway)
	{
		this.gateway = gateway;
	}

	@Override
	public String getDisplayContents()
	{
		// return "GatewayNode [gateway=" + gateway + ", motes=" + motes + "]";
		return JsonUtils.convertJsonToString(new GoogleMapMarker(
						new GoogleMapCoordinator(gateway.getStatus().getLati(),
										gateway.getStatus().getLong()),
						"/resources/images/capitals/newmexico.gif", getName()));
	}
}