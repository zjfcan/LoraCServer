package com.guina.loratracker.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class GpsLocation implements Serializable
{
	private static final long serialVersionUID = 379104433149715886L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private float latitude;
	private float longitude;
	private String description;
	
	public GpsLocation()
	{
		super();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getLatitude()
	{
		return latitude;
	}

	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}

	public float getLongitude()
	{
		return longitude;
	}

	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
