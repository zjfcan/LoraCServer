package com.guina.loratracker.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class LoraGateway implements Serializable
{
	private static final long serialVersionUID = -5212414597744266604L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
	private GpsLocation location;
	private String description;
	
	private LoraMote loraMote;

	public LoraGateway()
	{
		super();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

	public GpsLocation getLocation()
	{
		return location;
	}

	public void setLocation(GpsLocation location)
	{
		this.location = location;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public LoraMote getLoraMote()
	{
		return loraMote;
	}

	public void setLoraMote(LoraMote loraMote)
	{
		this.loraMote = loraMote;
	}
}
