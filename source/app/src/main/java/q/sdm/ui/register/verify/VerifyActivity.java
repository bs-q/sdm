package q.sdm.ui.register.verify;

import eu.davidea.flexibleadapter.databinding.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityVerifyBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;

public class VerifyActivity extends BaseActivity<ActivityVerifyBinding,VerifyViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
