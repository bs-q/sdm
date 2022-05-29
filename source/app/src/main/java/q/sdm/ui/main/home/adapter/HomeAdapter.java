package q.sdm.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import q.sdm.R;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.LayoutCategoryBarBinding;
import q.sdm.databinding.LayoutProductBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {

    public interface HomeAdapterClick{
        void productItemClick(ProductResponse productResponse);
    }

    List<ProductResponse> productResponseList = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    private HomeAdapterClick productCallback;
    private CategoryAdapter.CategoryAdapterClick categoryCallback;

    public HomeAdapter(HomeAdapterClick productCallback, CategoryAdapter.CategoryAdapterClick categoryCallback){
        this.productCallback = productCallback;
        this.categoryCallback = categoryCallback;
        categoryAdapter = new CategoryAdapter(categoryCallback);
    }

    public void updateCategory(List<CategoryResponse> categoryResponseList){
        this.categoryAdapter.categoryResponseList = categoryResponseList;
        this.categoryAdapter.notifyDataSetChanged();
    }

    public void updateProducts(List<ProductResponse> productResponseList){
        this.productResponseList = productResponseList;
        this.notifyDataSetChanged();
    }

    public HomeAdapter(){
    }


    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            LayoutCategoryBarBinding layoutCategoryBarBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_category_bar,parent,false);
            layoutCategoryBarBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HomeAdapterViewHolder(layoutCategoryBarBinding);
        }
        LayoutProductBinding layoutProductBinding = LayoutProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HomeAdapterViewHolder(layoutProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (viewType == 0) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
            holder.layoutCategoryBarBinding.rv.setAdapter(categoryAdapter);
            holder.layoutCategoryBarBinding.rv.setLayoutManager(new LinearLayoutManager(holder.layoutCategoryBarBinding.getRoot().getContext(),LinearLayoutManager.HORIZONTAL,false));
            holder.layoutCategoryBarBinding.executePendingBindings();

        } else {
            holder.layoutProductBinding.setName(productResponseList.get(position-1).getProductName());
            holder.layoutProductBinding.setThumbnail(productResponseList.get(position-1).getProductImage());
            holder.layoutProductBinding.setMoney(productResponseList.get(position-1).getProductPrice());
            holder.layoutProductBinding.getRoot().setOnClickListener((v)->{
                productCallback.productItemClick(productResponseList.get(position-1));
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        else return 1;
    }

    @Override
    public int getItemCount() {
        return productResponseList.size() + 1;
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {

        LayoutCategoryBarBinding layoutCategoryBarBinding;
        LayoutProductBinding layoutProductBinding;
        public HomeAdapterViewHolder(@NonNull LayoutCategoryBarBinding layoutCategoryBarBinding) {
            super(layoutCategoryBarBinding.getRoot());
            this.layoutCategoryBarBinding = layoutCategoryBarBinding;
        }
        public HomeAdapterViewHolder(@NonNull LayoutProductBinding layoutProductBinding) {
            super(layoutProductBinding.getRoot());
            this.layoutProductBinding = layoutProductBinding;
        }
    }
}
