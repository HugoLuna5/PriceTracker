package lunainc.com.mx.pricetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.R;
import lunainc.com.mx.pricetracker.Utils.MyWorker;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_STATUS = "message_status";
    private WorkManager mWorkManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    
    @BindView(R.id.recylcerView)
    RecyclerView recyclerView;
    
    @BindView(R.id.actionAdd)
    FloatingActionButton actionAdd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configView();
        mWorkManager = WorkManager.getInstance();
        periodicWork();
    }

    private void configView() {
        setSupportActionBar(toolbar);
       // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        events();
    }

    private void events() {
        
        actionAdd.setOnClickListener( v -> {
            Intent intent = new Intent(this, ShowProductActivity.class);

            
        });
        
    }

    public void periodicWork() {
        PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 6, TimeUnit.HOURS)
                .build();

        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                WorkInfo.State state = workInfo.getState();
                // Toast.makeText(mContext, state.toString() + "\n", Toast.LENGTH_SHORT).show();

            }
        });
        mWorkManager.enqueue(mRequest);
    }

}