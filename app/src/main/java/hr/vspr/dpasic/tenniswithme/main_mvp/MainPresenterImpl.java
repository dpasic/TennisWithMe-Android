package hr.vspr.dpasic.tenniswithme.main_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Credentials;
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
        AccessToken token = SQLite.select().from(AccessToken.class).querySingle();
        final IdentityPlayerRestInterface identityPlayerRestInterface = ServiceGenerator.createService(IdentityPlayerRestInterface.class, token);

        Call<User> call = identityPlayerRestInterface.getUserInfo();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mainView.setNavigationUserInfo(response.body());

                } else {
                    mainView.showError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mainView.showError(t.getMessage());
            }
        });
    }
}
