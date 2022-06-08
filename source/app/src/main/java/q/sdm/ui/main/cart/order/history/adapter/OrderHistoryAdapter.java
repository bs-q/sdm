package q.sdm.ui.main.cart.order.history.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import q.sdm.R;
import q.sdm.data.model.api.response.order.OrderHistoryResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.LayoutOrderHistoryItemBinding;
import q.sdm.databinding.LayoutOrderHistoryItemBinding;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryAdapterViewHolder> {

    public List<OrderHistoryResponse> orderHistoryResponses = new ArrayList<>();
    public interface OrderHistoryAdapterCallback{
        void orderItemClick(OrderHistoryResponse response);
    }
    public OrderHistoryAdapterCallback callback;
    public String defaultName;
    public String defaultPhone;
    @NonNull
    @Override
    public OrderHistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutOrderHistoryItemBinding layoutOrderHistoryItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_order_history_item,parent,false
        );
        return new OrderHistoryAdapterViewHolder(layoutOrderHistoryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapterViewHolder holder, int position) {
        holder.layoutOrderHistoryItemBinding.setOrder(orderHistoryResponses.get(position));
        holder.layoutOrderHistoryItemBinding.setDefaultName(defaultName);
        holder.layoutOrderHistoryItemBinding.setDefaultPhone(defaultPhone);
        holder.layoutOrderHistoryItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.orderItemClick(holder.layoutOrderHistoryItemBinding.getOrder());
            }
        });
        holder.layoutOrderHistoryItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return orderHistoryResponses.size();
    }

    public static class OrderHistoryAdapterViewHolder extends RecyclerView.ViewHolder {
        LayoutOrderHistoryItemBinding layoutOrderHistoryItemBinding;
        public OrderHistoryAdapterViewHolder(@NonNull LayoutOrderHistoryItemBinding layoutOrderHistoryItemBinding) {
            super(layoutOrderHistoryItemBinding.getRoot());
            this.layoutOrderHistoryItemBinding = layoutOrderHistoryItemBinding;
        }
    }
}
