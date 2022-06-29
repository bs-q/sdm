package q.sdm.ui.main.cart.order.detail;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.order.OrderHistoryDetailResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.ActivityOrderDetailBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.main.cart.adapter.CartAdapter;
import q.sdm.ui.main.cart.order.detail.adapter.OrderDetailAdapter;
import q.sdm.ui.product.ProductDetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding,OrderDetailViewModel> implements OrderDetailAdapter.OrderDetailAdapterCallback
, View.OnClickListener {

    public static OrderHistoryDetailResponse historyDetailResponse;
    private OrderDetailAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
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
    protected void onDestroy() {
        super.onDestroy();
        historyDetailResponse = null;
    }

    @Override
    public void sneakyBinding() {
        super.sneakyBinding();
        viewBinding.setDefaultName(myApplication().getProfileResponse().getCustomerFullName());
        viewBinding.setDefaultPhone(myApplication().getProfileResponse().getCustomerPhone());
        viewBinding.setOrder(historyDetailResponse);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        setupCartAdapter();
        viewModel.checkCancelTime();
    }
    private void setupCartAdapter(){
        adapter = new OrderDetailAdapter();
        List<ProductEntity>  productEntityList = historyDetailResponse.getOrdersDetailDtos()
                .stream().map(o-> {
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.setId(o.getId());
                    productEntity.setName(o.getProductDto().getProductName());
                    productEntity.setAmount(o.getAmount());
                    productEntity.setPrice(o.getPrice());
                    return productEntity;
                }).collect(Collectors.toList());
        viewModel.totalAndVat.set(historyDetailResponse.getOrdersTotalMoney());
        viewModel.total.set(viewModel.totalAndVat.get()/1.1/(1-(double)historyDetailResponse.getOrdersSaleOff()/100));
        viewModel.sale.set(String.valueOf(historyDetailResponse.getOrdersSaleOff()));
        viewModel.reduce.set(viewModel.total.get()*historyDetailResponse.getOrdersSaleOff()/100);
        viewModel.vat.set((viewModel.total.get()-viewModel.reduce.get())*historyDetailResponse.getOrdersVat()/100);
        viewModel.vatPercent.set(String.valueOf(historyDetailResponse.getOrdersVat().intValue()));
        adapter.productEntities = productEntityList;
        adapter.callback = this;
        viewBinding.fcRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewBinding.fcRv.setAdapter(adapter);
    }

    @Override
    public void productCallback(ProductEntity productEntity) {
    }

    @Override
    public void onClick(View v) {
        viewModel.cancelOrder(new BaseRequestCallback<Boolean>() {
            @Override
            public void doSuccess(Boolean response) {
                finish();
            }
        });
    }
}
