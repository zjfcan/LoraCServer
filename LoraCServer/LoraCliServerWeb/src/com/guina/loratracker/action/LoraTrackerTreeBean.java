package com.guina.loratracker.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;
import org.richfaces.component.AbstractTree;
import org.richfaces.event.TreeSelectionChangeEvent;

import com.guina.loratracker.model.GoogleMapCameraConfig;
import com.guina.loratracker.model.GoogleMapDisplayConfig;
import com.guina.loratracker.model.GoogleMapMarker;
import com.guina.loratracker.model.GoogleMapPosition;
import com.guina.loratracker.model.LoraTreeNode;
import com.guina.loratracker.model.NamedNode;
import com.guina.loratracker.service.LoraDataManager;
import com.guina.loratracker.service.LoraDataRetriever;
import com.guina.loratracker.util.JsonUtils;

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
	List<LoraTreeNode> rootNodes = new ArrayList<>();

	// private String cameraPosition = ;
	private String mapMarkers = "{'camConfig' : {'center' : {'lat':45.349384,'lng':-75.924628},'zoom' : 17}, 'markers' : ["
					+ "{'position':{'lat':45.351578,'lng':-75.939724},'title':'HelloWorld!'},"
					+ "{'position':{'lat':45.353039,'lng':-75.939859},'title':'Jianfeng!'},"
					+ "{'position':{'lat':45.353160,'lng':-75.940975},'title':'Liana!'},"
					+ "{'position':{'lat':45.352180,'lng':-75.940750},'title':'Robert!'},"
					+ "{'position':{'lat':45.352014,'lng':-75.938823},'title':'Taolei!'}" + "]}";
	private LoraTreeNode currentSelection = null;
	private int total = 100;
	private boolean pollEnabled = true;

	@PostConstruct
	public void init()
	{
	}

	public String startReceivingData()
	{
		setPollEnabled(true);
		dataRetriever.startReceiveData(total);

		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss.sss Z");
		String dateToStr = format.format(curDate);
		logger.info("Finished reveiving data from AServer at " + dateToStr);

		updateTreeAndMap();

		return null;
	}

	private void updateTreeAndMap()
	{
		rootNodes = dataManager.buildTree(rootNodes);

		GoogleMapCameraConfig camConfig = new GoogleMapCameraConfig();
		camConfig.setCenter(new GoogleMapPosition(0.0f, 0.0f));// TODO To be determined
		camConfig.setZoom(2);// TODO To be determined
		createMapMarkers(camConfig);
	}

	private void createMapMarkers(GoogleMapCameraConfig camConfig)
	{
		GoogleMapDisplayConfig mapConfig = new GoogleMapDisplayConfig();
		mapConfig.setCamConfig(camConfig);
		logger.info("Tree size: " + rootNodes.size());
		for (LoraTreeNode node : rootNodes)
		{
			mapConfig.getMarkers().add(node.getGoogleMapMarker());
			logger.info("Tree node " + ((NamedNode) node).getName() + " with type "
							+ ((NamedNode) node).getName() + " has " + node.getChildCount()
							+ " children.");
			addChildren(mapConfig, node);//TODO give user choice to add all markers
		}
		mapMarkers = JsonUtils.convertJsonToString(mapConfig);
	}

	private void addChildren(GoogleMapDisplayConfig mapConfig, LoraTreeNode node)
	{
		Enumeration<?> children = node.children();
		if (children == null)
		{
			return;
		}

		int childCount = 0;
		while (children.hasMoreElements())
		{
			LoraTreeNode childNode = (LoraTreeNode) children.nextElement();
			logger.info("" + childCount + " child node " + ((NamedNode) childNode).getName()
							+ " with type " + ((NamedNode) node).getName() + " has "
							+ childNode.getChildCount() + " children.");

			mapConfig.getMarkers().add(childNode.getGoogleMapMarker());

			addChildren(mapConfig, childNode);
		}
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

		GoogleMapMarker googleMapMarker = currentSelection.getGoogleMapMarker();
		GoogleMapPosition position = googleMapMarker.getPosition();
		GoogleMapCameraConfig camConfig = new GoogleMapCameraConfig();
		camConfig.setCenter(position);// TODO To be determined
		camConfig.setZoom(15);// TODO To be determined
		createMapMarkers(camConfig);
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

	public boolean isPollEnabled()
	{
		return pollEnabled;
	}

	public void setPollEnabled(boolean pollEnabled)
	{
		this.pollEnabled = pollEnabled;
	}

	public String stopPolling()
	{
		logger.info("Stop polling.");
		setPollEnabled(false);
//		updateTreeAndMap();

		return null;
	}

	public String startPolling()
	{
		setPollEnabled(true);

		return null;
	}
}
