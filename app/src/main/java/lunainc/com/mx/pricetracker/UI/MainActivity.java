package lunainc.com.mx.pricetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.Adapter.ProductsAdapter;
import lunainc.com.mx.pricetracker.Model.Product;
import lunainc.com.mx.pricetracker.R;
import lunainc.com.mx.pricetracker.Utils.DBHelper;
import lunainc.com.mx.pricetracker.Utils.MyWorker;

public class MainActivity extends AppCompatActivity implements ProductsAdapter.ItemClickListener {

    public static final String MESSAGE_STATUS = "message_status";
    private WorkManager mWorkManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    
    @BindView(R.id.recylcerView)
    RecyclerView recyclerView;
    
    @BindView(R.id.actionAdd)
    FloatingActionButton actionAdd;
    
    private DBHelper db;
    private ArrayList<Product> arrayList;
    private ProductsAdapter productsAdapter;
    
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
        db = new DBHelper(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayList = loadData();

        productsAdapter = new ProductsAdapter(this, arrayList);
        productsAdapter.notifyDataSetChanged();
        productsAdapter.setClickListener(this);
        //productsAdapter.setLongClickListener(this);
        recyclerView.setAdapter(productsAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        events();

    }

    private ArrayList<Product> loadData() {
        return db.getProducts();
    }

    private void events() {
        
        actionAdd.setOnClickListener( v -> {
            Intent intent = new Intent(this, CreateProductActivity.class);
            startActivity(intent);
            finish();
            
        });
        
    }

    public void periodicWork() {
        PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 6, TimeUnit.HOURS)
                .build();

        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                WorkInfo.State state = workInfo.getState();

            }
        });
        mWorkManager.enqueue(mRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        Product product = arrayList.get(position);
        Intent intent = new Intent(this, ShowProductActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
        finish();
    }
}