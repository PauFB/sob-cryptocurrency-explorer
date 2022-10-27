package model.entities;

import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Coin implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="Coin_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Coin_Gen")
    private long id;

    private String coinName;
    private double actualPrice;
    private String description;

    @OneToMany
    private Collection<Purchase> purchases;

    public Coin() {
        this.purchases = new ArrayList<>();
    }

    public Coin(String coinName, double actualPrice, String description) {
        this.coinName = coinName;
        this.actualPrice = actualPrice;
        this.description = description;
        this.purchases = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public String getCoinName() {
        return coinName;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public String getDescription() {
        return description;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
