package q.sdm.ui.main.cart.edit;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseSheetViewModel;

public class CartSheetViewModel extends BaseSheetViewModel {
    public CartSheetViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public ObservableInt quantity = new ObservableInt(0);
    public ObservableField<String> quantityString = new ObservableField<>("0");

    public void deleteProduct(ProductEntity productEntity){
        compositeDisposable.add(repository.getSqliteService().deleteProduct(productEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
    public void updateProduct(ProductEntity productEntity, BaseDbCallback<Long> callback){
        compositeDisposable.add(repository.getSqliteService()
                .insertProduct(productEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        callback::doSuccess,callback::doError
                ));
    }

    public void getProductDetail(Long id, BaseRequestCallback<ProductResponse> callback){
        compositeDisposable.add(repository.getApiService().productDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response-> {
                            if (response.isResult()){
                                callback.doSuccess(response.getData());
                            } else {
                                callback.doFail(response.getMessage(), response.getCode());
                            }
                        },throwable -> {
                            callback.doError(throwable,this);
                        }
                ));
    }
}
