package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by edjapas on 19.12.2016..
 */

public interface AccountRestInterface {

    @POST("api/account/register")
    Call<ResponseBody> register(@Body RegisterViewModel registerViewModel);

    @FormUrlEncoded
    @POST("token")
    Call<AccessToken> getAccessToken(
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("token")
    Call<AccessToken> getRefreshedAccessToken(
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType);
}
