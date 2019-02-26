package website.petrov.noue.repository.fetch;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import website.petrov.noue.common.model.MessageResponse;

public interface LoginAPI {
    @POST("login/email")
    Observable<MessageResponse> request(@Body String email, @Header("X-Device-Id") String deviceId);
}
