package q.sdm.ui.main.cart;

import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.databinding.FragmentCartBinding;
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;

public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> {
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void performDataBinding() {
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setCustomerFullName("QUAN");
        profileResponse.setCustomerAddress("ABC duong D xa E");
        binding.setA(this);
        binding.setVm(viewModel);
        binding.setProfile(profileResponse);
        binding.executePendingBindings();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {

    }
}
