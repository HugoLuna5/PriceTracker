package lunainc.com.mx.pricetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.Adapter.PriceAdapter;
import lunainc.com.mx.pricetracker.Adapter.ProductsAdapter;
import lunainc.com.mx.pricetracker.Model.Price;
import lunainc.com.mx.pricetracker.Model.Product;
import lunainc.com.mx.pricetracker.R;
import lunainc.com.mx.pricetracker.Utils.DBHelper;

public class ShowProductActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.url)
    TextView url;

    @BindView(R.id.recylcerView)
    RecyclerView recyclerView;

    private Product product;

    private ArrayList<Price> prices;

    private DBHelper db;

    private PriceAdapter priceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        ButterKnife.bind(this);
        initVars();
    }

    private void initVars() {
        product = (Product) getIntent().getSerializableExtra("product");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        db = new DBHelper(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prices = db.getPrices(product.getId());

        priceAdapter = new PriceAdapter(this, prices);
        priceAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(priceAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        name.setText("Nombre del producto: "+product.getName());
        price.setText("Precio inicial del producto: "+product.getPrice());
        url.setText("URL del producto: "+product.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}