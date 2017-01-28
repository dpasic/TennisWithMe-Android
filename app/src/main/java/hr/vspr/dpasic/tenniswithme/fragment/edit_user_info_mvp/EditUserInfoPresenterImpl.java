package hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.IdentityPlayerRestInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dpasic on 1/8/17.
 */

public class EditUserInfoPresenterImpl implements EditUserInfoPresenter {

    private EditUserInfoView editUserInfoView;

    public EditUserInfoPresenterImpl(EditUserInfoView editUserInfoView) {
        this.editUserInfoView = editUserInfoView;
    }

    @Override
    public void updateProfile(final Player player) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                updateProfileRequest(token, player);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void updateProfileRequest(AccessToken token, final Player player) {
        final IdentityPlayerRestInterface identityPlayerRestInterface = ServiceGenerator.createService(IdentityPlayerRestInterface.class, token);

        Call<ResponseBody> call = identityPlayerRestInterface.updatePlayer(player);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    editUserInfoView.onUpdatedProfile(player);

                } else {
                    editUserInfoView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                editUserInfoView.notifyRequestError(t.getMessage());
            }
        });
    }
}
