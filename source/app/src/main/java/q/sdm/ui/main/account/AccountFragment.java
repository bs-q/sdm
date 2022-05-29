package q.sdm.ui.main.account;

import android.content.Intent;
import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.FragmentAccountBinding;
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.login.LoginActivity;

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
        } else if (v.getId() == binding.lai.avatarEdit.getId()){

        }

    }

    private void changeAvatar(){

    }

    private void navigateToLogin(){
        Intent it = new Intent(requireContext(), LoginActivity.class);
        startActivity(it);
    }
}
