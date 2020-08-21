package lunainc.com.mx.pricetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.R;

public class MainActivity extends AppCompatActivity {

    
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
}