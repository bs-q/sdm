package q.sdm.ui.register.complete;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityRegisterCompleteBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.login.LoginActivity;
import q.sdm.ui.main.MainActivity;

public class RegisterCompleteActivity extends BaseActivity<ActivityRegisterCompleteBinding,RegisterCompleteViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_register_complete;
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
        navigateToLogin();
    }

    private void navigateToLogin(){
        Intent it = new Intent(this, LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(it);
    }
}
