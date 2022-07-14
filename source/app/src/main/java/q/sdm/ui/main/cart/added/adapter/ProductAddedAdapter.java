package q.sdm.ui.main.cart.added.adapter;

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
import q.sdm.databinding.LayoutProductAddedBinding;
import q.sdm.databinding.LayoutProductItemBinding;

public class ProductAddedAdapter extends RecyclerView.Adapter<ProductAddedAdapter.ProductAddedAdapterViewHolder> {

    public List<ProductEntity> productEntities = new ArrayList<>();
    public interface ProductAddedAdapterCallback{
        void delete(ProductEntity productEntity);
    }
    public ProductAddedAdapter(ProductAddedAdapterCallback callback){
        this.callback = callback;
    }
    private ProductAddedAdapterCallback callback;
    @NonNull
    @Override
    public ProductAddedAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutProductAddedBinding layoutProductAddedBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_product_added,parent,false
        );
        return new ProductAddedAdapterViewHolder(layoutProductAddedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAddedAdapterViewHolder holder, int position) {
        holder.layoutProductAddedBinding.setProduct(productEntities.get(position));
        holder.layoutProductAddedBinding.delete.setOnClickListener(v->{
            callback.delete(productEntities.get(position));
        });
        holder.layoutProductAddedBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return productEntities.size();
    }

    public static class ProductAddedAdapterViewHolder extends RecyclerView.ViewHolder {
        LayoutProductAddedBinding layoutProductAddedBinding;
        public ProductAddedAdapterViewHolder(@NonNull LayoutProductAddedBinding layoutProductAddedBinding) {
            super(layoutProductAddedBinding.getRoot());
            this.layoutProductAddedBinding = layoutProductAddedBinding;
        }
    }
}
