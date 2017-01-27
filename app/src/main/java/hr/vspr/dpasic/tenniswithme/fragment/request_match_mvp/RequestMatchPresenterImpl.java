package hr.vspr.dpasic.tenniswithme.fragment.request_match_mvp;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.MatchesRestInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 24.1.2017..
 */

public class RequestMatchPresenterImpl implements RequestMatchPresenter {

    private RequestMatchView requestMatchView;

    public RequestMatchPresenterImpl(RequestMatchView requestMatchView) {
        this.requestMatchView = requestMatchView;
    }

    @Override
    public void requestMatch(final Match match) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                requestMatchRequest(token, match);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void requestMatchRequest(AccessToken token, final Match match) {
        final MatchesRestInterface matchesRestInterface = ServiceGenerator.createService(MatchesRestInterface.class, token);

        Call<ResponseBody> call = matchesRestInterface.requestMatch(match);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    requestMatchView.requestCompleted(match);

                } else {
                    requestMatchView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                requestMatchView.notifyRequestError(t.getMessage());
            }
        });
    }
}
