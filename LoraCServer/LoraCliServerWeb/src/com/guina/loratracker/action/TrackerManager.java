package com.guina.loratracker.action;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import com.guina.loratracker.entity.LoraUser;
import com.guina.loratracker.service.LoraDbService;

@ManagedBean
@ViewScoped
public class TrackerManager implements Serializable
{
	private static final long serialVersionUID = 4932054294380372680L;
	private Logger logger = Logger.getLogger(this.getClass());

	@EJB
	private LoraDbService dbService;

	private LoraUser loraUser;

	@PostConstruct
	public void init()
	{
		loraUser = new LoraUser();
		loraUser.setUsername("aaa");
		loraUser.setPassword("bbb");
		loraUser.setUserRole("ccc");
		loraUser.setUserGroup("ddd");
	}

	public String saveUser()
	{
		logger.info("Save user: " + loraUser);
		dbService.saveUser(loraUser);

		return null;
	}

	public LoraUser getLoraUser()
	{
		return loraUser;
	}

	public void setLoraUser(LoraUser loraUser)
	{
		this.loraUser = loraUser;
	}
}
