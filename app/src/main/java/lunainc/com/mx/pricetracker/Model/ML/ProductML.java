package lunainc.com.mx.pricetracker.Model.ML;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductML {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("site_id")
    @Expose
    private String site_id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("subtitle")
    @Expose
    private String subtitle;

    @SerializedName("seller_id")
    @Expose
    private String seller_id;

    @SerializedName("category_id")
    @Expose
    private String category_id;

    @SerializedName("official_store_id")
    @Expose
    private String official_store_id;

    @SerializedName("price")
    @Expose
    private Long price;

    @SerializedName("base_price")
    @Expose
    private Long base_price;

    @SerializedName("original_price")
    @Expose
    private Long original_price;

    @SerializedName("currency_id")
    @Expose
    private String currency_id;

    @SerializedName("pictures")
    @Expose
    private ArrayList<Picture> pictures;


    public ProductML() {
    }

    public ProductML(String id, String site_id, String title, String subtitle, String seller_id, String category_id, String official_store_id, Long price, Long base_price, Long original_price, String currency_id, ArrayList<Picture> pictures) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.subtitle = subtitle;
        this.seller_id = seller_id;
        this.category_id = category_id;
        this.official_store_id = official_store_id;
        this.price = price;
        this.base_price = base_price;
        this.original_price = original_price;
        this.currency_id = currency_id;
        this.pictures = pictures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getOfficial_store_id() {
        return official_store_id;
    }

    public void setOfficial_store_id(String official_store_id) {
        this.official_store_id = official_store_id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getBase_price() {
        return base_price;
    }

    public void setBase_price(Long base_price) {
        this.base_price = base_price;
    }

    public Long getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Long original_price) {
        this.original_price = original_price;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }
}
