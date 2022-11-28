package exception;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationException
public class CustomException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private CustomExceptionInformation customExceptionInformation;
    private Response.Status status;

    public CustomException(Response.Status status, String message, String path) {
        super();
        this.customExceptionInformation = new CustomExceptionInformation(status.getStatusCode(), message, path);
        this.status = status;
    }

    public CustomExceptionInformation getCustomExceptionInformation() {
        return this.customExceptionInformation;
    }

    public Response.Status getStatus() {
        return this.status;
    }

}
