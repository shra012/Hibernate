package com.hibernate.rs;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.hibernate.entitymanager.factory.AppllicationEntityManagerFactory;
import com.hibernate.model.Message;

@Path("/controller")
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
			em.close();
			tx.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return Response.status(200).entity(printTable(messages)).build();
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
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(printTable(message)).build();
	}

	private String printTable(List<?> list) {
		if (null == list || list.size() <= 0) {
			return "<h1 style='color: red;'> No Records Found </h1>";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		if (list.get(0) instanceof Message) {
			Message me = (Message) list.get(0);
			sb = me.printTableHeader(sb);
		}
		for (Object object : list) {
			if (object instanceof Message) {
				Message me = (Message) object;
				sb = me.printTableRow(sb, me);
			}
		}
		sb.append("</table>");

		return sb.toString();
	}

	private String printTable(Object obj) {
		if (null == obj) {
			return "<h1 style='color: red;'> No Records Found </h1>";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		if (obj instanceof Message) {
			Message me = (Message) obj;
			sb = me.printTableHeader(sb);
		}

		if (obj instanceof Message) {
			Message me = (Message) obj;
			sb = me.printTableRow(sb, me);
		}

		sb.append("</table>");

		return sb.toString();
	}

}
