package q.sdm.ui.main.home;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseFragmentViewModel;

public class HomeViewModel extends BaseFragmentViewModel {
    LiveData<List<ProductEntity>> productEntityLiveData;
    public HomeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void observeProductsInCart(){
        productEntityLiveData = repository.getSqliteService().loadAllProductToLiveData();
    }

    public void getCategories(BaseRequestCallback<List<CategoryResponse>> callback){
        compositeDisposable.add(repository.getApiService().category()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response->{
            if (response.isResult()){
                callback.doSuccess(response.getData().getData());
            } else {
                callback.doFail(response.getMessage(),response.getCode());
            }
        },throwable -> {
            callback.doError(throwable,this);
        }));
    }

    public void getProducts(Integer size,Integer page,BaseRequestCallback<List<ProductResponse>> callback){
        compositeDisposable.add(repository.getApiService().products(size,page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response.isResult()){
                        callback.doSuccess(response.getData().getData());
                    } else {
                        callback.doFail(response.getMessage(),response.getCode());
                    }
                },throwable -> {
                    callback.doError(throwable,this);
                }
        ));
    }
}
