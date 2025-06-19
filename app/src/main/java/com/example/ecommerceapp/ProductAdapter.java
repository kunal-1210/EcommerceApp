package com.example.ecommerceapp;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private List<Product> fullList;

    public void updateData(List<Product> newList) {
        this.productList = newList;  // Replace old data
        notifyDataSetChanged();      // Refresh the RecyclerView
    }

    public void updateList(List<Product> newList) {
        productList = newList;
        notifyDataSetChanged();
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.fullList = new ArrayList<>(productList);
    }
    public void filter(String text) {
        productList.clear();
        if (text.isEmpty()) {
            productList.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (Product item : fullList) {
                if (item.getTitle().toLowerCase().contains(text) ||
                    item.getCategory().toLowerCase().contains(text)) {
                    productList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle, productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productTitle.setText(product.getTitle());
        holder.productPrice.setText("₹" + product.getPrice());

        Glide.with(context)
            .load(product.getImage())
            .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("product", productList.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

