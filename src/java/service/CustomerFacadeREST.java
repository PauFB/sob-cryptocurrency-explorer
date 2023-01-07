package service;

import authn.Credentials;
import authn.Secured;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.Customer;
import com.sun.xml.messaging.saaj.util.Base64;
import exception.CustomException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.StringTokenizer;
import model.entities.Purchase;

@Stateless
@Path("customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createCustomer(Customer entity) {
        Customer cust = new Customer();
        cust.setEmail(entity.getEmail());
        cust.setName(entity.getName());
        cust.setPhone(entity.getPhone());
        Credentials cred = new Credentials();
        cred.setCustomer(cust);
        cred.setPassword(entity.getPassword());
        em.persist(cust);
        em.persist(cred);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Secured
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCustomerInfo(@Context UriInfo uriInfo,
                                       @HeaderParam("Authorization") String auth,
                                       @PathParam("id") int id,
                                       Customer entity) {
        auth = auth.replace("Basic ", "");
        String email = new StringTokenizer(Base64.base64Decode(auth), ":").nextToken();
        Customer authorizedCustomer = em.createNamedQuery("Customer.findCustomerByEmail", Customer.class)
                .setParameter("email", email)
                .getSingleResult();
        if (id != authorizedCustomer.getId()) {
            throw new CustomException(Response.Status.FORBIDDEN, "Specified ID does not match authorized customer", uriInfo.getPath());
        }
        if (entity.getEmail() != null) {
            authorizedCustomer.setEmail(entity.getEmail());
        }
        if (entity.getName() != null) {
            authorizedCustomer.setName(entity.getName());
        }
        if (entity.getPassword() != null) {
            Credentials cred = em.createNamedQuery("Credentials.findCredentialsByCustomerId", Credentials.class)
                    .setParameter("customerId", id)
                    .getSingleResult();
            cred.setPassword(entity.getPassword());
            em.persist(cred);
        }
        if (entity.getPhone() != null) {
            authorizedCustomer.setPhone(entity.getPhone());
        }
        em.persist(authorizedCustomer);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") int id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") int id) {
        Customer customer = super.find(id);
        if (customer == null)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().entity(super.find(id)).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAllCustomers() {
        List<Customer> resultList = super.findAll();
        if (resultList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().entity(new GenericEntity<List<Customer>>(resultList) {}).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Override
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int count() {
        return super.count();
    }

    @GET
    @Path("validate")
    @Secured
    public Integer validateCustomer(@HeaderParam("Authorization") String auth) {
        auth = auth.replace("Basic ", "");
        String email = new StringTokenizer(Base64.base64Decode(auth), ":").nextToken();
        Customer authorizedCustomer = em.createNamedQuery("Customer.findCustomerByEmail", Customer.class)
                .setParameter("email", email)
                .getSingleResult();
        return authorizedCustomer.getId();
    }

    @GET
    @Path("purchases")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCustomerPurchases(@HeaderParam("Authorization") String auth) {
        auth = auth.replace("Basic ", "");
        String email = new StringTokenizer(Base64.base64Decode(auth), ":").nextToken();
        List<Purchase> resultList = em.createNamedQuery("Customer.findPurchasesByEmail", Purchase.class)
                .setParameter("email", email)
                .getResultList();
        if (resultList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().entity(new GenericEntity<List<Purchase>>(resultList) {}).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
