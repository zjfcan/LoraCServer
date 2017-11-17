package com.guina.loratracker.model;

public class GoogleMapCameraConfig
{
	private GoogleMapPosition center;
	private int zoom;

	public GoogleMapPosition getCenter()
	{
		return center;
	}

	public void setCenter(GoogleMapPosition center)
	{
		this.center = center;
	}

	public int getZoom()
	{
		return zoom;
	}

	public void setZoom(int zoom)
	{
		this.zoom = zoom;
	}

}
