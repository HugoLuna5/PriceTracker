package lunainc.com.mx.pricetracker.Utils;

import android.util.Log;

import androidx.multidex.MultiDexApplication;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import lunainc.com.mx.pricetracker.UI.MainActivity;

public class PriceTracker extends MultiDexApplication {
    private WorkManager mWorkManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkManager = WorkManager.getInstance();
        periodicWork();
    }


    public void periodicWork() {
        PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 6, TimeUnit.HOURS)
                .setConstraints(Constraints.NONE)
                .build();
        mWorkManager.enqueue(mRequest);
    }
}
