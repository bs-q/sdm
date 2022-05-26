package q.sdm.ui.register.verify;

import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;

import eu.davidea.flexibleadapter.databinding.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityVerifyBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.utils.DeviceUtils;

public class VerifyActivity extends BaseActivity<ActivityVerifyBinding,VerifyViewModel> implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_verify;
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
        setupOtpForm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewBinding.otpBar.otp1.requestFocus();
        DeviceUtils.openSoftKeyboard(this,viewBinding.otpBar.otp1);
    }

    private void setupOtpForm(){
        viewModel.otp1.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!viewModel.otp1.get().isEmpty()) {
                    disableTextView(viewBinding.otpBar.otp1);
                    enableTextView(viewBinding.otpBar.otp2);
                    viewBinding.otpBar.otp2.requestFocus();
                }
            }
        });

        viewModel.otp2.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!viewModel.otp2.get().isEmpty()) {
                    enableTextView(viewBinding.otpBar.otp3);
                    viewBinding.otpBar.otp3.requestFocus();
                    disableTextView(viewBinding.otpBar.otp2);
                }
            }
        });
        viewBinding.otpBar.otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && viewModel.otp2.get().isEmpty()) {
                    enableTextView(viewBinding.otpBar.otp1);
                    viewBinding.otpBar.otp1.requestFocus();
                    disableTextView(viewBinding.otpBar.otp2);
                }
                return false;
            }
        });

        viewModel.otp3.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!viewModel.otp3.get().isEmpty()) {
                    enableTextView(viewBinding.otpBar.otp4);
                    viewBinding.otpBar.otp4.requestFocus();
                    disableTextView(viewBinding.otpBar.otp3);
                }
            }
        });
        viewBinding.otpBar.otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && viewModel.otp3.get().isEmpty()) {
                    enableTextView(viewBinding.otpBar.otp2);
                    viewBinding.otpBar.otp2.requestFocus();
                    disableTextView(viewBinding.otpBar.otp3);
                }
                return false;
            }
        });
        viewModel.otp4.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (!viewModel.otp4.get().isEmpty()){
                    viewModel.validOtp.set(true);
                } else {
                    viewModel.validOtp.set(false);
                }

            }
        });
        viewBinding.otpBar.otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && viewModel.otp4.get().isEmpty()) {
                    enableTextView(viewBinding.otpBar.otp3);
                    viewBinding.otpBar.otp3.requestFocus();
                    disableTextView(viewBinding.otpBar.otp4);
                }
                return false;
            }
        });
    }
    void disableTextView(TextView textView){
        textView.post(()->{
            textView.setFocusable(false);
            textView.setCursorVisible(false);
            textView.setClickable(false);
            textView.clearFocus();
        });

    }
    void enableTextView(TextView textView) {
        textView.post(()->{
            textView.setFocusable(true);
            textView.setCursorVisible(true);
            textView.setClickable(true);
            textView.setFocusableInTouchMode(true);
            textView.append("");
            textView.requestFocus();
        });

    }
    @Override
    public void onClick(View v) {

    }
}
