package q.sdm.di.component;

import dagger.Component;
import q.sdm.di.module.ActivityModule;
import q.sdm.di.scope.ActivityScope;
import q.sdm.ui.main.MainActivity;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}

