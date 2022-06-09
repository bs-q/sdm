package q.sdm.ui.recovery;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.account.ResetPasswordRequest;
import q.sdm.data.model.api.response.account.ResetPasswordResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class RecoveryViewModel extends BaseViewModel {

    public ObservableBoolean validEmail = new ObservableBoolean(false);
    public ObservableField<String> email = new ObservableField<>("");

    public RecoveryViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void requestRecovery(BaseRequestCallback<ResetPasswordResponse> callback){
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setEmail(email.get());
        showLoading();
        compositeDisposable.add(repository.getApiService().resetPassword(request)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    if (response.isResult()) {
                        hideLoading();
                        callback.doSuccess(response.getData());
                    } else {
                        callback.doFail(response.getMessage(), response.getCode());
                        hideLoading();
                    }
                },throwable -> {
                    callback.doError(throwable,this);
                        }
                ));
    }
}
