package q.sdm.ui.main.news;

import android.view.View;

import androidx.databinding.library.baseAdapters.BR;

import q.sdm.R;
import q.sdm.databinding.FragmentNewsBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;

public class NewsFragment extends BaseFragment<FragmentNewsBinding,NewsViewModel> implements
        View.OnClickListener{
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
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
