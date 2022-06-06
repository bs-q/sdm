package q.sdm.ui.main.account.request;

import android.net.Uri;

import androidx.databinding.ObservableField;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.account.UpdateProfileRequest;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseSheetViewModel;

public class RequestPasswordViewModel extends BaseSheetViewModel {

    public ObservableField<String> password = new ObservableField<>("");
    public boolean checkForm(){

        if (password.get().isEmpty()) {
            showErrorMessage("Mật khẩu hiện tại được để trống");
            return false;
        }
        if (password.get().length() < 8) {
            showErrorMessage("Mật khẩu hiện tại phải có ít nhất 8 kí tự");
            return false;
        }

        return true;
    }
    public RequestPasswordViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void updateImage(String username,Uri img, BaseRequestCallback<ProfileResponse> callback){
        File f = new File(img.getPath());

        RequestBody reqFile = RequestBody.create(f, MediaType.parse("image/*"));
        RequestBody type = RequestBody.create("AVATAR",MediaType.parse("text/plain")
        );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", f.getName(), reqFile);
        compositeDisposable.add(
                repository.getApiService().fileUpload(body,type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response-> {
                                    if (response.isResult()){
                                        updateProfile(username,null,response.getData().getFilePath(),callback);
                                    } else {
                                        callback.doFail(response.getMessage(), response.getCode());
                                    }
                                }, throwable -> callback.doError(throwable,this)
                        )
        );
    }
    public void updateProfile(String username,String email,String avatarPath,BaseRequestCallback<ProfileResponse> callback){
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setOldPassword(password.get());
        request.setCustomerFullName(username);
        if (avatarPath != null){
            request.setCustomerAvatarPath(avatarPath);
        }
        if (email!=null){
            request.setCustomerEmail(email);
        }
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
}
