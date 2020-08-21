package lunainc.com.mx.pricetracker.Utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import lunainc.com.mx.pricetracker.Connect.APIService;
import lunainc.com.mx.pricetracker.Connect.ApiUtils;
import lunainc.com.mx.pricetracker.UI.MainActivity;

public class MyWorker extends Worker {

    private static final String WORK_RESULT = "work_result";
    private APIService mAPIService;
    private Data outputData;
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);

        mAPIService = ApiUtils.getAPIService();



        //mAPIService.getDataProductML();

        return Result.success(outputData);

    }

}
