package lunainc.com.mx.pricetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.R;
import lunainc.com.mx.pricetracker.Utils.DBHelper;

public class CreateProductActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nameProduct)
    TextInputEditText nameProduct;

    @BindView(R.id.idProduct)
    TextInputEditText idProduct;

    @BindView(R.id.priceActual)
    TextInputEditText priceActual;

    @BindView(R.id.url)
    TextInputEditText url;

    @BindView(R.id.actionCreateProduct)
    ExtendedFloatingActionButton actionCreateProduct;


    private DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        ButterKnife.bind(this);
        configView();
    }

    private void configView() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        db = new DBHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        events();
    }

    private void events() {

        actionCreateProduct.setOnClickListener(v -> {
            String id = idProduct.getText().toString();
            String name = nameProduct.getText().toString();
            String price = priceActual.getText().toString();
            String urlTx = url.getText().toString();

            db.createProduct(id, name, price, urlTx, "ML", "active");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

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