package com.guina.loratracker.model;

public class GoogleMapPosition
{
	private float lat;
	private float lng;

	public GoogleMapPosition()
	{
		super();
	}

	public GoogleMapPosition(float lat, float lng)
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
