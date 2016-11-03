package com.soa.crudapp.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author luisb
 */
@ApplicationPath("/app")
public class CrudAppConfig extends ResourceConfig {
    public CrudAppConfig() {
        packages("com.soa.crudapp.controller");
    }
}
