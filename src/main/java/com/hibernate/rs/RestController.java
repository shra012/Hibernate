package com.hibernate.rs;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/sayhello")
public class RestController {

    @GET
    @Path("/{name}")
    public Response sayHello(@PathParam("name") String msg) {
        String output = "Hello, " + msg + "!";
        Context initContext;
		try {
			initContext = new InitialContext();
			//Context envContext  = (Context)initContext.lookup("java:/comp/env");
			UserTransaction tx = (UserTransaction)initContext.lookup("java:comp/UserTransaction");
			if(tx!=null) {
				System.out.println("Success");
			}
	   /*     DataSource ds = (DataSource)envContext.lookup("jdbc/JAWSDB");
	        Connection conn = ds.getConnection();
	        System.out.print("Database is connected !"); */
		} catch (NamingException e) {
			System.out.print("Do not connect to JAWSDB - Error:" + e);
			e.printStackTrace();
		} 
        
        return Response.status(200).entity(output).build();
    }

}
