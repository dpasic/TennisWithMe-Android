package hr.vspr.dpasic.tenniswithme.activity.friend_request_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.IdentityPlayerRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 19.1.2017..
 */

public class FriendRequestPresenterImpl implements FriendRequestPresenter {

    private FriendRequestView friendRequestView;

    public FriendRequestPresenterImpl(FriendRequestView friendRequestView) {
        this.friendRequestView = friendRequestView;
    }

    @Override
    public void sendFriendsQuery(final String query) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                sendFriendsQueryRequest(query, token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void sendFriendsQueryRequest(String query, AccessToken token) {
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        Call<List<User>> call = friendsRestInterface.getStrangers(query);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    friendRequestView.updateListViewAdapter(response.body());

                } else {
                    friendRequestView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                friendRequestView.notifyRequestError(t.getMessage());
            }
        });
    }
}
