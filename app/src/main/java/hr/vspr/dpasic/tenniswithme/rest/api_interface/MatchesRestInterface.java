package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.Match;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by dpasic on 1/15/17.
 */

public interface MatchesRestInterface {

    @GET("api/Matches/Active")
    Call<List<Match>> getActiveMatches();

    @GET("api/Matches/Requested")
    Call<List<Match>> getRequestedMatches();

    @POST("api/Matches/RequestMatch")
    Call<ResponseBody> requestMatch(@Body Match match);

    @PUT("api/Matches/ConfirmMatch")
    Call<ResponseBody> confirmMatch(@Body Match match);

    @PUT("api/Matches/UpdateMatch")
    Call<ResponseBody> updateMatch(@Body Match match);
}
