package q.sdm.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityMainBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.main.news.AccountFragment;
import q.sdm.ui.main.revenue.RevenueFragment;
import q.sdm.ui.main.setting.SettingFragment;
import q.sdm.ui.main.store.StoreFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private Fragment active;
    private static final String STORE = "STORE";
    private static final String REVENUE = "REVENUE";
    private static final String NEWS = "NEWS";
    private static final String SETTING = "SETTING";

    private StoreFragment storeFragment;
    private RevenueFragment revenueFragment;
    private AccountFragment accountFragment;
    private SettingFragment settingFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setMainActivity(this);
        viewBinding.setMainViewModel(viewModel);
        fm = getSupportFragmentManager();
        storeFragment = new StoreFragment();
        active = storeFragment;
        fm.beginTransaction().add(R.id.nav_host_fragment,storeFragment,STORE).commitNow();
        viewBinding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.store:
                    fm.beginTransaction().hide(active).show(storeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    active = storeFragment;
                    return true;
                case R.id.revenue:
                    if (revenueFragment == null){
                        revenueFragment = new RevenueFragment();
                        fm.beginTransaction().add(R.id.nav_host_fragment, revenueFragment, REVENUE).hide(active).commit();
                    }  else {
                        fm.beginTransaction().hide(active).show(revenueFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    }
                    active = revenueFragment;
                    return true;
                case R.id.news:
                    if (accountFragment == null){
                        accountFragment = new AccountFragment();
                        fm.beginTransaction().add(R.id.nav_host_fragment, accountFragment, NEWS).hide(active).commit();
                    } else {
                        fm.beginTransaction().hide(active).show(accountFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    }
                    active = accountFragment;
                    return true;
                case R.id.settings:
                    if (settingFragment == null){
                        settingFragment = new SettingFragment();
                        fm.beginTransaction().add(R.id.nav_host_fragment, settingFragment, SETTING).hide(active).commit();
                    } else {
                        fm.beginTransaction().hide(active).show(settingFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    }
                    active = settingFragment;
                    return true;
                default:
                    break;
            }
            return false;
        });

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
}
