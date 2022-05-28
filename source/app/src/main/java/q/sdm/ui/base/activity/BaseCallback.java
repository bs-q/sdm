package q.sdm.ui.base.activity;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;


import q.sdm.R;
import q.sdm.data.model.api.ApiModelUtils;
import q.sdm.data.model.api.ResponseWrapper;
import q.sdm.ui.base.fragment.BaseFragmentViewModel;
import retrofit2.HttpException;
import timber.log.Timber;

public interface BaseCallback {
    void doSuccess();
    default void doFail() {

    }
    default void doError(Throwable throwable,BaseViewModel viewModel){
        String message = null;
        try {
            if (throwable instanceof HttpException){
                HttpException error = (HttpException) throwable;

                if (error.response() != null){
                    Type type = new TypeToken<ResponseWrapper<?>>(){}.getType();
                    ResponseWrapper<?> response = ApiModelUtils.fromJson(Objects.requireNonNull(Objects.requireNonNull(error.response()).errorBody()).string(),type);

                    message = response.getMessage();
                }
            } else if (throwable instanceof IOException) {
                message = viewModel.application.getString(R.string.newtwork_error);
            }

        } catch (Exception e){
            Timber.d(e);
        }
        viewModel.hideLoading();
        if (message!=null){
            viewModel.showErrorMessage(message);
        }
        Timber.d(throwable);
    }
    default void doError(Throwable throwable, BaseFragmentViewModel viewModel){
        String message = null;
        Timber.d(throwable);
        try {
            if (throwable instanceof HttpException){
                HttpException error = (HttpException) throwable;

                if (error.response() != null){
                    Type type = new TypeToken<ResponseWrapper<?>>(){}.getType();
                    ResponseWrapper<?> response = ApiModelUtils.fromJson(Objects.requireNonNull(Objects.requireNonNull(error.response()).errorBody()).string(),type);

                    message = response.getMessage();                }
            } else if (throwable instanceof IOException) {
                message = viewModel.application.getString(R.string.newtwork_error);
            }

        } catch (Exception e){
            Timber.d(e);
        }
        viewModel.hideLoading();
        if (message!=null){
            viewModel.showErrorMessage(message);
        }
    }
}
