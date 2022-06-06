package q.sdm.ui.main.account.password;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.databinding.ActivityChangePasswordBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class UpdatePasswordActivity extends BaseActivity<ActivityChangePasswordBinding,UpdatePasswordViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
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

    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.done.getId()){
            updatePassword();
        }
    }
    private void updatePassword(){
        if (viewModel.checkForm()){
            viewModel.showLoading();
            viewModel.updateProfile(myApplication().getProfileResponse().getCustomerFullName(), new BaseRequestCallback<ProfileResponse>() {
                @Override
                public void doSuccess(ProfileResponse response) {
                    viewModel.hideLoading();
                    viewModel.showSuccessMessage("Đổi mật khẩu thành công");
                    finish();
                }
            });
        }
    }
}
