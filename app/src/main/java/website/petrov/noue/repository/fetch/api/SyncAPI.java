package website.petrov.noue.repository.fetch.api;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.Response;

public interface SyncAPI {
    @POST("./")
    Observable<Response> load(@Body @NonNull Request json);
}
