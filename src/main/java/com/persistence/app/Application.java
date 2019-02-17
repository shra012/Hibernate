package com.persistence.app;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
    public Application() {
        // Define the package which contains the service classes.
        packages("com.persistence.rs","com.hibernate.rs");
    }
}
	