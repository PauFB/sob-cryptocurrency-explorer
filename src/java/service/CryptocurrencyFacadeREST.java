package service;

import exception.CustomException;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import model.entities.Purchase;
import model.entities.Cryptocurrency;

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
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@Context UriInfo uriInfo, @PathParam("id") int id) {
        try {
            Purchase purchase = em.createNamedQuery("Purchase.findPurchasesByCryptocurrencyId", Purchase.class)
                    .setParameter("cryptocurrencyId", id)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getSingleResult();
            return Response.ok().entity(purchase).build();
        } catch (NoResultException e) {
            throw new CustomException(Response.Status.NOT_FOUND, "Invalid cryptocurrency ID", uriInfo.getPath());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll(@Context UriInfo uriInfo, @QueryParam("order") String order) {
        List<Cryptocurrency> resultList;
        if (order == null) {
            resultList = super.findAll();
        } else {
            switch (order) {
                case "asc":
                    resultList = em.createNamedQuery("Cryptocurrency.findAllPriceAscending", Cryptocurrency.class).getResultList();
                    break;
                case "desc":
                    resultList = em.createNamedQuery("Cryptocurrency.findAllPriceDescending", Cryptocurrency.class).getResultList();
                    break;
                default:
                    throw new CustomException(Response.Status.BAD_REQUEST, "Invalid order", uriInfo.getPath());
            }
        }
        if (resultList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().entity(new GenericEntity<List<Cryptocurrency>>(resultList) {}).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cryptocurrency> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
