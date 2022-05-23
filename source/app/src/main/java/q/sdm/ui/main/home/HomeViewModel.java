package q.sdm.ui.main.home;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.ui.base.fragment.BaseFragmentViewModel;

public class HomeViewModel extends BaseFragmentViewModel {
    public HomeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
