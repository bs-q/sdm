package q.sdm.ui.main.cart.added;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;

import java.util.List;

import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.activity.BaseViewModel;

public class ProductAddedViewModel extends BaseViewModel {

    public ObservableInt amount = new ObservableInt(0);
    public ObservableDouble total = new ObservableDouble(0);
    LiveData<List<ProductEntity>> productEntityLiveData;

    public void observeProductsInCart(){
        productEntityLiveData = repository.getSqliteService().loadAllProductToLiveData();
    }

    public ProductAddedViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
