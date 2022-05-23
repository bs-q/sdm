package q.sdm.ui.main.home;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.main.home.adapter.HomeAdapter;

public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel> {

    private HomeAdapter homeAdapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
        homeAdapter = new HomeAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 2;
                }
                return 1;
            }
        });

        binding.rv.setLayoutManager(gridLayoutManager);
        binding.rv.setAdapter(homeAdapter);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {

    }
}
