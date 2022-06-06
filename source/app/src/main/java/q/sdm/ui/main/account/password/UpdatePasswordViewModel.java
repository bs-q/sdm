package q.sdm.ui.main.account.password;

import androidx.databinding.ObservableField;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.account.UpdateProfileRequest;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class UpdatePasswordViewModel extends BaseViewModel {
    public ObservableField<String> oldPassword = new ObservableField<>("");
    public ObservableField<String> newPassword = new ObservableField<>("");
    public ObservableField<String> confirmPassword = new ObservableField<>("");

    public boolean checkForm(){
        if (oldPassword.get().trim().isEmpty() || oldPassword.get().trim().length()<8) {
            showErrorMessage("Mật khẩu cũ phải có ít nhất 8 kí tự");
            return false;
        }
        if (newPassword.get().trim().isEmpty() || newPassword.get().trim().length()<8) {
            showErrorMessage("Mật khẩu mới phải có ít nhất 8 kí tự");
            return false;
        }
        if (confirmPassword.get().trim().isEmpty() || confirmPassword.get().trim().length()<8) {
            showErrorMessage("Mật khẩu xác nhận phải có ít nhất 8 kí tự");
            return false;
        }
        if (!confirmPassword.get().equals(newPassword.get())) {
            showErrorMessage("Mật khẩu xác nhận không chính xác");
            return false;
        }
        return true;
    }
    public void updateProfile(String username,BaseRequestCallback<ProfileResponse> callback){
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setOldPassword(oldPassword.get());
        request.setCustomerFullName(username);
        request.setCustomerPassword(newPassword.get());
        compositeDisposable.add(repository.getApiService().updateProfile(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(response -> repository.getApiService().profile().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()))
                .subscribe(
                        response -> {
                            if (response.isResult()) {
                                callback.doSuccess(response.getData());
                            } else {
                                callback.doFail(response.getMessage(), response.getCode());
                            }
                        },throwable -> {
                            callback.doError(throwable,this);
                        }
                ));

    }


    public UpdatePasswordViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
