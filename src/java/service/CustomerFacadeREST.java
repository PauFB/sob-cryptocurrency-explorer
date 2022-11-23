package service;

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
import authn.Secured;
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;
import java.util.StringTokenizer;

@Stateless
@Path("customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Customer entity) {
        super.create(entity);
    }

    @PUT
    @Secured
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCustomerInfo(@HeaderParam("Authorization") String auth, @PathParam("id") int id, Customer entity) {
        auth = auth.replace("Basic ", "");
        String email = new StringTokenizer(Base64.base64Decode(auth), ":").nextToken();
        Customer cust = em.createNamedQuery("Customer.findCustomerByEmail", Customer.class)
                .setParameter("email", email)
                .getSingleResult();
        if (id != cust.getId())
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if (entity.getEmail() != null) {
            cust.setEmail(entity.getEmail());
        }
        if (entity.getName() != null)
            cust.setName(entity.getName());
        if (entity.getPassword() != null) {
            cust.setPassword(entity.getPassword());
        }
        if (entity.getPhone() != null)
            cust.setPhone(entity.getPhone());
        em.persist(cust);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") int id) {
        super.remove(super.find(id));
    }

    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") int id) {
        return Response.ok().entity(super.find(id)).build();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findAll() {
        return super.findAll();
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
