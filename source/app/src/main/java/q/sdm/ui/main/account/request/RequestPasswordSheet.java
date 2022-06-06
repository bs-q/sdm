package q.sdm.ui.main.account.request;

import android.net.Uri;
import android.view.View;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.databinding.SheetRequestPasswordBinding;
import q.sdm.di.component.SheetComponent;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseSheet;

public class RequestPasswordSheet extends BaseSheet<SheetRequestPasswordBinding,RequestPasswordViewModel> {

    Uri imgUri;
    String email;
    String username;
    public RequestPasswordSheet(Uri imgUri,String username){
        super();
        this.imgUri = imgUri;
        this.username = username;
    }
    public RequestPasswordSheet(String email,String username){
        super();
        this.username = username;
        this.email = email;
    }
    public RequestPasswordSheet(String username){
        super();
        this.username = username;
    }
    public RequestPasswordSheet(){
        super();
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sheet_request_password;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(SheetComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (viewModel.checkForm()) {
           updateImage();
           updateEmail();
           updateName();
        }
    }
    private void updateImage(){
        if (imgUri == null) return;
        viewModel.showLoading();
        viewModel.updateImage(username,imgUri, new BaseRequestCallback<ProfileResponse>() {
            @Override
            public void doSuccess(ProfileResponse response) {
                viewModel.hideLoading();
                viewModel.showSuccessMessage("Cập nhật thông tin thành công");
                myApplication().setProfileResponse(response);
                dismiss();
            }
        });
    }
    private void updateEmail(){
        if (email == null) return;
        viewModel.showLoading();
        viewModel.updateProfile(username,email,null, new BaseRequestCallback<ProfileResponse>() {
            @Override
            public void doSuccess(ProfileResponse response) {
                viewModel.hideLoading();
                viewModel.showSuccessMessage("Cập nhật thông tin thành công");
                myApplication().setProfileResponse(response);
                dismiss();
                requireActivity().finish();
            }
        });
    }
    private void updateName(){
        if (email != null || imgUri != null) return;
        viewModel.showLoading();
        viewModel.updateProfile(username,null,null, new BaseRequestCallback<ProfileResponse>() {
            @Override
            public void doSuccess(ProfileResponse response) {
                viewModel.hideLoading();
                viewModel.showSuccessMessage("Cập nhật thông tin thành công");
                myApplication().setProfileResponse(response);
                dismiss();
                requireActivity().finish();

            }
        });
    }
}
