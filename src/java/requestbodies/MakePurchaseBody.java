package requestbodies;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

public class MakePurchaseBody implements java.io.Serializable {
    private String email;
    private double purchasedAmount;
    
    public MakePurchaseBody() {
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPurchasedAmount() {
        return this.purchasedAmount;
    }

    public void setPurchasedAmount(double purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }
}
