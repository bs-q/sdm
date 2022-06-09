package q.sdm.ui.register.verify;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.account.ResetPasswordRequest;
import q.sdm.data.model.api.response.account.ResetPasswordResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;
import q.sdm.ui.base.activity.MessageWrapper;

public class VerifyViewModel extends BaseViewModel {
    public ObservableField<String> otp1 = new ObservableField<>("");
    public ObservableField<String> otp2 = new ObservableField<>("");
    public ObservableField<String> otp3 = new ObservableField<>("");
    public ObservableField<String> otp4 = new ObservableField<>("");
    public ObservableField<String> countdown = new ObservableField<>("00:00:30s");
    public ObservableBoolean lockResend = new ObservableBoolean(false);

    public ObservableBoolean validOtp = new ObservableBoolean(false);
    public VerifyViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public String getOtp(){
        return otp1.get()+otp2.get()+otp3.get()+otp4.get();
    }

    public void resendReset(BaseRequestCallback<ResetPasswordResponse> callback){
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setEmail(application.getEmail());
        showLoading();
        compositeDisposable.add(repository.getApiService().resetPassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        response -> {
                            if (response.isResult()) {
                                hideLoading();
                                callback.doSuccess(response.getData());
                                startTimer();
                            } else {
                                callback.doFail(response.getMessage(), response.getCode());
                                hideLoading();
                            }
                        },throwable -> {
                            callback.doError(throwable,this);
                        }
                ));
    }
    public void startTimer(){
        lockResend.set(true);
        compositeDisposable.add(Observable.interval(30, TimeUnit.SECONDS,
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    response ->{
                        Long remain = 30 - response;
                        String remainString = String.valueOf(remain);
                        String remainParse = "00:00:"+remainString+"s";
                        countdown.set(remainParse);
                    },throwable -> {

                },()->{
                        lockResend.set(false);
                }
        ));
    }
}
