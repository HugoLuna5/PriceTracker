package lunainc.com.mx.pricetracker.Model;

import java.io.Serializable;

public class Price implements Serializable {


    private int id;
    private int product_id;
    private String price;
    private String checked;

    public Price() {
    }

    public Price(int id, int product_id, String price, String checked) {
        this.id = id;
        this.product_id = product_id;
        this.price = price;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
