package q.sdm.ui.main.account.detail;

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
import q.sdm.data.model.api.response.upload.UploadResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;
import timber.log.Timber;

public class AccountDetailViewModel extends BaseViewModel {
    public ObservableField<String> username = new ObservableField<>("");
    public ObservableField<String> oldPassword = new ObservableField<>("");
    public ObservableField<String> newPassword = new ObservableField<>("");

    public boolean checkForm(){
        if (username.get().isEmpty()) {
            showErrorMessage("Tên hiển thị không được để trống");
            return false;
        }
        if (oldPassword.get().isEmpty()) {
            showErrorMessage("Mật khẩu cũ được để trống");
            return false;
        }
        if (oldPassword.get().length() < 8) {
            showErrorMessage("Mật khẩu cũ phải có ít nhất 8 kí tự");
            return false;
        }
        if (!newPassword.get().isEmpty() && newPassword.get().length() < 8) {
            showErrorMessage("Mật khẩu mới phải có ít nhất 8 kí tự");
            return false;
        }
        return true;
    }



    public AccountDetailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

}
