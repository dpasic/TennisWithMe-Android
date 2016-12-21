package hr.vspr.dpasic.tenniswithme.login_mvp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Credentials;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.AccountRestInterface;
import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edjapas on 19.12.2016..
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void doLogin() {
        final LoginViewModel loginViewModel = loginView.getLoginViewModel();

        final AccountRestInterface accountRestInterface = ServiceGenerator.createService(AccountRestInterface.class);

        Call<AccessToken> call = accountRestInterface.getAccessToken(loginViewModel.getEmail(), loginViewModel.getPassword(), "password");
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    //delete old access token from LocalDB
                    AccessToken tokenToDelete = SQLite.select().from(AccessToken.class).querySingle();
                    tokenToDelete.delete();

                    //save access token to LocalDB
                    AccessToken accessToken = response.body();
                    accessToken.save();

                    //delete old credentials from LocalDB
                    Credentials credentialsToDelete = SQLite.select().from(Credentials.class).querySingle();
                    credentialsToDelete.delete();

                    //save credentials to LocalDB
                    Credentials credentials = new Credentials(loginViewModel.getEmail(), loginViewModel.getPassword());
                    credentials.save();

                    loginView.goToApp();

                } else {
                    loginView.showLoginError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                loginView.showLoginError(t.getMessage());
            }
        });
    }

    @Override
    public boolean tokenExists() {
        AccessToken token = SQLite.select().from(AccessToken.class).querySingle();
        return (token != null);
    }
}
