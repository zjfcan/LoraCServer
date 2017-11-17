package com.guina.loratracker.model;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.guina.loratracker.util.JsonUtils;

public class ActiveMoteNode extends NamedNode implements LoraTreeNode
{
	private static final long serialVersionUID = 2852508476848745672L;
	private MoteNode parent;
	private String time;
	private float lat;
	private float lng;

	public ActiveMoteNode()
	{
		super();
		this.setType("ActiveMote");
	}

	public void setParent(MoteNode parent)
	{
		this.parent = parent;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public float getLat()
	{
		return lat;
	}

	public void setLat(float lat)
	{
		this.lat = lat;
	}

	public float getLng()
	{
		return lng;
	}

	public void setLng(float lng)
	{
		this.lng = lng;
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

	@Override
	@SuppressWarnings("rawtypes")
	public Enumeration children()
	{
		return null;
	}

	@Override
	public String getDisplayContents()
	{
		return JsonUtils.convertJsonToString(getGoogleMapMarker());
	}

	@Override
	public GoogleMapMarker getGoogleMapMarker()
	{
		String contents = getType() + "=" + getName() + "<br/>" + lat + "," + lng;

		return new GoogleMapMarker(new GoogleMapPosition(lat, lng),
						"http://maps.google.com/mapfiles/ms/micons/blue-dot.png", contents,
						getName());

	}
}
