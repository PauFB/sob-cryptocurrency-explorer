package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/helloworld")
public class HelloWorldResourceV2 {

    @GET
    @Produces("text/plain")
    public String getHelloWorldMessage() {
        return "Hello world V2!";
    }
}
