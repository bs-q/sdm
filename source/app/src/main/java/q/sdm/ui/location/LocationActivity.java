package q.sdm.ui.location;

import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityLoginBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;

public class LocationActivity extends BaseActivity<ActivityLoginBinding,LocationViewModel>
    implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_location;
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

    }
}
