package com.guina.loratracker;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

/**
 * Session Bean implementation class LoraDataRetriever
 */
@Singleton
@LocalBean
public class LoraDataRetriever
{
	private Logger logger = Logger.getLogger(getClass());

	@PersistenceContext
	private EntityManager em;


	/**
	 * Default constructor.
	 */
	public LoraDataRetriever()
	{
		// TODO Auto-generated constructor stub
	}

	@Schedule(minute = "*/1", hour = "*", persistent = false)
	public void retrieveLoraData()
	{
		logger.debug("Retrieving Lora Data.");
//		String queryHootString = "SELECT DISTINCT x FROM HootChannel x WHERE x.sourceRouteList IS NOT EMPTY";
//		TypedQuery<HootChannel> queryHoot = em.createQuery(queryHootString, HootChannel.class);
//		List<HootChannel> hootsToAssureConnecting = queryHoot.getResultList();

	}
}
