package service;

import jakarta.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

import webresources.HelloWorldResourceV1;
import webresources.ParrotResource;

@jakarta.ws.rs.ApplicationPath("/rest/api/v1")
public class RESTappV1 extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(HelloWorldResourceV1.class);
        classes.add(ParrotResource.class);
        classes.add(CustomerFacadeREST.class);
        classes.add(CoinFacadeREST.class);
        classes.add(PurchaseFacadeREST.class);
        classes.add(CommentFacadeREST.class);
        return classes;
    }
}
