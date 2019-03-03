package website.petrov.noue.common.json.rpc;

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
}
