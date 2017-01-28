package hr.vspr.dpasic.tenniswithme.fragment.matches_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.FriendsRestInterface;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.MatchesRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 24.1.2017..
 */

public class MatchesPresenterImpl implements MatchesPresenter {

    private MatchesView matchesView;

    public MatchesPresenterImpl(MatchesView matchesView) {
        this.matchesView = matchesView;
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
        final MatchesRestInterface matchesRestInterface = ServiceGenerator.createService(MatchesRestInterface.class, token);

        Call<List<Match>> call = matchesRestInterface.getActiveMatches();
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    matchesView.updateListView(response.body());

                } else {
                    matchesView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                matchesView.notifyRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void prepareRequestedListView() {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                prepareRequiredListViewRequest(token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void prepareRequiredListViewRequest(AccessToken token) {
        final MatchesRestInterface matchesRestInterface = ServiceGenerator.createService(MatchesRestInterface.class, token);

        Call<List<Match>> call = matchesRestInterface.getRequestedMatches();
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    matchesView.updateListView(response.body());

                } else {
                    matchesView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                matchesView.notifyRequestError(t.getMessage());
            }
        });
    }
}
