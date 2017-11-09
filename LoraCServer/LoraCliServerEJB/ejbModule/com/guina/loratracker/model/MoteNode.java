package com.guina.loratracker.model;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.TreeNode;

public class MoteNode extends NamedNode implements LoraTreeNode
{
	private static final long serialVersionUID = -8509649794114670371L;
	private GatewayNode parent;
	private Mote mote;
	private Set<String> moteHistory;

	public MoteNode()
	{
		super();
		this.setType("Mote");
	}

	@Override
	public TreeNode getChildAt(int childIndex)
	{
		return null;
	}

	@Override
	public int getChildCount()
	{
		return 0;
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
		return 0;
	}

	@Override
	public boolean getAllowsChildren()
	{
		return false;
	}

	@Override
	public boolean isLeaf()
	{
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children()
	{
		return null;
	}

	public Mote getMote()
	{
		return mote;
	}

	public void setMote(Mote mote)
	{
		this.mote = mote;
	}

	public Set<String> getMoteHistory()
	{
		return moteHistory;
	}

	public void setMoteHistory(Set<String> moteHistory)
	{
		this.moteHistory = moteHistory;
	}

	@Override
	public String getDisplayContents()
	{
		return "MoteNode [parent=" + parent + ", mote=" + mote + "]";
	}
}
