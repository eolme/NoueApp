package website.petrov.noue.model.request;

import androidx.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public class DefaultRequest {
    public final String deviceId;
    public final String instanceId;

    public DefaultRequest(@NonNull String deviceId, @NonNull String instanceId) {
        this.deviceId = deviceId;
        this.instanceId = instanceId;
    }
}
