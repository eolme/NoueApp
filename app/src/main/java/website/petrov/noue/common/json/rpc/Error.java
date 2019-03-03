package website.petrov.noue.common.json.rpc;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("WeakerAccess")
public final class Error {
    public static final int PARSE_ERROR = -32700;
    public static final int INVALID_REQUEST = -32600;
    public static final int METHOD_NOT_FOUND = -32601;
    public static final int INVALID_PARAMS = -32602;
    public static final int INTERNAL_ERROR = -32603;
    public static final int SERVER_ERROR = -32000;
    @ErrorCode
    @NonNull
    public final Integer code;
    @NonNull
    public final String message;
    @Nullable
    public final Object data;

    public Error(@ErrorCode @NonNull Integer code, @NonNull String message, @Nullable Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PARSE_ERROR, INVALID_REQUEST, METHOD_NOT_FOUND, INVALID_PARAMS, INTERNAL_ERROR, SERVER_ERROR})
    public @interface ErrorCode {
    }
}
