package q.sdm.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.databinding.LayoutCategoryBinding;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {

    List<CategoryResponse> categoryResponseList;

    @NonNull
    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCategoryBinding layoutCategoryBinding = LayoutCategoryBinding.inflate(LayoutInflater.from(
                parent.getContext()
        ),parent,false);
        return new CategoryAdapterViewHolder(layoutCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterViewHolder holder, int position) {
        holder.layoutCategoryBinding.setThumbnail(categoryResponseList.get(position).getThumbnail());
        holder.layoutCategoryBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return categoryResponseList.size();
    }

    public static class CategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        LayoutCategoryBinding layoutCategoryBinding;
        public CategoryAdapterViewHolder(@NonNull LayoutCategoryBinding itemView) {
            super(itemView.getRoot());
            layoutCategoryBinding = itemView;
        }
    }
}
