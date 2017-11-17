package com.guina.loratracker.model;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapDisplayConfig
{
	private GoogleMapCameraConfig camConfig;
	private List<GoogleMapMarker> markers = new ArrayList<>();

	public GoogleMapCameraConfig getCamConfig()
	{
		return camConfig;
	}

	public void setCamConfig(GoogleMapCameraConfig camConfig)
	{
		this.camConfig = camConfig;
	}

	public List<GoogleMapMarker> getMarkers()
	{
		return markers;
	}

	public void setMarkers(List<GoogleMapMarker> markers)
	{
		this.markers = markers;
	}

}
