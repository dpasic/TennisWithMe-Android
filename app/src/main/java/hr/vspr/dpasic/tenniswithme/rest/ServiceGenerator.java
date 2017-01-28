package hr.vspr.dpasic.tenniswithme.rest;

import java.io.IOException;

import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edjapas on 19.12.2016..
 */

public class ServiceGenerator {

    //Use 10.0.2.2 for default AVD; 10.0.3.2 for genymotion; 192.168.1.252 for localhost
    /*private static final String BASE_URL = "http://10.0.2.2:8080/";*/
    private static final String BASE_URL = "http://192.168.1.252:8080/";

    public static <T> T createService(Class<T> serviceClass) {
        //Logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        return retrofit.create(serviceClass);
    }

    //For access with token
    public static <T> T createService(Class<T> serviceClass, final AccessToken token) {
        if (token != null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization",
                                    token.getTokenType() + " " + token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = retrofitBuilder.client(client).build();

            return retrofit.create(serviceClass);
        }

        return null;
    }
}
