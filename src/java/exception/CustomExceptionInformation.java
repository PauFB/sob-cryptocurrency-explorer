package exception;

import java.util.Date;

public class CustomExceptionInformation {
    
    private String message;
    private String path;
    private int status;
    private Date timestamp;
    
    public CustomExceptionInformation(int status, String message, String path) {
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = new Date();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
