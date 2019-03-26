package website.petrov.noue.common.json.rpc;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

@SuppressWarnings("WeakerAccess")
public final class Response {
    public final String jsonrpc;
    public final Object result;
    public final Error error;
    public final Integer id;

    public Response(String jsonrpc, Object result, Error error, Integer id) {
        this.jsonrpc = jsonrpc;
        this.result = result;
        this.error = error;
        this.id = id;
    }

    @Contract(pure = true)
    @NonNull
    @Override
    public String toString() {
        return "Response{" +
                "jsonrpc=" + jsonrpc + ", " +
                "result=" + result + ", " +
                "error=" + error + ", " +
                "id=" + id +
                "}";
    }
}
