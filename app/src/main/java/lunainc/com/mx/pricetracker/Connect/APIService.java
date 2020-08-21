package lunainc.com.mx.pricetracker.Connect;

import lunainc.com.mx.pricetracker.Model.ML.ProductML;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {


    @GET("/items/{id}")
    Call<ProductML> getDataProductML(@Path("id") String id);

}
