package q.sdm.ui.search;

import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class SearchViewModel extends BaseViewModel {

    public ObservableField<String> searchQuery = new ObservableField<>("");
    public PublishSubject<String> queryThrottle = PublishSubject.create();
    public List<ProductResponse> productResponses = new ArrayList<>();

    public SearchViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void listenToAutoComplete(BaseRequestCallback<List<ProductResponse>> callback){
        compositeDisposable.add(
                queryThrottle.debounce(500, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe(
                        result ->{
                            searchProducts(result,callback);
                        }
                )
        );
    }
    private void searchProducts(String query,BaseRequestCallback<List<ProductResponse>> callback){
        compositeDisposable.add(repository.getApiService().searchProducts(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response ->{
                    if (response.isResult()) {
                        callback.doSuccess(response.getData().getData());
                    } else {
                        callback.doFail(response.getMessage(), response.getCode());
                    }
                }, throwable -> {
                    callback.doError(throwable,this);
                }
        ));
    }
}
