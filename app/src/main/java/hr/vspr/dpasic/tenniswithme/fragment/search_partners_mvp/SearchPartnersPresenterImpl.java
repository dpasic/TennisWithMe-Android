package hr.vspr.dpasic.tenniswithme.fragment.search_partners_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.PlayersRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 20.1.2017..
 */

public class SearchPartnersPresenterImpl implements SearchPartnersPresenter {

    private SearchPartnersView searchPartnersView;

    public SearchPartnersPresenterImpl(SearchPartnersView searchPartnersView) {
        this.searchPartnersView = searchPartnersView;
    }

    @Override
    public void searchPartnersByQueries(final String city, final String gender, final String skill) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                searchPartnersByQueriesRequest(city, gender, skill, token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void searchPartnersByQueriesRequest(String city, String gender, String skill, AccessToken token) {
        final PlayersRestInterface playersRestInterface = ServiceGenerator.createService(PlayersRestInterface.class, token);

        Call<List<Player>> call = playersRestInterface.getPlayers(city, gender, skill);
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if (response.isSuccessful()) {
                    searchPartnersView.updateListViewAdapter(response.body());

                } else {
                    searchPartnersView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                searchPartnersView.notifyRequestError(t.getMessage());
            }
        });
    }
}
