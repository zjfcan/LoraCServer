package com.guina.loratracker.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.guina.loratracker.entity.LoraUser;

@Stateless
public class LoraDbService
{
	private Logger logger = Logger.getLogger(LoraDbService.class);

	@PersistenceContext
	private EntityManager em;

	public void saveUser(LoraUser loraUser)
	{
		em.merge(loraUser);
		em.flush();

		logger.info("Persisted User: " + loraUser);
	}
	// public List<RegisteredDevice> getRegisteredDevices()
	// {
	// String queryString = "SELECT DISTINCT d FROM RegisteredDevice d ORDER BY d.lastModified DESC, d.macAddr ASC";
	// TypedQuery<RegisteredDevice> query = em.createQuery(queryString, RegisteredDevice.class);
	// return query.getResultList();
	// }
}
