package q.sdm.ui.main.cart.order.history;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.order.OrderHistoryDetailResponse;
import q.sdm.data.model.api.response.order.OrderHistoryResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.ActivityOrderHistoryBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.main.cart.added.adapter.ProductAddedAdapter;
import q.sdm.ui.main.cart.order.detail.OrderDetailActivity;
import q.sdm.ui.main.cart.order.history.adapter.OrderHistoryAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryActivity extends BaseActivity<ActivityOrderHistoryBinding,OrderHistoryViewModel>
        implements View.OnClickListener, OrderHistoryAdapter.OrderHistoryAdapterCallback {

    private OrderHistoryAdapter orderHistoryAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_history;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        setupCartAdapter();
        getFirstOrderList();
    }
    private void getFirstOrderList(){
        viewModel.getOrderHistory(10, 0, new BaseRequestCallback<List<OrderHistoryResponse>>() {
            @Override
            public void doSuccess(List<OrderHistoryResponse> response) {
                orderHistoryAdapter.orderHistoryResponses = response;
                orderHistoryAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setupCartAdapter(){
        orderHistoryAdapter = new OrderHistoryAdapter();
        orderHistoryAdapter.callback = this;
        orderHistoryAdapter.defaultName = myApplication().getProfileResponse().getCustomerFullName();
        orderHistoryAdapter.defaultPhone = myApplication().getProfileResponse().getCustomerPhone();
        viewBinding.rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewBinding.rv.setAdapter(orderHistoryAdapter);
        viewBinding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && viewModel.orderList.hasNext()) { //1 for down
                    viewModel.getOrderHistory(10, viewModel.orderList.getNext(), new BaseRequestCallback<List<OrderHistoryResponse>>() {
                        @Override
                        public void doSuccess(List<OrderHistoryResponse> response) {
                            orderHistoryAdapter.orderHistoryResponses = response;
                            orderHistoryAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void orderItemClick(OrderHistoryResponse response) {
        viewModel.showLoading();
        viewModel.getOrderDetail(response.getId(), new BaseRequestCallback<OrderHistoryDetailResponse>() {
            @Override
            public void doSuccess(OrderHistoryDetailResponse response) {
                viewModel.hideLoading();
                Intent it = new Intent(OrderHistoryActivity.this, OrderDetailActivity.class);
                OrderDetailActivity.historyDetailResponse = response;
                startActivity(it);
            }
        });

    }
}
