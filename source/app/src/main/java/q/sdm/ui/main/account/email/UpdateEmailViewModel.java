package q.sdm.ui.main.account.email;

import static q.sdm.constant.Constants.VALID_EMAIL_ADDRESS_REGEX;

import androidx.databinding.ObservableField;

import java.util.Objects;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.activity.BaseViewModel;

public class UpdateEmailViewModel extends BaseViewModel {
    public ObservableField<String> email = new ObservableField<>("");
    public UpdateEmailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public boolean checkName(){
        if (email.get().trim().isEmpty()) {
            showErrorMessage("Vui lòng nhập email");
            return false;
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(
                Objects.requireNonNull(email.get())
        ).find()){
            showErrorMessage("Email không hợp lệ");
            return false;
        }
        return true;
    }
}
