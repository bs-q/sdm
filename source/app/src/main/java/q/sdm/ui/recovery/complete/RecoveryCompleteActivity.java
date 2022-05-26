package q.sdm.ui.recovery.complete;

import android.content.Intent;
import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityRecoveryCompleteBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.login.LoginActivity;

public class RecoveryCompleteActivity extends BaseActivity<ActivityRecoveryCompleteBinding,RecoveryCompleteViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_recovery_complete;
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
    public void onClick(View v) {
        navigateToLogin();
    }
    private void navigateToLogin(){
        Intent it = new Intent(this, LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(it);
    }
}

