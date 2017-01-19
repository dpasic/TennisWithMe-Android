package hr.vspr.dpasic.tenniswithme.common;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.rest.ServiceGenerator;
import hr.vspr.dpasic.tenniswithme.rest.api_interface.AccountRestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dpasic on 1/18/17.
 */

public class AccessTokenRefresher implements RestPublisher {

    private List<RestSubscriber> subscribers;
    private AccessToken token;

    public AccessTokenRefresher() {
        this.subscribers = new ArrayList<>();
    }

    public void refreshTokenIfNecessary() {
        token = SQLite.select().from(AccessToken.class).querySingle();

        long expirationTime = token.getExpiresDate().getTime();
        expirationTime = expirationTime - (1000 * 60); //1 minute before expiration
        long nowTime = new Date().getTime();

        //TODO: check if refresh token request works ok after access token expiration, otherwise use credentials to log in
        if (nowTime < expirationTime) {
            notifySubscriber(token);
            return;
        }

        final AccountRestInterface accountRestInterface = ServiceGenerator.createService(AccountRestInterface.class);

        Call<AccessToken> call = accountRestInterface.getRefreshedAccessToken(token.getRefreshToken(), "refresh_token");
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    //delete old access token from LocalDB
                    AccessToken tokenToDelete = SQLite.select().from(AccessToken.class).querySingle();
                    if (tokenToDelete != null) {
                        tokenToDelete.delete();
                    }

                    //save access token to LocalDB
                    AccessToken accessToken = response.body();
                    accessToken.save();

                    notifySubscriber(accessToken);

                } else {
                    notifySubscriber(token);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                notifySubscriber(token);
            }
        });
    }

    @Override
    public void registerSubscriber(RestSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscriber(AccessToken token) {
        for (RestSubscriber subscriber : subscribers) {
            subscriber.doRequest(this, token);
        }
    }
}
