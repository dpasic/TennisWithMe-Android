package hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Credentials;
import hr.vspr.dpasic.tenniswithme.model.PlayersFriendship;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.AccountRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 16.1.2017..
 */

public class UserInfoPresenterImpl implements UserInfoPresenter {

    private UserInfoView userInfoView;

    public UserInfoPresenterImpl(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
    }

    @Override
    public void confirmFriendship(final Player player) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                confirmFriendshipRequest(token, player);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void confirmFriendshipRequest(AccessToken token, Player player) {
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        PlayersFriendship friendship = new PlayersFriendship();
        friendship.playerTwoId = player.getId();

        Call<ResponseBody> call = friendsRestInterface.confirmFriendship(friendship);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    userInfoView.acceptFriendship();

                } else {
                    userInfoView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                userInfoView.notifyRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void requestFriendship(final Player player) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                requestFriendshipRequest(token, player);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void requestFriendshipRequest(AccessToken token, Player player) {
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        PlayersFriendship friendship = new PlayersFriendship();
        friendship.playerTwoId = player.getId();

        Call<ResponseBody> call = friendsRestInterface.requestFriendship(friendship);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    userInfoView.friendshipRequested();

                } else {
                    userInfoView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                userInfoView.notifyRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void signOut() {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                signOutRequest(token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void signOutRequest(AccessToken token) {
        final AccountRestInterface accountRestInterface = ServiceGenerator.createService(AccountRestInterface.class, token);

        Call<ResponseBody> call = accountRestInterface.logout();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //delete old access token from LocalDB
                    AccessToken tokenToDelete = SQLite.select().from(AccessToken.class).querySingle();
                    if (tokenToDelete != null) {
                        tokenToDelete.delete();
                    }

                    //delete old credentials from LocalDB
                    Credentials credentialsToDelete = SQLite.select().from(Credentials.class).querySingle();
                    if (credentialsToDelete != null) {
                        credentialsToDelete.delete();
                    }

                    userInfoView.signedOut();

                } else {
                    userInfoView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                userInfoView.notifyRequestError(t.getMessage());
            }
        });
    }
}
