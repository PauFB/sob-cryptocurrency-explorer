package service;

import authn.RESTRequestFilter;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import testresources.HelloWorldResourceV1;

@jakarta.ws.rs.ApplicationPath("/rest/api/v1")
public class RESTappV1 extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(AbstractFacade.class);
        classes.add(CryptocurrencyFacadeREST.class);
        classes.add(CustomerFacadeREST.class);
        classes.add(PurchaseFacadeREST.class);
        classes.add(RESTRequestFilter.class);
        classes.add(HelloWorldResourceV1.class);
        return classes;
    }
}
