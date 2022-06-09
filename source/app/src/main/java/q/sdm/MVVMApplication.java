package q.sdm;

import android.app.Application;
import android.app.Dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import lombok.Setter;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.di.component.AppComponent;
import q.sdm.di.component.DaggerAppComponent;
import q.sdm.others.MyTimberDebugTree;
import q.sdm.others.MyTimberReleaseTree;
import q.sdm.utils.DialogUtils;
import timber.log.Timber;

public class MVVMApplication extends Application{
    @Setter
    private AppCompatActivity currentActivity;

    @Getter
    private AppComponent appComponent;

    @Getter
    @Setter
    private ProductResponse productDetailItem;

    @Getter
    @Setter
    private List<ProductResponse> searchResults;

    @Getter
    @Setter
    private ObservableInt totalItemInCart = new ObservableInt(0);

    @Getter
    @Setter
    private ObservableField<String> customerLocation = new ObservableField<>("...");

    @Getter
    @Setter
    private ProfileResponse profileResponse;

    @Getter
    @Setter
    private ProductEntity editProduct;

    @Getter
    @Setter
    private CategoryResponse selectedCategory;


    // recovery flow
    @Getter
    @Setter
    private String idHash;
    @Getter
    @Setter
    private String otp;

    @Getter
    @Setter
    private String email;
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable firebase log
        FirebaseCrashlytics firebaseCrashlytics = FirebaseCrashlytics.getInstance();
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(true);


        if (BuildConfig.DEBUG) {
            Timber.plant(new MyTimberDebugTree());
        }else{
            Timber.plant(new MyTimberReleaseTree(firebaseCrashlytics));
        }

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
        appComponent.inject(this);

        // Init Toasty
        Toasty.Config.getInstance()
                .allowQueue(false)
                .apply();


        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleObserver() {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            void onMoveToForeground(){

            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            void onMoveToBackground(){

            }
        });

    }


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public PublishSubject<Integer> showDialogNoInternetAccess(){
        Timber.d("No wifi");
        final PublishSubject<Integer> subject = PublishSubject.create();
        currentActivity.runOnUiThread(() ->
                {
                    Dialog dialog =  DialogUtils.dialogConfirm(currentActivity, currentActivity.getResources().getString(R.string.newtwork_error),
                            null,
                            null, currentActivity.getResources().getString(R.string.newtwork_error_button_exit),
                            (dialogInterface, i) -> this.currentActivity.finishAffinity());
                    compositeDisposable.add( subject.doOnComplete(() -> {
                        if (dialog!=null){
                            dialog.dismiss();
                        }
                    }).subscribe(retry -> {}));
                }

        );
        return subject;
    }
}
