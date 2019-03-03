package website.petrov.noue.model.request;

import androidx.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public class LoginRequest {
    public final String deviceId;
    public final String instanceId;
    public final String email;

    public LoginRequest(@NonNull String deviceId, @NonNull String instanceId, @NonNull String email) {
        this.deviceId = deviceId;
        this.instanceId = instanceId;
        this.email = email;
    }
}
