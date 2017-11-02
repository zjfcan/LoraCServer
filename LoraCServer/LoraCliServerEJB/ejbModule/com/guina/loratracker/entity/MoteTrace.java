package com.guina.loratracker.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.*;

@Entity
public class MoteTrace implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private LoraMote mote;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	Map<Date, GpsLocation> locations;

	public MoteTrace()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public LoraMote getMote()
	{
		return mote;
	}

	public void setMote(LoraMote mote)
	{
		this.mote = mote;
	}

	public Map<Date, GpsLocation> getLocations()
	{
		return locations;
	}

	public void setLocations(Map<Date, GpsLocation> locations)
	{
		this.locations = locations;
	}
}
