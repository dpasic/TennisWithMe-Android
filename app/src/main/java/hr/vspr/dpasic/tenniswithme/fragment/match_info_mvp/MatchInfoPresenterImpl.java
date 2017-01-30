package hr.vspr.dpasic.tenniswithme.fragment.match_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.PlayersFriendship;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.MatchesRestInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 30.1.2017..
 */

public class MatchInfoPresenterImpl implements MatchInfoPresenter {

    private MatchInfoView matchInfoView;

    public MatchInfoPresenterImpl(MatchInfoView matchInfoView) {
        this.matchInfoView = matchInfoView;
    }

    @Override
    public void confirmMatch(final Match match) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                confirmMatchRequest(token, match);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void confirmMatchRequest(AccessToken token, Match match) {
        final MatchesRestInterface matchesRestInterface = ServiceGenerator.createService(MatchesRestInterface.class, token);

        Call<ResponseBody> call = matchesRestInterface.confirmMatch(match);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    matchInfoView.acceptedMatch();

                } else {
                    matchInfoView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                matchInfoView.notifyRequestError(t.getMessage());
            }
        });
    }
}
