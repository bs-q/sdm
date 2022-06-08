package q.sdm.ui.main.account;

import android.content.Intent;
import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.FragmentAccountBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.location.LocationActivity;
import q.sdm.ui.login.LoginActivity;
import q.sdm.ui.main.account.detail.AccountDetailActivity;
import q.sdm.ui.main.cart.order.history.OrderHistoryActivity;

public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel> {
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
        binding.setA(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.fcLogout.getId()){
            viewModel.logout();
            navigateToLogin();
        } else if (v.getId() == binding.fcAccountBtn.getId()){
            navigateToAccountDetail();
        } else if (v.getId() == binding.fcLocationBtn.getId()) {
            navigateToAddressList();
        } else if (v.getId() == binding.fcOrderBtn.getId()) {
            navigateToOrderHistory();
        }

    }

    private void navigateToOrderHistory(){
        Intent it = new Intent(requireContext(), OrderHistoryActivity.class);
        startActivity(it);
    }
    private void navigateToAddressList(){
        Intent it = new Intent(requireContext(), LocationActivity.class);
        startActivity(it);
    }



    private void navigateToAccountDetail(){
        Intent it = new Intent(requireContext(), AccountDetailActivity.class);
        startActivity(it);
    }

    private void navigateToLogin(){
        Intent it = new Intent(requireContext(), LoginActivity.class);
        startActivity(it);
        requireActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.lai.setEmail(myApplication().getProfileResponse().getCustomerEmail());
        binding.lai.setUsername(myApplication().getProfileResponse().getCustomerFullName());
        binding.lai.setAvatar(myApplication().getProfileResponse().getAvatar());
        binding.lai.executePendingBindings();

    }
}
