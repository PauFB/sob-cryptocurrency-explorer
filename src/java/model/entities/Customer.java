package model.entities;

import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Customer implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "Customer_Gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_Gen")
    private int id;
    private String email;
    private String name;
    private String password;
    private String phone;
    @ManyToMany(mappedBy = "customers")
    final private Collection<Coin> coins;
    @OneToMany(mappedBy = "customer")
    final private Collection<Purchase> purchases;

    public Customer() {
        this.purchases = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    public Customer(String name, String mail, String passwd, String phone) {
        this.name = name;
        this.email = mail;
        this.password = passwd;
        this.phone = phone;
        this.purchases = new ArrayList<>();
        this.coins = new ArrayList<>();
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

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    public void addCoin(Coin coin) {
        this.coins.add(coin);
    }

    @Override
    public String toString() {
        return ("Name: " + this.name + " Email: " + this.email + " Password: " + this.password + " Phone:" + this.phone);
    }

}
