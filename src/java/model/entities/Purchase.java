package model.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Purchase implements java.io.Serializable {

    @Id
    @SequenceGenerator(name = "Purchase_Gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Purchase_Gen")
    public long id;

    private double pricePerCoin;
    private float coinAmount;
    private Date date;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Coin coin;

    public Purchase() {
    }

    public Purchase(double pricePerCoin, float coinAmount, Date date) {
        this.pricePerCoin = pricePerCoin;
        this.coinAmount = coinAmount;
        this.date = (Date) date.clone();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPricePerCoin() {
        return pricePerCoin;
    }

    public void setPricePerCoin(double pricePerCoin) {
        this.pricePerCoin = pricePerCoin;
    }

    public float getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(float coinAmount) {
        this.coinAmount = coinAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
}
