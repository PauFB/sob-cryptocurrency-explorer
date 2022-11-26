package authn;
import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import model.entities.Customer;

@Entity
@NamedQuery(name="Credentials.findCredentialsByCustomerId",
            query="SELECT c FROM Credentials c WHERE c.customer.id = :customerId")
@XmlRootElement
public class Credentials implements Serializable {

    @Id
    @SequenceGenerator(name="Credentials_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Credentials_Gen") 
    private Long id;

    @NotNull(message="Password can't be null")
    private String password;

    @OneToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}