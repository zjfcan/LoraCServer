package com.guina.loratracker.model;

public class ApplicationData
{
	private UpLinkData immeApp;
	private UpLinkData app;
	private Mote mote;
	private Gateway gateway;

	public UpLinkData getImmeApp()
	{
		return immeApp;
	}

	public void setImmeApp(UpLinkData immeApp)
	{
		this.immeApp = immeApp;
	}

	public UpLinkData getApp()
	{
		return app;
	}

	public void setApp(UpLinkData app)
	{
		this.app = app;
	}

	public Mote getMote()
	{
		return mote;
	}

	public void setMote(Mote mote)
	{
		this.mote = mote;
	}

	public Gateway getGateway()
	{
		return gateway;
	}

	public void setGateway(Gateway gateway)
	{
		this.gateway = gateway;
	}
}
