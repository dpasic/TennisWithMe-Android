package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import hr.vspr.dpasic.tenniswithme.model.PlayersRating;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by edjapas on 13.11.2017..
 */

public interface PlayerRatingsRestInterface {

    @GET("api/PlayerRatings")
    Call<PlayersRating> getPlayersRatingForPlayerId(@Query("playerId") String playerId);

    @PUT("api/PlayerRatings")
    Call<ResponseBody> createOrUpdatePlayersRating(@Body PlayersRating playersRating);
}
