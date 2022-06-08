package q.sdm.ui.main.cart.order.detail.adapter;

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

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailAdapterViewHolder> {
    public interface OrderDetailAdapterCallback {
        void productCallback(ProductEntity productEntity);
    }
    public List<ProductEntity> productEntities = new ArrayList<>();
    public OrderDetailAdapterCallback callback;

    @NonNull
    @Override
    public OrderDetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutProductItemBinding layoutProductItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_product_item,parent,false
        );
        return new OrderDetailAdapterViewHolder(layoutProductItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapterViewHolder holder, int position) {
        holder.layoutProductItemBinding.setProduct(productEntities.get(position));
        holder.layoutProductItemBinding.lpiDelete.setVisibility(View.GONE);
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

    public static class OrderDetailAdapterViewHolder extends RecyclerView.ViewHolder {
        LayoutProductItemBinding layoutProductItemBinding;
        public OrderDetailAdapterViewHolder(@NonNull LayoutProductItemBinding layoutProductItemBinding) {
            super(layoutProductItemBinding.getRoot());
            this.layoutProductItemBinding = layoutProductItemBinding;
        }
    }
}
