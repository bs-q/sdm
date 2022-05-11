package q.sdm.ui.main.revenue;

import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.FragmentRevenueBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;

public class RevenueFragment extends BaseFragment<FragmentRevenueBinding,RevenueViewModel>
        implements View.OnClickListener{

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_revenue;
    }

    @Override
    protected void performDataBinding() {

    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }


    @Override
    public void onClick(View v) {

    }
}
