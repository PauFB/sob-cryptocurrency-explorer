package model.entities;

import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
    @NamedQuery(name="Customer.findCustomerByEmail",
            query="SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name="Customer.findCustomerById",
            query="SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name="Customer.findCustomerByIdExceptPassword",
            query="SELECT c.id, c.email, c.name, c.phone FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name="Customer.findAll",
            query="SELECT c.id, c.email, c.name, c.phone FROM Customer c")
})
@XmlRootElement
public class Customer implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "Customer_Gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_Gen")
    private int id;
    @Column(unique=true)
    private String email;
    private String name;
    private String password;
    private String phone;
    @ManyToMany(mappedBy = "customers")
    final private Collection<Cryptocurrency> cryptocurrencies;
    @OneToMany(mappedBy = "customer")
    final private Collection<Purchase> orders;

    public Customer() {
        this.orders = new ArrayList<>();
        this.cryptocurrencies = new ArrayList<>();
    }

    public Customer(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.orders = new ArrayList<>();
        this.cryptocurrencies = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addOrder(Purchase order) {
        this.orders.add(order);
    }

    public void addCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrencies.add(cryptocurrency);
    }

    @Override
    public String toString() {
        return ("Name: " + this.name + " Email: " + this.email + " Password: " + this.password + " Phone:" + this.phone);
    }

}
