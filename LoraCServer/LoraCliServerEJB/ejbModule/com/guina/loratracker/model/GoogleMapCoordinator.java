package com.guina.loratracker.model;

public class GoogleMapCoordinator
{
	private float lat;
	private float lng;

	public GoogleMapCoordinator()
	{
		super();
	}

	public GoogleMapCoordinator(float lat, float lng)
	{
		super();
		this.lat = lat;
		this.lng = lng;
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
}
