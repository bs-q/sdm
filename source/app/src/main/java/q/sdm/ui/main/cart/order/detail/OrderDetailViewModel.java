package q.sdm.ui.main.cart.order.detail;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    public ObservableField<String> vatPercent = new ObservableField<String>("0");
    public ObservableDouble totalAndVat = new ObservableDouble(0);
    public ObservableDouble reduce = new ObservableDouble(0);
    public ObservableField<String> sale = new ObservableField<>("0");
    public ObservableBoolean canCancelOrder = new ObservableBoolean(true);
    public OrderDetailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void cancelOrder(BaseRequestCallback<Boolean> callback){
        if (!checkCancelTime()) {
            showErrorMessage("Đã quá thời gian huỷ đơn");
        } else {
            compositeDisposable.add(repository.getApiService().cancelOrder(
                    OrderDetailActivity.historyDetailResponse.getId()
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            response -> {
                                if (response.isResult()){
                                    callback.doSuccess(response.isResult());
                                } else {
                                    callback.doFail(response.getMessage(), response.getMessage());
                                }
                            },throwable -> {
                                callback.doError(throwable,this);
                            }
                    ));
        }
    }

    public boolean checkCancelTime(){
        long diffInMillies = Math.abs((new Date()).getTime() - OrderDetailActivity.historyDetailResponse.getCreatedDate().getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies + 5000, TimeUnit.MILLISECONDS); // threshold
        boolean canCancel = (diff < application.getTimeLimit());
        canCancelOrder.set(canCancel);
        return canCancel ;
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
