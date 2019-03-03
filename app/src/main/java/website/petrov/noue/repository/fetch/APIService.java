package website.petrov.noue.repository.fetch;

import org.jetbrains.annotations.Contract;

import javax.net.SocketFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import website.petrov.noue.common.component.SafeSocketFactory;

import static website.petrov.noue.utilities.Constants.API.BASE_URL;

public class APIService {
    private static volatile Retrofit sInstance;

    private APIService() {} // hide constructor

    @Contract("-> !null")
    public static Retrofit getInstance() {
        if (sInstance == null) {
            synchronized (Retrofit.class) {
                if (sInstance == null) {
                    final SocketFactory socketFactory = new SafeSocketFactory();
                    final OkHttpClient client = new OkHttpClient.Builder()
                            .socketFactory(socketFactory)
                            .build();

                    sInstance = new Retrofit.Builder()
                            .client(client)
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                            .build();
                }
            }
        }

        return sInstance;
    }
}
