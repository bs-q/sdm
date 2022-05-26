package q.sdm.ui.register.verify;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.activity.BaseViewModel;

public class VerifyViewModel extends BaseViewModel {
    public ObservableField<String> otp1 = new ObservableField<>("");
    public ObservableField<String> otp2 = new ObservableField<>("");
    public ObservableField<String> otp3 = new ObservableField<>("");
    public ObservableField<String> otp4 = new ObservableField<>("");

    public ObservableBoolean validOtp = new ObservableBoolean(false);
    public VerifyViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
