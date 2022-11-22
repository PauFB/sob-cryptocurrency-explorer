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
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
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
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") int id, Customer entity) {
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") int id) {
        
        Customer customer = super.find(id);
        Gson gson = new Gson();
        String jsonString = new Gson().toJson(customer);
        JSONObject request = serializeClass(customer);
        
        return Response.ok().entity(request.toString()).build();
    }
    
    private JSONObject serializeClass(Customer customer){
        Gson gson = new Gson();
        String jsonString = new Gson().toJson(customer);
        JSONObject request = null;
        try{
            request = new JSONObject(jsonString);
            request.remove("password");
        }catch(JSONException e){
        }
        return request;
    }
    
    @Override
    public List<Customer> findAll() {
        return null;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
      public String findAllNoPasswd() {
        
        List<Customer> result = super.findAll();
        JSONArray jsonList = new JSONArray();
        JSONObject request = null;
        
        for (Customer cust : result){
            request = serializeClass(cust);
            request.remove("password");
            jsonList.put(request);
        }
        return jsonList.toString();
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
