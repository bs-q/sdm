package q.sdm.ui.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.LayoutProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder> {
    public interface ProductAdapterClick{
        void productItemClick(ProductResponse productResponse);
    }
    List<ProductResponse> productResponseList = new ArrayList<>();

    private ProductAdapterClick callback;

    public ProductAdapter(ProductAdapterClick callback){
        this.callback = callback;
    }

    public void updateProducts(List<ProductResponse> productResponseList){
        this.productResponseList = productResponseList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutProductBinding layoutProductBinding = LayoutProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductAdapterViewHolder(layoutProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterViewHolder holder, int position) {
        holder.layoutProductBinding.setName(productResponseList.get(position).getProductName());
        holder.layoutProductBinding.setThumbnail(productResponseList.get(position).getProductImage());
        holder.layoutProductBinding.setMoney(productResponseList.get(position).getProductPrice());
        holder.layoutProductBinding.getRoot().setOnClickListener((v)->{
            callback.productItemClick(productResponseList.get(position));
        });
        holder.layoutProductBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public static class ProductAdapterViewHolder extends RecyclerView.ViewHolder {
        LayoutProductBinding layoutProductBinding;
        public ProductAdapterViewHolder(@NonNull  LayoutProductBinding layoutProductBinding) {
            super(layoutProductBinding.getRoot());
            this.layoutProductBinding = layoutProductBinding;
        }
    }
}
