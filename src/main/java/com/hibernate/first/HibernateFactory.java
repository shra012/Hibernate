package com.hibernate.first;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.persistence.model.Message;


public class HibernateFactory {
	private static final SessionFactory sessionFactory = new  HibernateFactory().getSession();
	
	private SessionFactory getSession() {

		StandardServiceRegistryBuilder serviceRegistryBuilder =
				new StandardServiceRegistryBuilder();
		serviceRegistryBuilder
		.applySetting("hibernate.connection.datasource", "java:comp/env/jdbc/JAWSDB")
		.applySetting("hibernate.format_sql", "true")
		.applySetting("hibernate.use_sql_comments", "true")
		.applySetting("hibernate.hbm2ddl.auto", "create-drop")
		.applySetting("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.JOTMJtaPlatform")
		.applySetting("hibernate.current_session_context_class", "thread")
		.applySetting("hibernate.transaction.coordinator_class", "jta");

		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		MetadataSources metadataSources = new MetadataSources(serviceRegistry).addAnnotatedClass(Message.class)	;
		Metadata metadata = metadataSources.buildMetadata();
		SessionFactory sessionFactory = metadata.buildSessionFactory();
		return sessionFactory;
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
