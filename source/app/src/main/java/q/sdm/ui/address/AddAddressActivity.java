package q.sdm.ui.address;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.constant.Constants;
import q.sdm.data.model.api.response.EmptyResponse;
import q.sdm.data.model.api.response.province.ProvinceResponse;
import q.sdm.databinding.ActivityUpdateLocationBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;

public class AddAddressActivity extends BaseActivity<ActivityUpdateLocationBinding,AddAddressViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_update_location;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.provinceAuto.setShowSoftInputOnFocus(false);
        viewBinding.districtAuto.setShowSoftInputOnFocus(false);
        viewBinding.communeAuto.setShowSoftInputOnFocus(false);
        viewBinding.districtMenu.setEnabled(false);
        viewBinding.communeMenu.setEnabled(false);
        setupProvinceAutoComplete();
    }
    private void resetFromProvince(){
        viewBinding.districtAuto.getText().clear();
        viewBinding.communeAuto.getText().clear();
        viewModel.districtId = null;
        viewModel.communeId = null;
        viewBinding.districtAuto.setEnabled(true);
        viewBinding.districtMenu.setEnabled(true);
        viewBinding.communeAuto.setEnabled(false);
        viewBinding.communeMenu.setEnabled(false);

    }
    private void resetFromDistrict(){
        viewBinding.communeAuto.getText().clear();
        viewModel.communeId = null;
        viewBinding.communeAuto.setEnabled(true);
        viewBinding.communeMenu.setEnabled(true);
    }
    private void setupProvinceAutoComplete(){
        viewModel.showLoading();
        viewModel.getProvince(Constants.PROVINCE_KIND_PROVINCE, null, new BaseRequestCallback<List<ProvinceResponse>>() {
            @Override
            public void doSuccess(List<ProvinceResponse> response) {
                viewModel.hideLoading();
                viewModel.province = response;
                List<String> names = viewModel.province.stream().map(s->s.getProvinceName()).collect(Collectors.toList());
                ArrayList<String> items = new ArrayList<>(names);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAddressActivity.this,R.layout.list_item,items);
                viewBinding.provinceAuto.setAdapter(adapter);
                viewBinding.provinceAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        viewModel.provinceId = viewModel.province.get(position).getId();
                        setupDistrictAutoComplete(viewModel.provinceId);
                        resetFromProvince();
                    }
                });
            }
        });
    }
    private void setupDistrictAutoComplete(Long id){
        viewModel.showLoading();
        viewModel.getProvince(Constants.PROVINCE_KIND_DISTRICT, id, new BaseRequestCallback<List<ProvinceResponse>>() {
            @Override
            public void doSuccess(List<ProvinceResponse> response) {
                viewModel.hideLoading();
                viewModel.districts = response;
                List<String> names = viewModel.districts.stream().map(s->s.getProvinceName()).collect(Collectors.toList());
                ArrayList<String> items = new ArrayList<>();
                items.addAll(names);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAddressActivity.this,R.layout.list_item,items);
                viewBinding.districtAuto.setAdapter(adapter);
                viewBinding.districtAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        viewModel.districtId = viewModel.districts.get(position).getId();
                        setupCommuneAutoComplete(viewModel.districtId);
                        resetFromDistrict();
                    }
                });
            }
        });
    }
    private void setupCommuneAutoComplete(Long id){
        viewModel.showLoading();
        viewModel.getProvince(Constants.PROVINCE_KIND_COMMUNE, id, new BaseRequestCallback<List<ProvinceResponse>>() {
            @Override
            public void doSuccess(List<ProvinceResponse> response) {
                viewModel.hideLoading();
                viewModel.commune = response;
                List<String> names = viewModel.commune.stream().map(s->s.getProvinceName()).collect(Collectors.toList());
                ArrayList<String> items = new ArrayList<>();
                items.addAll(names);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAddressActivity.this,R.layout.list_item,items);
                viewBinding.communeAuto.setAdapter(adapter);
                viewBinding.communeAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        viewModel.communeId = viewModel.commune.get(position).getId();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.updateAddressBtn.getId()){
            createAddress();
        }
    }
    private void createAddress(){
        if (viewModel.checkForm()) {
            viewModel.createAddress(new BaseRequestCallback<EmptyResponse>() {
                @Override
                public void doSuccess(EmptyResponse response) {
                    viewModel.showSuccessMessage("Cập nhật địa chỉ thành công");
                    finish();
                }
            });
        }
    }
}
