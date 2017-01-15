package hr.vspr.dpasic.tenniswithme.active_friends_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Credentials;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.AccountRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dpasic on 1/15/17.
 */

public class ActiveFriendsPresenterImpl implements ActiveFriendsPresenter {

    private ActiveFriendsView activeFriendsView;

    public ActiveFriendsPresenterImpl(ActiveFriendsView activeFriendsView) {
        this.activeFriendsView = activeFriendsView;
    }

    @Override
    public void prepareListView() {
        AccessToken token = SQLite.select().from(AccessToken.class).querySingle();
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        Call<List<User>> call = friendsRestInterface.getActiveFriends();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    activeFriendsView.updateListViewAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
