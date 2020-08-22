package lunainc.com.mx.pricetracker.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.Constraints;
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

public class MainActivity extends AppCompatActivity implements ProductsAdapter.ItemClickListener, ProductsAdapter.ItemLongClickListener {

    public static final String MESSAGE_STATUS = "message_status";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    
    @BindView(R.id.recylcerView)
    RecyclerView recyclerView;
    
    @BindView(R.id.actionAdd)
    FloatingActionButton actionAdd;
    
    private DBHelper db;
    private ArrayList<Product> arrayList;
    private ProductsAdapter productsAdapter;
    private WorkManager mWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configView();
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
        mWorkManager = WorkManager.getInstance();
        productsAdapter = new ProductsAdapter(this, arrayList);
        productsAdapter.notifyDataSetChanged();
        productsAdapter.setClickListener(this);
        productsAdapter.setLongClickListener(this);
        recyclerView.setAdapter(productsAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        events();
        periodicWork();
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

        refresh.setOnRefreshListener(() -> {
            reloadData();
            refresh.setRefreshing(false);
        });
        
    }




    private void reloadData() {
        arrayList = db.getProducts();
        productsAdapter = new ProductsAdapter(this, arrayList);
        productsAdapter.notifyDataSetChanged();
        productsAdapter.setClickListener(this);
        productsAdapter.setLongClickListener(this);
        recyclerView.setAdapter(productsAdapter);

    }

    private void deleteProduct(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Realmente desea eliminar este producto?")
                .setTitle("Eliminar producto")
                .setPositiveButton("Aceptar", (dialog, id) -> {

                    db.deleteProduct(String.valueOf(arrayList.get(position).getId()));
                    reloadData();

                })
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());
        builder.create();
        builder.show();

    }

    @Override
    public void onItemClick(View view, int position) {
        Product product = arrayList.get(position);
        Intent intent = new Intent(this, ShowProductActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        deleteProduct(position);
    }


    public void periodicWork() {
        PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 6, TimeUnit.HOURS)
                .setConstraints(Constraints.NONE)
                .build();

        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                WorkInfo.State state = workInfo.getState();


            }
        });
        mWorkManager.enqueue(mRequest);
    }

}