package q.sdm.ui.location;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.address.AddressResponse;
import q.sdm.databinding.ActivityAddressesBinding;
import q.sdm.databinding.ActivityLoginBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.address.AddAddressActivity;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.location.adapter.LocationAdapter;

public class LocationActivity extends BaseActivity<ActivityAddressesBinding,LocationViewModel>
        implements View.OnClickListener, LocationAdapter.LocationAdapterCallback {

    LocationAdapter locationAdapter;
    boolean refresh = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_addresses;
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
        setupAdapter();
        getAddresses();
    }

    private void getAddresses(){
        viewModel.showLoading();
        viewModel.getAddresses(new BaseRequestCallback<List<AddressResponse>>() {
            @Override
            public void doSuccess(List<AddressResponse> response) {
                viewModel.hideLoading();
                locationAdapter.addressResponseList = response;
                locationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupAdapter(){
        locationAdapter = new LocationAdapter();
        locationAdapter.callback = this;
        viewBinding.rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewBinding.rv.setAdapter(locationAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh) {
            getAddresses();
        }
    }

    @Override
    public void addLocation() {
        navigateToCreateAddress();
    }
    private void navigateToCreateAddress(){
        Intent it = new Intent(this, AddAddressActivity.class);
        startActivity(it);
        refresh = true;
    }


    @Override
    public void setDefaultLocation(AddressResponse addressResponse) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Đặt làm địa chỉ mặc định")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.saveAddress(addressResponse);
                        finish();
                    }
                })
                .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                ;
        alertDialog.show();
    }
}
