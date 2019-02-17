package com.persistence.rs;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.core.builder.ResponseBuilder;
import com.persistence.entitymanager.factory.AppllicationEntityManagerFactory;
import com.persistence.model.Message;

@Path("/persistence")
public class RestController {

	@GET
	@Path("/insert/{name}")
	public Response insertMessage(@PathParam("name") String msg) {
		String output = "Hello, " + msg + "!";
		Context initContext;
		try {
			// Using JDBC with JNDI.
			/*
			 * Context envContext = (Context)initContext.lookup("java:/comp/env");
			 * DataSource ds = (DataSource)envContext.lookup("jdbc/JAWSDB"); Connection con
			 * = ds.getConnection(); Statement stmt=con.createStatement(); String s =
			 * "INSERT into message_t (message_id, text_message) values (1, 'Hello World!')"
			 * ; stmt.execute(s); con.close(); System.out.print("Database is connected !");
			 */

			// Using entity Manager - JPA with JTA
			initContext = new InitialContext();
			UserTransaction tx = (UserTransaction) initContext.lookup("java:comp/UserTransaction");
			tx.begin();
			EntityManager em = AppllicationEntityManagerFactory.getInstance().getEntityManager();
			Message message = new Message();
			message.setText("Hello, " + msg + "!");
			em.persist(message);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Failed to insert the record").build();
		}

		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/getMessages")
	public Response getMessages() {
		Context initContext;
		List<Message> messages = null;
		try {
			// Using entity Manager - JPA with JTA
			initContext = new InitialContext();
			UserTransaction tx = (UserTransaction) initContext.lookup("java:comp/UserTransaction");
			tx.begin();
			EntityManager em = AppllicationEntityManagerFactory.getInstance().getEntityManager();
			messages = em.createQuery("select m from Message m").getResultList();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(ResponseBuilder.printTable(messages)).build();
	}
	
	@GET
	@Path("/getMessage/{id}")
	public Response getMessages(@PathParam("id") Long id) {
		Context initContext;
		Message message = null;
		try {
			// Using entity Manager - JPA with JTA
			initContext = new InitialContext();
			UserTransaction tx = (UserTransaction) initContext.lookup("java:comp/UserTransaction");
			tx.begin();
			EntityManager em = AppllicationEntityManagerFactory.getInstance().getEntityManager();
			message = em.find(Message.class, id);
			message.setText("Hey "+message.getText().split(",")[1]);
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(ResponseBuilder.printTable(message)).build();
	}
	

}
