package model.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Purchase implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="Purchase_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Purchase_Gen")
    public long id;

    private double pricePerCoin;
    private float coinAmount;
    private Date date;

    //TODO Still have to implement the foreign keys to client and coin.
    @ManyToOne
    private Customer customer;

    //@OneToMany(mappedBy="purchases")
    private Coin coin;

    public Purchase() {}

    public Purchase(double pricePerCoin, float coinAmount, Date date) {
        this.pricePerCoin = pricePerCoin;
        this.coinAmount = coinAmount;
        this.date = (Date) date.clone();
    }

    public float getCoinAmount() {
        return coinAmount;
    }

    public Date getDate() {
        return date;
    }

    public double getPricePerCoin() {
        return pricePerCoin;
    }

    public void setCustomer(Customer customer) {
        if (this.customer == null) {
            this.customer = customer;
        }
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        if (this.coin == null) {
            this.coin = coin;
        }
    }
}