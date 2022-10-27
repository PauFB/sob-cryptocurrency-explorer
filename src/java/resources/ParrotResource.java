package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/parrot/{msg}")
public class ParrotResource {

    @GET
    @Produces("text/plain")
    public String parrotMessage(@PathParam("msg") String msg) {
        return msg;
    }
}
