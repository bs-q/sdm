package q.sdm.ui.main.account;

import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;

public class AccountFragment extends BaseFragment<FragmentHomeBinding, AccountViewModel> {
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
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
