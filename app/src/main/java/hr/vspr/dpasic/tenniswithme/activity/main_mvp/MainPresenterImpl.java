package hr.vspr.dpasic.tenniswithme.activity.main_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.IdentityPlayerRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 3.1.2017..
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void setUserInfo() {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                setUserInfoRequest(token);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void setUserInfoRequest(AccessToken token) {
        final IdentityPlayerRestInterface identityPlayerRestInterface = ServiceGenerator.createService(IdentityPlayerRestInterface.class, token);

        Call<User> call = identityPlayerRestInterface.getUserInfo();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mainView.setNavigationUserInfo(response.body());

                } else {
                    mainView.notifyRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mainView.notifyRequestError(t.getMessage());
            }
        });
    }
}
