package com.guina.loratracker.model;

public class GoogleMapMarker
{
	private GoogleMapPosition position;
	private String iconImg;
	private String contents;
	private String title;

	public GoogleMapMarker()
	{
		super();
	}

	public GoogleMapMarker(GoogleMapPosition position, String iconImg, String contents, String title)
	{
		super();
		this.position = position;
		this.iconImg = iconImg;
		this.contents = contents;
		this.title = title;
	}

	public GoogleMapPosition getPosition()
	{
		return position;
	}

	public void setPisition(GoogleMapPosition position)
	{
		this.position = position;
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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
