package q.sdm.ui.location;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.response.EmptyResponse;
import q.sdm.data.model.api.response.address.AddressResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class LocationViewModel extends BaseViewModel {
    public LocationViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void getAddresses(BaseRequestCallback<List<AddressResponse>> callback){
        compositeDisposable.add(repository.getApiService().getAddresses()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response.getData().getData().stream().filter(o->o.getAddress()!=null).collect(Collectors.toList()));
                    } else {
                        callback.doFail(response.getMessage(), response.getCode());
                    }
                },throwable -> {
                    callback.doError(throwable,this);
                }
        ));
    }
    public void deleteAddress(Long id , BaseRequestCallback<EmptyResponse> callback){
        showLoading();
        compositeDisposable.add(repository.getApiService()
        .deleteAddress(id).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                response->{
                    if (response.isResult()){
                        hideLoading();
                        callback.doSuccess(response.getData());
                    } else {
                        hideLoading();
                        callback.doFail(response.getMessage(), response.getCode());
                    }
                },throwable -> {
                    callback.doError(throwable,this);
                        }
                ));
    }
    public void saveAddress(AddressResponse addressResponse){
        repository.getSharedPreferences().setCurrentLocation(addressResponse.getId());
        application.getCustomerLocation().set(addressResponse.getParseAddress());
    }
}
