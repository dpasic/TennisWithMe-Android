package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.Player;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dpasic on 1/15/17.
 */

public interface PlayersRestInterface {

    @GET("api/Players")
    Call<List<Player>> getPlayers(@Query("city") String city,
                                  @Query("gender") String gender,
                                  @Query("skill") String skill);
}
