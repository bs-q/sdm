package q.sdm.ui.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import q.sdm.R;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.LayoutSearchResultBinding;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder> {
    
    public interface SearchAdapterCallback{
        void searchItemClick(ProductResponse result);
    }
    
    private List<ProductResponse> productResponses = new ArrayList<>();
    private SearchAdapterCallback callback;
    
    public SearchAdapter(SearchAdapterCallback callback){
        this.callback = callback;
    }
    
    public void updateProducts(List<ProductResponse> productResponses){
        this.productResponses = productResponses;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public SearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        LayoutSearchResultBinding layoutSearchResultBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_search_result,parent,false
        );
        
        return new SearchAdapterViewHolder(layoutSearchResultBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapterViewHolder holder, int position) {
        holder.layoutSearchResultBinding.setProduct(productResponses.get(position));
        holder.layoutSearchResultBinding.getRoot().setOnClickListener((v)->{
            callback.searchItemClick(productResponses.get(position));
        });
        holder.layoutSearchResultBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return productResponses.size();
    }

    public static class SearchAdapterViewHolder extends RecyclerView.ViewHolder {
        
        LayoutSearchResultBinding layoutSearchResultBinding;
        
        public SearchAdapterViewHolder(@NonNull LayoutSearchResultBinding layoutSearchResultBinding) {
            super(layoutSearchResultBinding.getRoot());
            this.layoutSearchResultBinding = layoutSearchResultBinding;
        }
    }
}
