package model.entities;

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
import java.util.Date;

@Entity
@NamedQueries({
    @NamedQuery(name = "Cryptocurrency.findCryptocurrencyById",
            query = "SELECT c FROM Cryptocurrency c WHERE c.id = :id"),
    @NamedQuery(name = "Cryptocurrency.findAllPriceAscending",
            query = "SELECT c FROM Cryptocurrency c ORDER BY c.price ASC"),
    @NamedQuery(name = "Cryptocurrency.findAllPriceDescending",
            query = "SELECT c FROM Cryptocurrency c ORDER BY c.price DESC")
})
@XmlRootElement
public class Cryptocurrency implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "Cryptocurrency_Gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cryptocurrency_Gen")
    private int id;
    
    private String description;

    @NotNull
    private String name;

    @NotNull
    private double price;

    @NotNull
    private Date priceTimestamp;

    @ManyToMany
    private Collection<Customer> customers;

    @OneToMany(mappedBy = "cryptocurrency")
    private Collection<Purchase> purchases;

    public Cryptocurrency() {
    }

    public Cryptocurrency(String name, String description, double price, Date priceTimestamp) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.priceTimestamp = priceTimestamp;
        this.customers = new ArrayList<>();
        this.purchases = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPriceTimestamp() {
        return this.priceTimestamp;
    }

    public void setPriceTimestamp(Date priceTimestamp) {
        this.priceTimestamp = priceTimestamp;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    @Override
    public String toString() {
        return ("Name: " + this.name + " Description: " + this.description + " Price: " + this.price + " Price timestamp:" + this.priceTimestamp);
    }

}
