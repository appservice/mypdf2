package eu.luckyapp.mypdf.utils;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Singleton
public class EntityManagerProducer {

    @Inject
    Logger LOG;


    @PersistenceContext(unitName = "mypdf-persistence-unit")
    private EntityManager entityManager;


    @Produces
    @Dependent
    public EntityManager create() {
        LOG.info("entity maganer is created: {} " ,entityManager.toString());
        return entityManager;
    }

}
