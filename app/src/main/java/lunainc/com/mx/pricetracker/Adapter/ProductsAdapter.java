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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.pricetracker.Connect.APIService;
import lunainc.com.mx.pricetracker.Connect.ApiUtils;
import lunainc.com.mx.pricetracker.Model.ML.ProductML;
import lunainc.com.mx.pricetracker.Model.Product;
import lunainc.com.mx.pricetracker.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Product> products;
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    private APIService apiService;


    public ProductsAdapter(Context context, List<Product> products){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.products = products;
        apiService = ApiUtils.getAPIService();

    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.desc.setText(product.getUrl());
        holder.cantidad.setText("Precio inicial: "+product.getPrice()+ " MXN");


        apiService.getDataProductML(product.getId_product()).enqueue(new Callback<ProductML>() {
            @Override
            public void onResponse(Call<ProductML> call, Response<ProductML> response) {

                if (response.isSuccessful()){

                    String imageURL = response.body().getPictures().get(1).getUrl();
                    //Picasso.get().load(imageURL).into(holder.image);
                    Glide.with(context).load(imageURL).into(holder.image);


                }else {
                    Log.e("Error: ", response.message());
                }

            }

            @Override
            public void onFailure(Call<ProductML> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });





    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.desc)
        TextView desc;

        @BindView(R.id.cantidad)
        TextView cantidad;

        @BindView(R.id.image)
        ImageView image;

        ProductViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mClickListener != null){
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongClickListener != null){
                mLongClickListener.onItemLongClick(v, getAdapterPosition());
            }

            return false;
        }


    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


}
