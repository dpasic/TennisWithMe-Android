package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.PlayersFriendship;
import hr.vspr.dpasic.tenniswithme.model.Player;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by dpasic on 1/15/17.
 */

public interface FriendsRestInterface {

    @GET("api/PlayerFriendships/Active")
    Call<List<Player>> getActiveFriends();

    @GET("api/PlayerFriendships/Requested")
    Call<List<Player>> getRequestedFriends();

    @GET("api/PlayerFriendships/Strangers")
    Call<List<Player>> getStrangers(@Query("query") String query);

    @POST("api/PlayerFriendships/RequestFriendship")
    Call<ResponseBody> requestFriendship(@Body PlayersFriendship friendship);

    @PUT("api/PlayerFriendships/ConfirmFriendship")
    Call<ResponseBody> confirmFriendship(@Body PlayersFriendship friendship);
}
