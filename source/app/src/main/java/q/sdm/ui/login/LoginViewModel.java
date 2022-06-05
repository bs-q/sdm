package q.sdm.ui.login;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.LoginRequest;
import q.sdm.ui.base.activity.BaseCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    /** true : email active; false: password active
     * **/
    public ObservableBoolean emailState = new ObservableBoolean(true);
    public ObservableBoolean passwordState = new ObservableBoolean(false);
    public ObservableBoolean toggle = new ObservableBoolean(true); // toggle to show password, true : hidden ; false : reveal

    /***
     * state of valid email icon, true : check; false : cross
     * ***/
    public ObservableBoolean valid = new ObservableBoolean(false);


    public ObservableBoolean showSplashScreen = new ObservableBoolean(true);
    public LoginViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void login(BaseCallback callback){
        showLoading();
        LoginRequest request = new LoginRequest();
        request.setPhoneOrEmail(email.get());
        request.setPassword(password.get());
        repository.getApiService().login(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    if (response.isResult()){
                        repository.setToken(response.getData().getToken());
                        profile(callback);
                    } else {
                        callback.doFail();
                    }
                },throwable -> callback.doError(throwable,this));
    }
    public void profile(BaseCallback callback){
        compositeDisposable.add(repository.getApiService().profile()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response.isResult()){
                        application.setProfileResponse(response.getData());
                        callback.doSuccess();
                    }
                },throwable -> {
                    showSplashScreen.set(false);
                    callback.doError(throwable,this);
                }
        ));
    }
}
