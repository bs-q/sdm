package q.sdm.ui.main.cart.added.edit;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.fragment.BaseSheetViewModel;

public class ProductAddedEditViewModel extends BaseSheetViewModel {
    public ProductAddedEditViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public ObservableInt amount = new ObservableInt(0);
    public ObservableDouble total = new ObservableDouble(0);
    LiveData<List<ProductEntity>> productEntityLiveData;

    public void observeProductsInCart(){
        productEntityLiveData = repository.getSqliteService().loadAllProductToLiveData();
    }
    public void updateAllProduct(List<ProductEntity> productEntities, BaseDbCallback<Boolean> callback) {
        compositeDisposable.add(repository.getSqliteService().insertAllProduct(productEntities)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response) {
                        callback.doSuccess(response);
                    }
                },throwable -> {
                    callback.doError(throwable);
                }
        ));
    }
}
