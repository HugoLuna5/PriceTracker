package lunainc.com.mx.pricetracker.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.Model.ML.ProductML;
import lunainc.com.mx.pricetracker.Model.Price;
import lunainc.com.mx.pricetracker.Model.Product;
import lunainc.com.mx.pricetracker.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder>{

    private List<Price> prices;
    private Context context;
    private LayoutInflater mInflater;

    public PriceAdapter(Context context, List<Price> prices){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.prices = prices;

    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_price, parent, false);
        return new PriceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {

        Price price = prices.get(position);
        holder.price.setText("Precio: "+price.getPrice()+ " MXN");
        holder.datetime.setText("Consultado: "+price.getChecked());







    }

    @Override
    public int getItemCount() {
        return prices.size();
    }


    public class PriceViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.datetime)
        TextView datetime;


        PriceViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }



    }

}
