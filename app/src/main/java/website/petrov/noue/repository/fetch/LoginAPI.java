package website.petrov.noue.repository.fetch;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import website.petrov.noue.BuildConfig;
import website.petrov.noue.repository.fetch.model.LoginResponse;

public interface LoginAPI {
    @Headers({
            "User-Agent: " + BuildConfig.APPLICATION_ID + "/" + BuildConfig.VERSION_NAME
    })
    @POST("login/email")
    Call<LoginResponse> attemptLogin(@Body String email, @Header("X-Device-Id") String deviceId);
}
