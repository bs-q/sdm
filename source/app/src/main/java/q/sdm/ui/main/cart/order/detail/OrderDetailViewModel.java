package q.sdm.ui.main.cart.order.detail;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.response.order.OrderHistoryDetailResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class OrderDetailViewModel extends BaseViewModel {
    public ObservableDouble total = new ObservableDouble(0);
    public ObservableDouble vat = new ObservableDouble(0);
    public ObservableDouble totalAndVat = new ObservableDouble(0);
    public ObservableDouble reduce = new ObservableDouble(0);
    public ObservableField<String> sale = new ObservableField<>("0");
    public OrderDetailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void getOrderDetail(Long id,BaseRequestCallback<OrderHistoryDetailResponse> callback) {
        compositeDisposable.add(repository.getApiService()
        .orderHistoryDetail(id).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
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
