package q.sdm.data.remote;

import io.reactivex.rxjava3.core.Observable;
import q.sdm.data.model.api.ResponseWrapper;
import q.sdm.data.model.api.request.LoginRequest;
import q.sdm.data.model.api.request.register.RegisterRequest;
import q.sdm.data.model.api.response.LoginResponse;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.api.response.register.RegisterResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("v1/account/client-login")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<LoginResponse>> login(@Body LoginRequest request);

    @GET("v1/account/profile")
    Observable<ResponseWrapper<ProfileResponse>> profile();

    @POST("v1/customer/mb-register")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<RegisterResponse>> register(@Body RegisterRequest request);

}
