package lunainc.com.mx.pricetracker.Model;

public class Product {


    private int id;
    private String id_product;
    private String name;
    private String price;
    private String url;
    private String dist;
    private String created_at;

    public Product() {
    }

    public Product(int id, String id_product, String name, String price, String url, String dist, String created_at) {
        this.id = id;
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.url = url;
        this.dist = dist;
        this.created_at = created_at;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
