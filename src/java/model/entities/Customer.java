package model.entities;

import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Customer implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Customer_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_Gen")
    private long id;

    private String name;
    private String email;
    private String password;
    private String phone;

    @OneToMany(mappedBy="customer")
    private Collection<Purchase> purchases;

    public Customer() {
        this.purchases = new ArrayList<>();
    }

    public Customer(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.purchases = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void addPurchases(Purchase purchase) {
        this.purchases.add(purchase);
    }

    @Override
    public String toString() {
        return ("Name: " + this.name + " Mail: " + this.email + " Password: " + this.password + " Phone:" + this.phone);
    }
}
