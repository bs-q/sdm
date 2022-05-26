package q.sdm.ui.main.home;

import androidx.lifecycle.LiveData;

import java.util.List;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.fragment.BaseFragmentViewModel;

public class HomeViewModel extends BaseFragmentViewModel {
    LiveData<List<ProductEntity>> productEntityLiveData;
    public HomeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void observeProductsInCart(){
        productEntityLiveData = repository.getSqliteService().loadAllProductToLiveData();
    }
}
