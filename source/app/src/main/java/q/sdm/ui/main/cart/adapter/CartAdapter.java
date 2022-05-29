package q.sdm.ui.main.cart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import q.sdm.R;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.LayoutProductItemBinding;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder> {
    public interface CartAdapterCallback {
        void productCallback(ProductEntity productEntity);
        void deleteProduct(ProductEntity productEntity);
    }
    public List<ProductEntity> productEntities = new ArrayList<>();
    public CartAdapterCallback callback;

    @NonNull
    @Override
    public CartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutProductItemBinding layoutProductItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_product_item,parent,false
        );
        return new CartAdapterViewHolder(layoutProductItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapterViewHolder holder, int position) {
        holder.layoutProductItemBinding.setProduct(productEntities.get(position));
        holder.layoutProductItemBinding.setClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteProduct(holder.layoutProductItemBinding.getProduct());
            }
        });
        holder.layoutProductItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.productCallback(holder.layoutProductItemBinding.getProduct());
            }
        });
        holder.layoutProductItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return productEntities.size();
    }

    public static class CartAdapterViewHolder extends RecyclerView.ViewHolder {
        LayoutProductItemBinding layoutProductItemBinding;
        public CartAdapterViewHolder(@NonNull LayoutProductItemBinding layoutProductItemBinding) {
            super(layoutProductItemBinding.getRoot());
            this.layoutProductItemBinding = layoutProductItemBinding;
        }
    }
}
