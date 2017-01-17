package hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.PlayersFriendship;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
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
    public void confirmFriendship(User user) {
        AccessToken token = SQLite.select().from(AccessToken.class).querySingle();
        final FriendsRestInterface friendsRestInterface = ServiceGenerator.createService(FriendsRestInterface.class, token);

        PlayersFriendship friendship = new PlayersFriendship();
        friendship.playerTwoId = user.getId();

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
}
