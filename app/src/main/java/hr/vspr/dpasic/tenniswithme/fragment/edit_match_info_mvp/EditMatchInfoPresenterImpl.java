package hr.vspr.dpasic.tenniswithme.fragment.edit_match_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.EditUserInfoPresenter;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.IdentityPlayerRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.MatchesRestInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 30.1.2017..
 */

public class EditMatchInfoPresenterImpl implements EditMatchInfoPresenter {

    private EditMatchInfoView editMatchInfoView;

    public EditMatchInfoPresenterImpl(EditMatchInfoView editMatchInfoView) {
        this.editMatchInfoView = editMatchInfoView;
    }

    @Override
    public void updateMatch(final Match match) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                updateMatchRequest(token, match);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void updateMatchRequest(AccessToken token, final Match match) {
        final MatchesRestInterface matchesRestInterface = ServiceGenerator.createService(MatchesRestInterface.class, token);

        Call<ResponseBody> call = matchesRestInterface.updateMatch(match);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    editMatchInfoView.onUpdatedMatch(match);

                } else {
                    editMatchInfoView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                editMatchInfoView.notifyRequestError(t.getMessage());
            }
        });
    }
}
