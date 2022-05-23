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
import q.sdm.ui.main.account.AccountFragment;
import q.sdm.ui.main.cart.CartFragment;
import q.sdm.ui.main.home.HomeFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private Fragment active;
    private static final String HOME = "HOME";
    private static final String CART = "CART";
    private static final String ACCOUNT = "ACCOUNT";

    private HomeFragment homeFragment;
    private CartFragment cartFragment;
    private AccountFragment accountFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setMainActivity(this);
        viewBinding.setMainViewModel(viewModel);
        fm = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        active = homeFragment;
        fm.beginTransaction().add(R.id.nav_host_fragment,homeFragment,HOME).commitNow();
        viewBinding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    fm.beginTransaction().hide(active).show(homeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    active = homeFragment;
                    return true;
                case R.id.cart:
                    if (cartFragment == null){
                        cartFragment = new CartFragment();
                        fm.beginTransaction().add(R.id.nav_host_fragment, cartFragment, CART).hide(active).commit();
                    }  else {
                        fm.beginTransaction().hide(active).show(cartFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    }
                    active = cartFragment;
                    return true;
                case R.id.account:
                    if (accountFragment == null){
                        accountFragment = new AccountFragment();
                        fm.beginTransaction().add(R.id.nav_host_fragment, accountFragment, ACCOUNT).hide(active).commit();
                    } else {
                        fm.beginTransaction().hide(active).show(accountFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    }
                    active = accountFragment;
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
