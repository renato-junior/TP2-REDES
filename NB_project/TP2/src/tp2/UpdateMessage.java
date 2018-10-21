package tp2;

import java.util.HashMap;
import org.json.JSONObject;

/**
 * Classe que representa uma mensagem de atualização. Esse tipo de mensagem é
 * descrito na seção 4.1.1 da especificação.
 *
 * @author renatojuniortmp
 */
public class UpdateMessage implements Message {

    private final String source;
    private final String destination;
    private HashMap<String, Integer> distances;

    private static final String UPDATE_MESSAGE_TYPE = "update";

    public UpdateMessage(String s, String d) {
        this.source = s;
        this.destination = d;
        this.distances = new HashMap<>();
    }

    public void addDistance(String ip, int distance) {
        this.distances.put(ip, distance);
    }

    @Override
    public String getMessageJson() {
        JSONObject json = new JSONObject();
        json.put("type", UPDATE_MESSAGE_TYPE);
        json.put("source", this.source);
        json.put("destination", this.destination);
        json.put("distances", distances);
        return json.toString();
    }

}
