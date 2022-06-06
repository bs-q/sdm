package q.sdm.ui.main.account.email;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityUpdateEmailBinding;
import q.sdm.databinding.ActivityUpdateUsernameBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.main.account.name.UpdateNameViewModel;
import q.sdm.ui.main.account.request.RequestPasswordSheet;

public class UpdateEmailActivity extends BaseActivity<ActivityUpdateEmailBinding, UpdateEmailViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_update_email;
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
        if (myApplication().getProfileResponse().getCustomerEmail()!=null){
            viewModel.email.set(myApplication().getProfileResponse().getCustomerEmail());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.close.getId()){
            viewModel.email.set("");
        } else if (v.getId() == viewBinding.done.getId()){
            updateName();
        }
    }
    private void updateName(){
        if (viewModel.checkName()){
            RequestPasswordSheet requestPasswordSheet = new RequestPasswordSheet(viewModel.email.get(),myApplication().getProfileResponse().getCustomerFullName());
            requestPasswordSheet.show(getSupportFragmentManager(),"REQUEST_PWD");
        }
    }
}
