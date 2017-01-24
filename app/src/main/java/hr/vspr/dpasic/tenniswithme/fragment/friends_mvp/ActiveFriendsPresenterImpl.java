package hr.vspr.dpasic.tenniswithme.fragment.friends_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

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

public class ActiveFriendsPresenterImpl implements FriendsPresenter {

    private FriendsView friendsView;

    public ActiveFriendsPresenterImpl(FriendsView friendsView) {
        this.friendsView = friendsView;
    }

    @Override
    public void prepareListView() {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                prepareListViewRequest(token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void prepareListViewRequest(AccessToken token) {
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        Call<List<Player>> call = friendsRestInterface.getActiveFriends();
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if (response.isSuccessful()) {
                    friendsView.updateListViewAdapter(response.body());

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
