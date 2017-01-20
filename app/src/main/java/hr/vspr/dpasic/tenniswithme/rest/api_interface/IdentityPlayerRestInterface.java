package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import hr.vspr.dpasic.tenniswithme.model.Player;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by edjapas on 3.1.2017..
 */

public interface IdentityPlayerRestInterface {

    @GET("api/IdentityPlayer")
    Call<Player> getUserInfo();
}
