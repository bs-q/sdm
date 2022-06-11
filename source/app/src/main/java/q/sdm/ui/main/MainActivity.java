package q.sdm.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityMainBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.main.home.HomeFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private HomeFragment homeFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setMainActivity(this);
        viewBinding.setMainViewModel(viewModel);
        fm = getSupportFragmentManager();
        homeFragment = viewBinding.home.getFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariable() {
        return BR.mainViewModel;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void showFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(viewBinding.generic.getId(),fragment,null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }
    int backPressCount = 0;
    @Override
    public void onBackPressed() {
        if (viewBinding.generic.getFragment() != null) {
            getSupportFragmentManager().beginTransaction().remove(viewBinding.generic.getFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
        } else if (backPressCount == 0){
            Toast.makeText(this,"Chạm lần nữa để thoát",Toast.LENGTH_SHORT).show();
            backPressCount +=1;
            viewBinding.getRoot().postDelayed(()->{
                //reset backPress
                backPressCount = 0;
            },2000);
        } else if (backPressCount == 1){
            super.onBackPressed();
        }
    }
}
