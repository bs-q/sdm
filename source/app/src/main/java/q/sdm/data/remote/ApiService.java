package q.sdm.data.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import q.sdm.data.model.api.ResponseListObj;
import q.sdm.data.model.api.ResponseWrapper;
import q.sdm.data.model.api.request.LoginRequest;
import q.sdm.data.model.api.request.account.UpdateProfileRequest;
import q.sdm.data.model.api.request.address.CreateAddressRequest;
import q.sdm.data.model.api.request.register.RegisterRequest;
import q.sdm.data.model.api.response.BaseResponse;
import q.sdm.data.model.api.response.DataWrapper;
import q.sdm.data.model.api.response.EmptyResponse;
import q.sdm.data.model.api.response.LoginResponse;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.api.response.address.AddressResponse;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.api.response.province.ProvinceResponse;
import q.sdm.data.model.api.response.register.RegisterResponse;
import q.sdm.data.model.api.response.upload.UploadResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("v1/category/auto-complete?kind=3")
    Observable<ResponseWrapper<DataWrapper<List<CategoryResponse>>>> category();

    @GET("v1/province/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<ProvinceResponse>>>> province(@Query("kind") String kind, @Query("parentId") Long parentId);

    @GET("v1/product/client-get")
    Observable<ResponseWrapper<ProductResponse>> productDetail(@Query("productId") Long id);

    @GET("v1/product/client-list")
    Observable<ResponseWrapper<ResponseListObj<ProductResponse>>> products(@Query("size")Integer size, @Query("page") Integer page);

    @GET("v1/product/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<ProductResponse>>>> productsCategory(@Query("categoryId")Long categoryId);

    @GET("v1/product/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<ProductResponse>>>> searchProducts(@Query("name") String query);

    @POST("/v1/addresses/create")
    Observable<ResponseWrapper<EmptyResponse>> createAddress(@Body CreateAddressRequest request);

    @Multipart
    @POST("/v1/file/upload")
    Observable<ResponseWrapper<UploadResponse>> fileUpload(@Part MultipartBody.Part image, @Part("type") RequestBody type);

    @PUT("/v1/customer/update_profile")
    Observable<ResponseWrapper<EmptyResponse>> updateProfile(@Body UpdateProfileRequest request);

    @GET("/v1/addresses/auto-complete")
    Observable<ResponseWrapper<DataWrapper<List<AddressResponse>>>> getAddresses();

}
