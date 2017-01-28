package hr.vspr.dpasic.tenniswithme.fragment.friends_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dpasic on 1/15/17.
 */

public class FriendsPresenterImpl implements FriendsPresenter {

    private FriendsView friendsView;

    public FriendsPresenterImpl(FriendsView friendsView) {
        this.friendsView = friendsView;
    }

    @Override
    public void prepareActiveListView() {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                prepareActiveListViewRequest(token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void prepareActiveListViewRequest(AccessToken token) {
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        Call<List<Player>> call = friendsRestInterface.getActiveFriends();
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if (response.isSuccessful()) {
                    friendsView.updateViewAdapter(response.body());

                } else {
                    friendsView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                friendsView.notifyRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void prepareRequestedListView() {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                prepareRequestedListViewRequest(token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void prepareRequestedListViewRequest(AccessToken token) {
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        Call<List<Player>> call = friendsRestInterface.getRequestedFriends();
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if (response.isSuccessful()) {
                    friendsView.updateViewAdapter(response.body());

                } else {
                    friendsView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                friendsView.notifyRequestError(t.getMessage());
            }
        });
    }
}
