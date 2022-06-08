package q.sdm.ui.main.cart.order.complete;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.databinding.ActivityOrderCompleteBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.main.MainActivity;
import q.sdm.ui.main.cart.order.history.OrderHistoryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class OrderCompleteActivity extends BaseActivity<ActivityOrderCompleteBinding,OrderCompleteViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_complete;
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
    }

    private void navigateToHome(){
        Intent it = new Intent(this, MainActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(it);
    }

    private void navigateToOrder(){
        Intent it = new Intent(this, OrderHistoryActivity.class);
        startActivity(it);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.backToHome.getId()) {
            navigateToHome();
        } else if (v.getId() == viewBinding.goToOrder.getId()) {
            navigateToOrder();
        }
    }
}
