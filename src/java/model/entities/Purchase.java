package model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
public class Purchase implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "Purchase_Gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Purchase_Gen")
    private int id;
    
    @NotNull
    private double purchasedAmount;
    
    @NotNull
    private Date date;
    
    @ManyToOne
    @NotNull
    private Cryptocurrency cryptocurrency;
    
    @ManyToOne
    @NotNull
    private Customer customer;
    
    @Transient
    private double price;

    public Purchase() {
    }

    public Purchase(Date date, double purchasedAmount, Customer customer, Cryptocurrency cryptocurrency) {
        this.date = date;
        this.purchasedAmount = purchasedAmount;
        this.customer = customer;
        this.cryptocurrency = cryptocurrency;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPurchasedAmount() {
        return this.purchasedAmount;
    }

    public void setPurchasedAmount(double purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Cryptocurrency getCryptocurrency() {
        return this.cryptocurrency;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return ("Date: " + this.date + " Amount: " + this.purchasedAmount + " Customer: " + this.customer + " Cryptocurrency:" + this.cryptocurrency);
    }

}
