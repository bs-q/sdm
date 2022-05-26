package q.sdm.ui.recovery;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.activity.BaseViewModel;

public class RecoveryViewModel extends BaseViewModel {

    public ObservableBoolean validEmail = new ObservableBoolean(false);
    public ObservableField<String> email = new ObservableField<>("");

    public RecoveryViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
