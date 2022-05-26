package q.sdm.ui.category;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.databinding.ActivityCategoryBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;

import android.os.Bundle;
import android.view.View;

public class CategoryActivity extends BaseActivity<ActivityCategoryBinding,CategoryViewModel> implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_category;
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
