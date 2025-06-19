package com.example.ecommerceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartProduct> cartProductList;
    private List<Product> allProducts; // All fetched products to get full info by productId

    public CartAdapter(Context context, List<CartProduct> cartProductList, List<Product> allProducts) {
        this.context = context;
        this.cartProductList = cartProductList;
        this.allProducts = allProducts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct cartProduct = cartProductList.get(position);

        // Find the product details by matching ID
        for (Product product : allProducts) {
            if (product.getId() == cartProduct.getProductId()) {
                holder.title.setText(product.getTitle());
                holder.price.setText("Price: ₹" + product.getPrice());
                holder.quantity.setText("Qty: " + cartProduct.getQuantity());
                holder.total.setText("Total: ₹" + (product.getPrice() * cartProduct.getQuantity()));
                Glide.with(context).load(product.getImage()).into(holder.image);
                break;
            }
        }

        // Delete button action
        holder.deleteBtn.setOnClickListener(v -> {
            cartProductList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartProductList.size());
        });
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price, quantity, total;
        Button deleteBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartProductImage);
            title = itemView.findViewById(R.id.cartProductTitle);
            price = itemView.findViewById(R.id.cartProductPrice);
            quantity = itemView.findViewById(R.id.cartQuantity);
            total = itemView.findViewById(R.id.cartTotalPrice);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
