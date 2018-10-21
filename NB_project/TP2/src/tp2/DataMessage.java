package tp2;

import org.json.JSONObject;

/**
 * Classe que representa uma mensagem de dados. Esse tipo de mensagem é descrito
 * na seção 3.1 da especificação.
 *
 * @author renatojuniortmp
 */
public class DataMessage implements Message {

    private final String source;
    private final String destination;
    private final String payload;

    private static final String DATA_MESSAGE_TYPE = "data";

    public DataMessage(String s, String d, String p) {
        this.source = s;
        this.destination = d;
        this.payload = p;
    }

    @Override
    public String getMessageJson() {
        JSONObject json = new JSONObject();
        json.put("type", DATA_MESSAGE_TYPE);
        json.put("source", this.source);
        json.put("destination", this.destination);
        json.put("payload", this.payload);
        return json.toString();
    }

}
