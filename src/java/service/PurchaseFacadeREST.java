package service;

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
import model.entities.Purchase;
import com.sun.xml.messaging.saaj.util.Base64;
import exception.CustomException;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.StringTokenizer;
import model.entities.Cryptocurrency;
import model.entities.Customer;

@Stateless
@Path("purchase")
public class PurchaseFacadeREST extends AbstractFacade<Purchase> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public PurchaseFacadeREST() {
        super(Purchase.class);
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPurchase(@Context UriInfo uriInfo,
                                   @HeaderParam("Authorization") String auth,
                                   @QueryParam("cryptocurrency") int cryptocurrencyId,
                                   Purchase entity) {
        auth = auth.replace("Basic ", "");
        String email = new StringTokenizer(Base64.base64Decode(auth), ":").nextToken();
        try {
            Customer customer = em.createNamedQuery("Customer.findCustomerByEmail", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
            Cryptocurrency cryptocurrency = em.createNamedQuery("Cryptocurrency.findCryptocurrencyById", Cryptocurrency.class)
                    .setParameter("id", cryptocurrencyId)
                    .getSingleResult();
            entity.setCryptocurrency(cryptocurrency);
            entity.setCustomer(customer);
            entity.setDate(new Date());
            em.persist(entity);
            entity.setPrice(entity.getPurchasedAmount() * cryptocurrency.getPrice());
            em.persist(cryptocurrency);
        } catch (NoResultException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, "Invalid cryptocurrency ID", uriInfo.getPath());
        }
        return Response.ok().entity(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") int id, Purchase entity) {
        super.edit(entity);
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
    public List<Purchase> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Purchase> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
