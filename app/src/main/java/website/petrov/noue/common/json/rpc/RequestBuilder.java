package website.petrov.noue.common.json.rpc;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.security.SecureRandom;

public final class RequestBuilder {
    private static final String DEFAULT_VERSION = "2.0";
    private static final String DEFAULT_METHOD = "default";

    private String jsonrpc = null;
    private String method = null;
    private Object[] params = null;
    private int id = -1;

    public RequestBuilder() {
    }

    @Contract("_ -> this")
    public RequestBuilder withVersion(@NonNull String jsonrpc) {
        this.jsonrpc = jsonrpc;

        return this;
    }

    @Contract("_ -> this")
    public RequestBuilder withId(int id) {
        this.id = id;

        return this;
    }

    @Contract("_ -> this")
    public RequestBuilder withMethod(@NonNull String method) {
        this.method = method;

        return this;
    }

    @Contract("_ -> this")
    public RequestBuilder withParams(@NonNull Object[] params) {
        this.params = params;

        return this;
    }

    @NonNull
    @Contract(" -> new")
    public Request build() {
        if (jsonrpc == null) {
            jsonrpc = DEFAULT_VERSION;
        }

        if (id < 0) {
            id = Math.abs(new SecureRandom().nextInt());
        }

        if (method == null) {
            method = DEFAULT_METHOD;
        }

        return new Request(jsonrpc, method, params, id);
    }
}
