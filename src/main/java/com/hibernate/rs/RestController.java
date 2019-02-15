package com.hibernate.rs;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.hibernate.entitymanager.factory.AppllicationEntityManagerFactory;
import com.hibernate.model.Message;

@Path("/sayhello")
public class RestController {

    @GET
    @Path("/{name}")
    public Response sayHello(@PathParam("name") String msg) {
        String output = "Hello, " + msg + "!";
        Context initContext;
		try {
			//Using JDBC with JNDI. 
			/*
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    	DataSource ds = (DataSource)envContext.lookup("jdbc/JAWSDB");
	        Connection con = ds.getConnection();
	        Statement stmt=con.createStatement(); 
			String s =  "INSERT into message_t (message_id, text_message) values (1, 'Hello World!')";
			stmt.execute(s);  
			con.close();
	        System.out.print("Database is connected !"); */
			
			//Using entity Manager - JPA with JTA
		    initContext = new InitialContext();
			UserTransaction tx = (UserTransaction)initContext.lookup("java:comp/UserTransaction");
			tx.begin();
			EntityManager em = AppllicationEntityManagerFactory.getInstance().getEntityManager();
			Message message = new Message();
			message.setText("Hello, " + msg + "!");
			em.persist(message);
			tx.commit();
			em.close();
			
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return Response.status(200).entity(output).build();
    }

}
