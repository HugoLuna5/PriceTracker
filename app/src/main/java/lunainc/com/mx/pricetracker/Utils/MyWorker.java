package lunainc.com.mx.pricetracker.Utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import lunainc.com.mx.pricetracker.Connect.APIService;
import lunainc.com.mx.pricetracker.Connect.ApiUtils;
import lunainc.com.mx.pricetracker.Model.ML.ProductML;
import lunainc.com.mx.pricetracker.Model.Product;
import lunainc.com.mx.pricetracker.UI.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWorker extends Worker {

    private static final String WORK_RESULT = "work_result";
    private APIService mAPIService;
    private DBHelper db;
    private ArrayList<Product> arrayList;

    private Data outputData;
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = new DBHelper(context);
        mAPIService = ApiUtils.getAPIService();
    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);

        arrayList = db.getProducts();

        for (int i = 0; i < arrayList.size() ; i++) {
            Product product = arrayList.get(i);
            mAPIService.getDataProductML(product.getId_product()).enqueue(new Callback<ProductML>() {
                @Override
                public void onResponse(Call<ProductML> call, Response<ProductML> response) {
                    if (response.isSuccessful()){

                        String price = String.valueOf(response.body().getPrice());
                        db.createPrice(product.getId(), price);
                        db.updateProduct("status", response.body().getStatus(), String.valueOf(product.getId()));


                    }else {
                        Log.e("Error: ", response.message());
                    }
                }

                @Override
                public void onFailure(Call<ProductML> call, Throwable t) {

                }
            });
        }
        


        return Result.success(outputData);

    }

}
