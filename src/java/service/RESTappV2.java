package service;

import authn.RESTRequestFilter;
import exception.CustomExceptionMapper;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import testresources.HelloWorldResourceV2;

@jakarta.ws.rs.ApplicationPath("/rest/api/v2")
public class RESTappV2 extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(AbstractFacade.class);
        classes.add(CryptocurrencyFacadeREST.class);
        classes.add(CustomerFacadeREST.class);
        classes.add(PurchaseFacadeREST.class);
        classes.add(RESTRequestFilter.class);
        classes.add(CustomExceptionMapper.class);
        classes.add(HelloWorldResourceV2.class);
        return classes;
    }
}
