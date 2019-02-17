package com.hibernate.rs;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.core.builder.ResponseBuilder;
import com.hibernate.first.HibernateFactory;
import com.persistence.model.Message;

@Path("/hibernate")
public class RestController {
	
	@GET
	@Path("/insert/{name}")
	public Response sayHello(@PathParam("name") String msg) {
		Context initContext;
		List<Message> messages = null;
		try {
			// Using entity Manager - JPA with JTA
			initContext = new InitialContext();
			UserTransaction tx = (UserTransaction) initContext.lookup("java:comp/UserTransaction");
			tx.begin();
			Session session = HibernateFactory.getSessionFactory().getCurrentSession();
			Message message = new Message();
			message.setText("Hello, " + msg + "!");
			session.persist(message);
			
			messages = session.createCriteria(Message.class).list();
			
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(ResponseBuilder.printTable(messages)).build();
	}
}
