package website.petrov.noue.repository.fetch.login;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.Response;

public interface LoginAPI {
    @POST("login")
    Observable<Response> attempt(@Body @NonNull Request json);
}
