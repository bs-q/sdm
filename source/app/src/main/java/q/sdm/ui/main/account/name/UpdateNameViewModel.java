package q.sdm.ui.main.account.name;

import androidx.databinding.ObservableField;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.activity.BaseViewModel;

public class UpdateNameViewModel extends BaseViewModel {
    public ObservableField<String> name = new ObservableField<>("");
    public UpdateNameViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public boolean checkName(){
        if (name.get().trim().isEmpty()) {
            showErrorMessage("Vui lòng nhập tên hiển thị");
            return false;
        }
        return true;
    }
}
