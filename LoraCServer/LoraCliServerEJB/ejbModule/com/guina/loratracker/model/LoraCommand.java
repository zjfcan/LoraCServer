package com.guina.loratracker.model;

import java.io.Serializable;

public class LoraCommand implements Serializable
{
	private static final long serialVersionUID = 7193884672453813930L;
	private LoraCommandType commandType;
	private String data;

	public LoraCommand()
	{
		super();
	}

	public LoraCommand(LoraCommandType commandType, String data)
	{
		super();
		this.commandType = commandType;
		this.data = data;
	}

	public LoraCommandType getCommandType()
	{
		return commandType;
	}

	public void setCommandType(LoraCommandType commandType)
	{
		this.commandType = commandType;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "LoraCommand [commandType=" + commandType + ", data=" + data + "]";
	}
}
