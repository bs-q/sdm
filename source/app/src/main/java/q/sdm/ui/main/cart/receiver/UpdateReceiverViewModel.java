package q.sdm.ui.main.cart.receiver;

import androidx.databinding.ObservableField;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.fragment.BaseSheetViewModel;

public class UpdateReceiverViewModel extends BaseSheetViewModel {
    public ObservableField<String> receiverName = new ObservableField<>("");
    public ObservableField<String> receiverPhone = new ObservableField<>("");
    public ObservableField<String> receiverAddress = new ObservableField<>("");
    public UpdateReceiverViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
