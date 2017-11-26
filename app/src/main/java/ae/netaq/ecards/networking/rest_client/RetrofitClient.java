package ae.netaq.ecards.networking.rest_client;

import java.io.IOException;

import ae.netaq.ecards.misc.AppPreferences;
import ae.netaq.ecards.networking.rest_client.api_interfaces.MainApiInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Muhammed Refaat on 2/7/2017.
 */

public class RetrofitClient {

    private static MainApiInterface apiInterface;

    private RetrofitClient() {

    }

    static {
        initClient();
    }

    private static Retrofit retrofit = null;

    private static void initClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient= new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(AppPreferences.URL).build();
        }
        apiInterface = retrofit.create(MainApiInterface.class);
    }

    public static MainApiInterface getAdapter() {
        return apiInterface;
    }

    /**
     * To add the required headers after getting it
     */
    public static void buildHeaders() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient= new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(AppPreferences.URL).build();
        apiInterface = retrofit.create(MainApiInterface.class);
    }

}
