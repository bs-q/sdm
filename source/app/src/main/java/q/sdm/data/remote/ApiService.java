package q.sdm.data.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import q.sdm.data.model.api.ResponseListObj;
import q.sdm.data.model.api.ResponseWrapper;
import q.sdm.data.model.api.request.LoginRequest;
import q.sdm.data.model.api.request.address.CreateAddressRequest;
import q.sdm.data.model.api.request.register.RegisterRequest;
import q.sdm.data.model.api.response.BaseResponse;
import q.sdm.data.model.api.response.DataWrapper;
import q.sdm.data.model.api.response.EmptyResponse;
import q.sdm.data.model.api.response.LoginResponse;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.api.response.province.ProvinceResponse;
import q.sdm.data.model.api.response.register.RegisterResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("v1/account/client-login")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<LoginResponse>> login(@Body LoginRequest request);

    @GET("v1/customer/profile")
    Observable<ResponseWrapper<ProfileResponse>> profile();

    @POST("v1/customer/mb-register")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<RegisterResponse>> register(@Body RegisterRequest request);


    @GET("v1/category/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<CategoryResponse>>>> category();

    @GET("v1/province/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<ProvinceResponse>>>> province(@Query("kind") String kind, @Query("parentId") Long parentId);

    @GET("v1/product/client-get")
    Observable<ResponseWrapper<ProductResponse>> productDetail(@Query("productId") Long id);

    @GET("v1/product/client-list")
    Observable<ResponseWrapper<ResponseListObj<ProductResponse>>> products(@Query("size")Integer size, @Query("page") Integer page);


    @GET("v1/product/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<ProductResponse>>>> searchProducts(@Query("name") String query);

    @POST("/v1/addresses/create")
    Observable<ResponseWrapper<EmptyResponse>> createAddress(@Body CreateAddressRequest request);
}
