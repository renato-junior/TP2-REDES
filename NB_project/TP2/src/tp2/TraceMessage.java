package tp2;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Classe que representa uma mensagem de trace. Esse tipo de mensagem é descrito
 * na seção 5.1 da especificação.
 *
 * @author renatojuniortmp
 */
class TraceMessage {

    private final String source;
    private final String destination;
    private List<String> hops;

    private static final String TRACE_MESSAGE_TYPE = "trace";

    public TraceMessage(String s, String d) {
        this.source = s;
        this.destination = d;
        this.hops = new ArrayList<>();
    }

    public void addHop(String ip) {
        this.hops.add(ip);
    }

    public String getMessageJson() {
        JSONObject json = new JSONObject();
        json.put("type", TRACE_MESSAGE_TYPE);
        json.put("source", this.source);
        json.put("destination", this.destination);
        json.put("hops", hops);
        return json.toString();
    }

}
