package q.sdm.ui.main.setting;

import android.view.View;

import androidx.databinding.library.baseAdapters.BR;

import q.sdm.R;
import q.sdm.databinding.FragmentSettingBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;

public class SettingFragment extends BaseFragment<FragmentSettingBinding,SettingViewModel> implements
        View.OnClickListener {
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void performDataBinding() {

    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {

    }


}
