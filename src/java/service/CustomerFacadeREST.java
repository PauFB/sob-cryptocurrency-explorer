package service;

import authn.Credentials;
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
import com.google.gson.Gson;
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

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
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Customer entity) {
        Credentials credentials = new Credentials();
        credentials.setUsername(entity.getEmail());
        credentials.setPassword(entity.getPassword());
        em.persist(credentials);
        super.create(entity);
    }

    @PUT
    @Secured
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCustomerInfo(@HeaderParam("Authorization") String auth, @PathParam("id") int id, Customer entity) {
        auth = auth.replace("Basic ", "");
        String decode = Base64.base64Decode(auth);
        StringTokenizer tokenizer = new StringTokenizer(decode, ":");
        String email = tokenizer.nextToken();
        Customer cust = em.createNamedQuery("Customer.findCustomerById", Customer.class)
                .setParameter("id", id)
                .getSingleResult();
        Credentials cred = em.createNamedQuery("Credentials.findUser", Credentials.class)
                .setParameter("username", email)
                .getSingleResult();
        if (entity.getEmail() != null) {
            cust.setEmail(entity.getEmail());
            cred.setUsername(entity.getEmail());
        }
        if (entity.getName() != null)
            cust.setName(entity.getName());
        if (entity.getPassword() != null) {
            cust.setPassword(entity.getPassword());
            cred.setPassword(entity.getPassword());
        }
        if (entity.getPhone() != null)
            cust.setPhone(entity.getPhone());
        em.persist(cust);
        em.persist(cred);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") int id) {
        super.remove(super.find(id));
    }

    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") int id) {
        Customer customer = super.find(id);
        JSONObject response = getJsonObjectWithoutPassword(customer);
        return Response.ok().entity(response.toString()).build();
    }

    private JSONObject getJsonObjectWithoutPassword(Customer customer) {
        String jsonString = new Gson().toJson(customer);
        JSONObject request;
        try {
            request = new JSONObject(jsonString);
            request.remove("password");
        } catch (JSONException e) {
            return null;
        }
        return request;
    }

    @Override
    public List<Customer> findAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String findAllWithoutPasswords() {
        List<Customer> customerList = super.findAll();
        JSONObject actualCustomer;
        JSONArray jsonArray = new JSONArray();
        for (Customer cust : customerList) {
            actualCustomer = getJsonObjectWithoutPassword(cust);
            actualCustomer.remove("password");
            jsonArray.put(actualCustomer);
        }
        return jsonArray.toString();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
