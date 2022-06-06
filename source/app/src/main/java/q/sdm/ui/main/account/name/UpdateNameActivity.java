package q.sdm.ui.main.account.name;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityUpdateUsernameBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.main.account.request.RequestPasswordSheet;

public class UpdateNameActivity extends BaseActivity<ActivityUpdateUsernameBinding,UpdateNameViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_update_username;
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
        viewModel.name.set(myApplication().getProfileResponse().getCustomerFullName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.close.getId()){
            viewModel.name.set("");
        } else if (v.getId() == viewBinding.done.getId()){
            updateName();
        }
    }
    private void updateName(){
        if (viewModel.checkName()){
            RequestPasswordSheet requestPasswordSheet = new RequestPasswordSheet(viewModel.name.get());
            requestPasswordSheet.show(getSupportFragmentManager(),"REQUEST_PWD");
        }
    }
}
