package q.sdm.ui.main.cart.order.history;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.ResponseListObj;
import q.sdm.data.model.api.response.order.OrderHistoryDetailResponse;
import q.sdm.data.model.api.response.order.OrderHistoryResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class OrderHistoryViewModel extends BaseViewModel {
    public OrderHistoryViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    ResponseListObj<OrderHistoryResponse> orderList;
    public void getOrderHistory(Integer size,Integer page,BaseRequestCallback<List<OrderHistoryResponse>> callback) {
        compositeDisposable.add(repository.getApiService().orderHistory(size,page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                response ->{
                    if (orderList == null ){
                        orderList = response.getData();
                    } else {
                        orderList.copy(response.getData());
                    }
                    callback.doSuccess(orderList.getData());
                },throwable -> {
                    callback.doError(throwable,this);
                        }
                ));
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
