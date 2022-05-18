package q.sdm.ui.main.news;

import android.content.Intent;
import android.view.View;

import androidx.databinding.library.baseAdapters.BR;

import q.sdm.R;
import q.sdm.databinding.FragmentAccountBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.login.LoginActivity;

public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel> implements
        View.OnClickListener{
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
        navigateToLogin();
    }
    private void navigateToLogin(){
        Intent it = new Intent(requireActivity(), LoginActivity.class);
        startActivity(it);
    }
}
