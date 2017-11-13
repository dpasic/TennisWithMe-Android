package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import hr.vspr.dpasic.tenniswithme.model.PlayersRating;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by edjapas on 13.11.2017..
 */

public interface PlayerRatingsRestInterface {

    @GET("api/PlayerRatings")
    Call<PlayersRating> getPlayersRatingForFriendId(@Query("friendId") String friendId);

    @POST("api/PlayerRatings")
    Call<ResponseBody> createOrUpdatePlayersRating(@Body PlayersRating playersRating);
}
