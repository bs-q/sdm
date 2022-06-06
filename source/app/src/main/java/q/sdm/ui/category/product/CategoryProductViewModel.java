package q.sdm.ui.category.product;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class CategoryProductViewModel extends BaseViewModel {
    public CategoryProductViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void getProductsByCategory(Long categoryId, BaseRequestCallback<List<ProductResponse>> callback){
        compositeDisposable.add(repository.getApiService()
        .productsCategory(categoryId).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response.getData().getData());
                    } else {
                        callback.doFail(response.getMessage(), response.getCode());
                    }
                },throwable -> {
                    callback.doError(throwable,this);
                }
        ));
    }
}
