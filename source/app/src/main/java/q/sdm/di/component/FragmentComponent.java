package q.sdm.di.component;


import dagger.Component;
import q.sdm.di.module.FragmentModule;
import q.sdm.di.scope.FragmentScope;
import q.sdm.ui.main.news.NewsFragment;
import q.sdm.ui.main.revenue.RevenueFragment;
import q.sdm.ui.main.setting.SettingFragment;
import q.sdm.ui.main.store.StoreFragment;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(RevenueFragment fragment);

    void inject(StoreFragment fragment);

    void inject(NewsFragment fragment);

    void inject(SettingFragment fragment);
}
