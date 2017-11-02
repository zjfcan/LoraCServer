package com.guina.loratracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LoraMote implements Serializable
{
	private static final long serialVersionUID = 5475915757588300986L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@OneToMany
	private List<MoteTrace> traces;
	
	private String description;
	
	public LoraMote()
	{
		super();
		traces = new ArrayList<>();
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

	public List<MoteTrace> getTraces()
	{
		return traces;
	}

	public void setTraces(List<MoteTrace> traces)
	{
		this.traces = traces;
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
