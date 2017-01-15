package hr.vspr.dpasic.tenniswithme.rest.api_interface;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dpasic on 1/15/17.
 */

public interface FriendsRestInterface {

    @GET("api/PlayerFriendships/Active")
    Call<List<User>> getActiveFriends();
}
