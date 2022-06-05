package q.sdm.ui.main.cart;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableLong;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.activity.BaseViewModel;
import q.sdm.ui.base.fragment.BaseFragmentViewModel;

public class CartViewModel extends BaseViewModel {
    public ObservableDouble total = new ObservableDouble(0);
    public ObservableDouble vat = new ObservableDouble(0);
    public ObservableDouble totalAndVat = new ObservableDouble(0);
    LiveData<List<ProductEntity>> productEntityLiveData;
    public CartViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void observeProductsInCart(){
        productEntityLiveData = repository.getSqliteService().loadAllProductToLiveData();
    }

    public void deleteProduct(ProductEntity productEntity){
        compositeDisposable.add(repository.getSqliteService().deleteProduct(productEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe());
    }
}
