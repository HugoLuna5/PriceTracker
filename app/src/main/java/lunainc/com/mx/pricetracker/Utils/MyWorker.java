package lunainc.com.mx.pricetracker.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import lunainc.com.mx.pricetracker.Connect.APIService;
import lunainc.com.mx.pricetracker.Connect.ApiUtils;
import lunainc.com.mx.pricetracker.Model.ML.ProductML;
import lunainc.com.mx.pricetracker.Model.Product;
import lunainc.com.mx.pricetracker.R;
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

        Log.e("Worker", "GG");
        for (int i = 0; i < arrayList.size() ; i++) {
            Product product = arrayList.get(i);
            mAPIService.getDataProductML(product.getId_product()).enqueue(new Callback<ProductML>() {
                @Override
                public void onResponse(Call<ProductML> call, Response<ProductML> response) {
                    if (response.isSuccessful()){

                        String price = String.valueOf(response.body().getPrice());

                        if (Integer.parseInt(product.getPrice().replaceAll(",", "")) != Integer.parseInt(price.replaceAll(",", ""))){
                            db.createPrice(product.getId(), price);
                            db.updateProduct("status", response.body().getStatus(), String.valueOf(product.getId()));
                            sendNotification(product.getName(), "Tu producto "+product.getName()+" ha cambiado de precio, su precio ahora es "+price);
                        }




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

    private void sendNotification(String productName, String body){
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_check);
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "pricetrackernotification";
            CharSequence name = "pricetrackernotification";
            String description = "Channel description";


            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);
            Objects.requireNonNull(notificationManager).createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "pricetrackernotification")
                .setContentTitle("El producto "+productName+" ha cambiado de precio")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_check)
                .setLargeIcon(bitmap)
                .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                .setContentText(body)
                .setGroup("pricetrackernotify")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);


        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(resultPendingIntent);
        Objects.requireNonNull(notificationManager).notify(0, notification.build());

    }

}
