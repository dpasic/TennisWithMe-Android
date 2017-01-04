package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by edjapas on 3.1.2017..
 */

public interface IdentityPlayerRestInterface {

    @GET("api/IdentityPlayer")
    Call<User> getUserInfo();
}
