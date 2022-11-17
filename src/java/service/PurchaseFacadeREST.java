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
import model.entities.Purchase;
import authn.Secured;
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.StringTokenizer;
import model.entities.Coin;
import model.entities.Customer;
import requestbodies.MakePurchaseBody;

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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response makePurchase(@HeaderParam("Authorization") String auth, @QueryParam("cryptocurrency") int coinId, Purchase entity) {
        
        auth = auth.replace("Basic ", "");
        String decode = Base64.base64Decode(auth);
        StringTokenizer tokenizer = new StringTokenizer(decode, ":");
        String email = tokenizer.nextToken();
        
        Customer customer = em.createNamedQuery("Customer.findCustomerByEmail", Customer.class)
            .setParameter("email", email)
            .getSingleResult();
        Coin coin = em.createNamedQuery("Coin.findCoinById", Coin.class)
            .setParameter("id", coinId)
            .getSingleResult();
        
        //double amountCharged = entity.getPurchasedAmount() * coin.getPrice();
        
        entity.setCoin(coin);
        entity.setCustomer(customer);
        entity.setDate(new Date());
        super.create(entity);

        return Response.ok(entity).build();
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
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
