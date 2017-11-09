package com.guina.loratracker.model;

public class GoogleMapMarker
{
	private GoogleMapCoordinator coords;
	private String iconImg;
	private String contents;

	public GoogleMapMarker()
	{
		super();
	}

	public GoogleMapMarker(GoogleMapCoordinator coords, String iconImg, String contents)
	{
		super();
		this.coords = coords;
		this.iconImg = iconImg;
		this.contents = contents;
	}

	public GoogleMapCoordinator getCoords()
	{
		return coords;
	}

	public void setCoords(GoogleMapCoordinator coords)
	{
		this.coords = coords;
	}

	public String getIconImg()
	{
		return iconImg;
	}

	public void setIconImg(String iconImg)
	{
		this.iconImg = iconImg;
	}

	public String getContents()
	{
		return contents;
	}

	public void setContents(String contents)
	{
		this.contents = contents;
	}

}
