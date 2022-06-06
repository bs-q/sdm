package q.sdm.ui.main.account.detail;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.binding.ImageBindingAdapter;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.api.response.upload.UploadResponse;
import q.sdm.databinding.ActivityAccountDetailBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.main.account.detail.dialog.CaptureImageDialog;
import q.sdm.ui.main.account.email.UpdateEmailActivity;
import q.sdm.ui.main.account.name.UpdateNameActivity;
import q.sdm.ui.main.account.password.UpdatePasswordActivity;
import q.sdm.ui.main.account.request.RequestPasswordSheet;
import timber.log.Timber;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class AccountDetailActivity extends BaseActivity<ActivityAccountDetailBinding,AccountDetailViewModel>
implements View.OnClickListener {

    private static final int PROFILE_IMAGE_REQ_CODE = 101;
    private static final int GALLERY_IMAGE_REQ_CODE = 102;
    private static final int CAMERA_IMAGE_REQ_CODE = 103;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_detail;
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
        viewBinding.setProfile(myApplication().getProfileResponse());
        viewModel.username.set(myApplication().getProfileResponse().getCustomerFullName());
        ImageBindingAdapter.setAvatarImage(viewBinding.acdAvatar,myApplication().getProfileResponse().getAvatar());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.nameTitleLayer.getId()) {
            navigateToEditName();
        }  else if (v.getId() == viewBinding.acdChangeAvatar.getId()) {
            changeImage();
        } else if (v.getId() == viewBinding.emailTitleLayer.getId()){
            navigateToEditEmail();
        } else if (v.getId() == viewBinding.passwordLayer.getId()) {
            navigateToEditPassword();
        }
    }
    private void navigateToEditPassword(){
        Intent it = new Intent(this, UpdatePasswordActivity.class);
        startActivity(it);
    }
    private void navigateToEditName(){
        Intent it = new Intent(this, UpdateNameActivity.class);
        startActivity(it);
    }
    private void navigateToEditEmail(){
        Intent it = new Intent(this, UpdateEmailActivity.class);
        startActivity(it);
    }
    private Uri mCameraUri;
    private Uri mGalleryUri;
    private Uri mProfileUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            Uri uri = data.getData();

            switch (requestCode) {
                case PROFILE_IMAGE_REQ_CODE:
                    mProfileUri = uri;
                    break;
                case GALLERY_IMAGE_REQ_CODE:
                    mGalleryUri = uri;
                    Timber.d("get gallery image success");
                    uploadImage(mGalleryUri);
                    break;
                case CAMERA_IMAGE_REQ_CODE:
                    mCameraUri = uri;
                    uploadImage(mCameraUri);
                    Timber.d("get camera image success");
                    break;
            }
        }
    }
    Uri currentUri;
    private void uploadImage(Uri img){
       currentUri = img;
       RequestPasswordSheet requestPasswordSheet = new RequestPasswordSheet(currentUri,myApplication().getProfileResponse().getCustomerFullName());
       requestPasswordSheet.show(getSupportFragmentManager(),"REQUEST_PWD");

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewBinding.setProfile(myApplication().getProfileResponse());
        viewBinding.executePendingBindings();
    }

    private void changeImage(){

        CaptureImageDialog dialog = new CaptureImageDialog(new CaptureImageDialog.CaptureImageDialogInterface() {
            @Override
            public void captureImage() {
                pickCameraImage();
            }

            @Override
            public void loadImage() {
                pickGalleryImage();
            }
        });
        dialog.show(getSupportFragmentManager(),"CHANGE_AVATAR");
    }
    /**
     * Ref: https://gist.github.com/granoeste/5574148
     */
    public void pickCameraImage() {
        ImagePicker.with(this)
                // User can only capture image from Camera
                .cameraOnly()
                // Image size will be less than 1024 KB
                .compress(1024)

                //  Path: /storage/sdcard0/Android/data/package/files/Pictures
                .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                .cropSquare()
                .maxResultSize(400,400)
                .start(CAMERA_IMAGE_REQ_CODE);
    }

    public void pickGalleryImage() {
        ImagePicker.with(this)
                // Crop Image(User can choose Aspect Ratio)
                .crop()
                // User can only select image from Gallery
                .galleryOnly()
                .galleryMimeTypes(new String[]{"image/png",
                        "image/jpg",
                        "image/jpeg"
                })
                .compress(1024)
                .cropSquare()
                .maxResultSize(400,400)
                // Image resolution will be less than 1080 x 1920
                .maxResultSize(1080, 1920)
                // .saveDir(getExternalFilesDir(null))
                .start(GALLERY_IMAGE_REQ_CODE);
    }


}
