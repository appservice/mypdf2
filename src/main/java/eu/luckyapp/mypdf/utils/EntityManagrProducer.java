package eu.luckyapp.mypdf.utils;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Singleton
public class EntityManagrProducer {

	   @Produces
	   @PersistenceContext(unitName = "mypdf-persistence-unit")
	   private EntityManager em;

}
