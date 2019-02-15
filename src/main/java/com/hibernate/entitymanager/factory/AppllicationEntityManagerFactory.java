package com.hibernate.entitymanager.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AppllicationEntityManagerFactory {
	
	private EntityManagerFactory factory;
	private static AppllicationEntityManagerFactory appllicationEntityManagerFactory = new AppllicationEntityManagerFactory();
	
	private AppllicationEntityManagerFactory() {
		this.factory = Persistence.createEntityManagerFactory("MyPresistance");
	}

	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return factory;
	}
	
	public static AppllicationEntityManagerFactory getInstance() {
		return appllicationEntityManagerFactory;
	}
}
