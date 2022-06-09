package q.sdm.ui.recovery.input;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.account.UpdatePasswordRequest;
import q.sdm.data.model.api.response.account.UpdatePasswordResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class InputPasswordViewModel extends BaseViewModel {

    public ObservableBoolean validPassword = new ObservableBoolean(false);
    public ObservableField<String> password = new ObservableField<>("");


    public InputPasswordViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void updatePassword(BaseRequestCallback<UpdatePasswordResponse> callback){
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setNewPassword(password.get());
        request.setIdHash(application.getIdHash());
        request.setOtp(application.getOtp());
        showLoading();
        compositeDisposable.add(repository.getApiService().updatePassword(request)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response.isResult()){
                        hideLoading();
                        callback.doSuccess(response.getData());
                    } else {
                        hideLoading();
                        callback.doFail(response.getMessage(), response.getCode());
                    }
                },throwable -> {
                    callback.doError(throwable,this);
                }
        ));
    }
}
