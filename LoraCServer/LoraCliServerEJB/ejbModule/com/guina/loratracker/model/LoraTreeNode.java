package com.guina.loratracker.model;

import javax.swing.tree.TreeNode;

public interface LoraTreeNode extends TreeNode
{
	public String getDisplayContents();
	public GoogleMapMarker getGoogleMapMarker();
}
