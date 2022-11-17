package model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
    private double purchasedAmount;
    private Date date;
    @OneToOne
    private Coin coin;
    @ManyToOne
    private Customer customer;

    public Purchase() {
    }

    public Purchase(Date date, double purchasedAmount, Customer customer, Coin coin) {
        this.date = date;
        this.purchasedAmount = purchasedAmount;
        this.customer = customer;
        this.coin = coin;
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

    public Coin getCoin() {
        return this.coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return ("Date: " + this.date + " Amount: " + this.purchasedAmount + " Customer: " + this.customer + " Coin:" + this.coin);
    }

}
