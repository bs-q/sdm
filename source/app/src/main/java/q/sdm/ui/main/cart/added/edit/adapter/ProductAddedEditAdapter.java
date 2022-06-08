package q.sdm.ui.main.cart.added.edit.adapter;

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
import q.sdm.databinding.LayoutEditProductAddedBinding;
import q.sdm.databinding.LayoutProductAddedBinding;

public class ProductAddedEditAdapter extends RecyclerView.Adapter<ProductAddedEditAdapter.ProductAddedEditAdapterViewHolder> {
    public interface Refresh{
        void refreshList();
    }
    public List<ProductEntity> productEntities = new ArrayList<>();
    public Refresh callback;
    @NonNull
    @Override
    public ProductAddedEditAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutEditProductAddedBinding layoutEditProductAddedBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_edit_product_added,parent,false
        );
        return new ProductAddedEditAdapterViewHolder(layoutEditProductAddedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAddedEditAdapterViewHolder holder, int position) {
        holder.layoutEditProductAddedBinding.setProduct(productEntities.get(position));
        holder.layoutEditProductAddedBinding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.layoutEditProductAddedBinding.getProduct().getObservableAmount().get()==1){
                    // do nothing
                } else {
                    holder.layoutEditProductAddedBinding.getProduct().getObservableAmount().set(holder.layoutEditProductAddedBinding.getProduct().getObservableAmount().get()-1);
                    callback.refreshList();
                }
            }
        });
        holder.layoutEditProductAddedBinding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.layoutEditProductAddedBinding.getProduct().getObservableAmount().get()==holder.layoutEditProductAddedBinding.getProduct().quantityInStock){
                    // do nothing
                } else {
                    holder.layoutEditProductAddedBinding.getProduct().getObservableAmount().set(holder.layoutEditProductAddedBinding.getProduct().getObservableAmount().get()+1);
                    callback.refreshList();
                }
            }
        });
        holder.layoutEditProductAddedBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return productEntities.size();
    }

    public static class ProductAddedEditAdapterViewHolder extends RecyclerView.ViewHolder {
        LayoutEditProductAddedBinding layoutEditProductAddedBinding;
        public ProductAddedEditAdapterViewHolder(@NonNull LayoutEditProductAddedBinding layoutEditProductAddedBinding) {
            super(layoutEditProductAddedBinding.getRoot());
            this.layoutEditProductAddedBinding = layoutEditProductAddedBinding;
        }
    }
}
