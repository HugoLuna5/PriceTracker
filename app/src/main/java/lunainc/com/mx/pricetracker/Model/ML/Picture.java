package lunainc.com.mx.pricetracker.Model.ML;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Picture implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("secure_url")
    @Expose
    private String secure_url;

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("max_size")
    @Expose
    private String max_size;

    @SerializedName("quality")
    @Expose
    private String quality;

    public Picture() {
    }

    public Picture(String id, String url, String secure_url, String size, String max_size, String quality) {
        this.id = id;
        this.url = url;
        this.secure_url = secure_url;
        this.size = size;
        this.max_size = max_size;
        this.quality = quality;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecure_url() {
        return secure_url;
    }

    public void setSecure_url(String secure_url) {
        this.secure_url = secure_url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMax_size() {
        return max_size;
    }

    public void setMax_size(String max_size) {
        this.max_size = max_size;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
