package q.sdm.ui.payment;

import androidx.databinding.ObservableBoolean;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.fragment.BaseSheetViewModel;

public class SelectPaymentViewModel extends BaseSheetViewModel {

    /** type of payment
     * true -> cash; false -> paypal
     */
    public ObservableBoolean type = new ObservableBoolean(true);

    public SelectPaymentViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);

    }
}
