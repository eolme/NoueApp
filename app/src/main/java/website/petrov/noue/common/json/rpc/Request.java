package website.petrov.noue.common.json.rpc;

@SuppressWarnings("WeakerAccess")
public final class Request {
    public final String jsonrpc;
    public final String method;
    public final Object[] params;
    public final Integer id;

    public Request(String jsonrpc, String method, Object[] params, Integer id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.params = params;
        this.id = id;
    }
}
