package q.sdm.ui.register;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.register.RegisterRequest;
import q.sdm.ui.base.activity.BaseCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class RegisterViewModel extends BaseViewModel {
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");

    /** true : email active;
     * **/
    public ObservableBoolean emailState = new ObservableBoolean(false);
    /** true : password active;
     * **/
    public ObservableBoolean passwordState = new ObservableBoolean(false);
    /** true : name active;
     * **/
    public ObservableBoolean nameState = new ObservableBoolean(true);
    public ObservableBoolean toggle = new ObservableBoolean(true); // toggle to show password, true : hidden ; false : reveal

    /***
     * state of valid email icon, true : check; false : cross
     * ***/
    public ObservableBoolean valid = new ObservableBoolean(false);
    public RegisterViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void register(BaseCallback callback){
        showLoading();
        RegisterRequest request = new RegisterRequest();
        request.setName(name.get());
        request.setEmail(email.get());
        request.setPassword(password.get());

        compositeDisposable.add(repository.getApiService().register(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    hideLoading();
                    callback.doSuccess();
                },throwable -> {
                    callback.doError(throwable, this);
                }));

    }
}
