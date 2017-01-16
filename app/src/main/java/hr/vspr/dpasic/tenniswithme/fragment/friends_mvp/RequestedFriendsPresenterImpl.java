package hr.vspr.dpasic.tenniswithme.fragment.friends_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.friends_mvp.FriendsView;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dpasic on 1/15/17.
 */

public class RequestedFriendsPresenterImpl implements FriendsPresenter {

    private FriendsView friendsView;

    public RequestedFriendsPresenterImpl(FriendsView friendsView) {
        this.friendsView = friendsView;
    }

    @Override
    public void prepareListView() {
        AccessToken token = SQLite.select().from(AccessToken.class).querySingle();
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        Call<List<User>> call = friendsRestInterface.getRequestedFriends();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    friendsView.updateListViewAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
