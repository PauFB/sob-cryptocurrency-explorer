package exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {
    
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public Response toResponse(CustomException e) {
        return Response.status(e.getStatus()).entity(e.getCustomExceptionInformation()).build();
    }
    
}
