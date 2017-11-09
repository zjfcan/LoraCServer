package com.guina.loratracker.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;
import org.richfaces.component.AbstractTree;
import org.richfaces.event.TreeSelectionChangeEvent;

import com.guina.loratracker.model.LoraTreeNode;
import com.guina.loratracker.service.LoraDataManager;
import com.guina.loratracker.service.LoraDataRetriever;

@ManagedBean
@ViewScoped
public class LoraTrackerTreeBean implements Serializable
{
	private static final long serialVersionUID = 8796392781540109986L;

	private Logger logger = Logger.getLogger(this.getClass());

	@EJB
	private LoraDataManager dataManager;

	@EJB
	private LoraDataRetriever dataRetriever;
	List<LoraTreeNode> rootNodes;

//	private String cameraPosition = ;
	private String mapMarkers = "["
					+ "{'position':{'lat':45.351578,'lng':-75.939724},'title':'HelloWorld!'},"
					+ "{'position':{'lat':45.353039,'lng':-75.939859},'title':'Jianfeng!'},"
					+ "{'position':{'lat':45.353160,'lng':-75.940975},'title':'Liana!'},"
					+ "{'position':{'lat':45.352180,'lng':-75.940750},'title':'Robert!'},"
					+ "{'position':{'lat':45.352014,'lng':-75.938823},'title':'Taolei!'}" + "]";
	private LoraTreeNode currentSelection = null;
	private int total = 100;

	@PostConstruct
	public void init()
	{
	}

	public String startReceivingData()
	{
		total = 100;
		dataRetriever.startReceiveData(total);
		logger.info("Started connecting server.");

		return null;
	}

	public String stopReceivingData()
	{
		dataRetriever.stopReceiveData();
		logger.info("Stop listening server.");
		rootNodes = dataManager.buildTree();

		return null;
	}

	public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent)
	{
		// considering only single selection
		List<Object> selection = new ArrayList<Object>(selectionChangeEvent.getNewSelection());
		Object currentSelectionKey = selection.get(0);
		AbstractTree tree = (AbstractTree) selectionChangeEvent.getSource();

		Object storedKey = tree.getRowKey();
		tree.setRowKey(currentSelectionKey);
		currentSelection = (LoraTreeNode) tree.getRowData();
		tree.setRowKey(storedKey);
	}

	public List<LoraTreeNode> getRootNodes()
	{
		return rootNodes;
	}

	public void setRootNodes(List<LoraTreeNode> rootNodes)
	{
		this.rootNodes = rootNodes;
	}

	public LoraTreeNode getCurrentSelection()
	{
		return currentSelection;
	}

	public void setCurrentSelection(LoraTreeNode currentSelection)
	{
		this.currentSelection = currentSelection;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public String getDisplayData()
	{
		String ret = "Empty";
		if (currentSelection != null)
		{
			ret = currentSelection.getDisplayContents();
		}

		return ret;
	}

	public String getMapMarkers()
	{
		logger.info("Get map markers: " + mapMarkers);

		return mapMarkers;
	}

	public void setMapMarkers(String mapMarkers)
	{
		this.mapMarkers = mapMarkers;
	}
}
