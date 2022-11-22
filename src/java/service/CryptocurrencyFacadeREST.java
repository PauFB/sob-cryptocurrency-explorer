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
import model.entities.Cryptocurrency;
import authn.Secured;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Stateless
@Path("cryptocurrency")
public class CryptocurrencyFacadeREST extends AbstractFacade<Cryptocurrency> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CryptocurrencyFacadeREST() {
        super(Cryptocurrency.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cryptocurrency entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") int id, Cryptocurrency entity) {
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/plain"})
    public Response findAll(@QueryParam("order") String order) {

        java.util.List<Cryptocurrency> listResult;
        jakarta.persistence.criteria.CriteriaBuilder cb = em.getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery criteria = cb.createQuery(Cryptocurrency.class);
        jakarta.persistence.criteria.Root<Cryptocurrency> root = criteria.from(Cryptocurrency.class);

        if (order == null) {
            listResult = super.findAll();
        } else {
            if (order.equalsIgnoreCase("asc")) {
                listResult = em.createQuery(criteria.select(root).orderBy(cb.asc(root.get("price")))).getResultList();
            } else if (order.equalsIgnoreCase("desc")) {
                listResult = em.createQuery(criteria.select(root).orderBy(cb.desc(root.get("price")))).getResultList();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }

        }
        return Response.ok(listResult).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cryptocurrency> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
