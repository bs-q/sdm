package q.sdm.ui.address;

import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.address.CreateAddressRequest;
import q.sdm.data.model.api.response.EmptyResponse;
import q.sdm.data.model.api.response.province.ProvinceResponse;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;

public class AddAddressViewModel extends BaseViewModel {
    public Long districtId;
    public Long provinceId;
    public Long communeId;
    public List<ProvinceResponse> districts = new ArrayList<>();
    public List<ProvinceResponse> province = new ArrayList<>();
    public List<ProvinceResponse> commune = new ArrayList<>();
    public ObservableField<String> address = new ObservableField<>("");

    public AddAddressViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public boolean checkForm(){
        if (provinceId == null) {
            showErrorMessage("Bạn chưa chọn Tỉnh/Thành");
            return false;
        }
        if (districtId == null) {
            showErrorMessage("Bạn chưa chọn Quận/Huyện");
            return false;
        }
        if (communeId == null) {
            showErrorMessage("Bạn chưa chọn Xã/Phường");
            return false;
        }
        if (address.get().isEmpty()) {
            showErrorMessage("Bạn chưa nhập địa chỉ");
            return false;
        }
        return true;
    }

    public void createAddress(BaseRequestCallback<EmptyResponse> callback){
        showLoading();
        CreateAddressRequest request = new CreateAddressRequest();
        request.setAddress(address.get());
        request.setProvinceId(provinceId);
        request.setCommuneId(communeId);
        request.setDistrictId(districtId);
        request.setName(application.getProfileResponse().getCustomerFullName());
        request.setCustomerId(application.getProfileResponse().getId());
        request.setPhone(application.getProfileResponse().getCustomerPhone());
        compositeDisposable.add(repository.getApiService().createAddress(request)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response.getData());
                    } else {
                        callback.doFail(response.getMessage(), response.getCode());
                    }
                    hideLoading();
                },throwable -> {
                    callback.doError(throwable,this);
                }
        ));
    }

    public void getProvince(String type, Long parentId, BaseRequestCallback<List<ProvinceResponse>> callback){
        compositeDisposable.add(repository.getApiService().province(type,parentId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                response ->{
                    if (response.isResult()){
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
