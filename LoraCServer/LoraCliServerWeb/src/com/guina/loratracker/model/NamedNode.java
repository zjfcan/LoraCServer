package com.guina.loratracker.model;

import java.io.Serializable;

public class NamedNode implements Serializable
{
	private static final long serialVersionUID = 2253107005447940202L;
	protected String type;
	protected String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}