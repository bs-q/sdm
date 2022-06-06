package q.sdm.ui.location.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import q.sdm.R;
import q.sdm.data.model.api.response.address.AddressResponse;
import q.sdm.databinding.LayoutAddLocationBinding;
import q.sdm.databinding.LayoutLocationItemBinding;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationAdapterViewHolder> {

    public List<AddressResponse> addressResponseList = new ArrayList<>();
    public interface LocationAdapterCallback{
        void addLocation();
        void setDefaultLocation(AddressResponse addressResponse);
    }
    public LocationAdapterCallback callback;
    @NonNull
    @Override
    public LocationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            LayoutAddLocationBinding  layoutAddLocationBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.layout_add_location,parent,false
            );
            return new LocationAdapterViewHolder(layoutAddLocationBinding);
        }
        LayoutLocationItemBinding layoutLocationItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_location_item,parent,false
        );
        return new LocationAdapterViewHolder(layoutLocationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapterViewHolder holder, int position) {
        if (holder.getItemViewType() == 1) {
            holder.layoutAddLocationBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.addLocation();
                }
            });
            holder.layoutAddLocationBinding.executePendingBindings();
        } else {
            holder.layoutLocationItemBinding.setLocation(addressResponseList.get(position));
            holder.layoutLocationItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.setDefaultLocation(holder.layoutLocationItemBinding.getLocation());
                }
            });
            holder.layoutLocationItemBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 1 : add item; 0 : location item
        if (addressResponseList.isEmpty()) return 1;
        if (position == addressResponseList.size()) return 1;
        return 0;

    }

    @Override
    public int getItemCount() {
        return addressResponseList.size()+1;
    }

    public static class LocationAdapterViewHolder extends RecyclerView.ViewHolder {

        LayoutLocationItemBinding layoutLocationItemBinding;
        LayoutAddLocationBinding  layoutAddLocationBinding;

        public LocationAdapterViewHolder(@NonNull LayoutLocationItemBinding layoutLocationItemBinding) {
            super(layoutLocationItemBinding.getRoot());
            this.layoutLocationItemBinding = layoutLocationItemBinding;
        }
        public LocationAdapterViewHolder(@NonNull LayoutAddLocationBinding  layoutAddLocationBinding) {
            super(layoutAddLocationBinding.getRoot());
            this.layoutAddLocationBinding = layoutAddLocationBinding;
        }
    }
}
