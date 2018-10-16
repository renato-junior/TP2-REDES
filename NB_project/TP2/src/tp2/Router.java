package tp2;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import org.json.*;

public class Router {

    public static void main(String[] args) {
        DataMessage dm = new DataMessage("Source IP", "Destination IP", "Data Payload");
        System.out.println(dm.getMessageJson());
        UpdateMessage um = new UpdateMessage("Source IP", "Destination IP");
        um.addDistance("IP 1", 2);
        um.addDistance("IP 2", 5);
        um.addDistance("IP 3", 10);
        System.out.println(um.getMessageJson());
    }

}

class RouterRIP {

    private DatagramSocket socket;  // Socket UDP
    private String ip;              // IP do roteador
    private int period;             // Período de atualização

    private static final int PORTA_ROTEADOR = 55151;

    public RouterRIP(String ip, int period) {
        this.ip = ip;
        this.period = period;
        try {
            this.socket = new DatagramSocket(PORTA_ROTEADOR, InetAddress.getByName(ip));
        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro ao criar o socket! " + ex.getLocalizedMessage());
            System.exit(0);
        }
    }

}

/**
 * Classe que representa uma mensagem de dados. Esse tipo de mensagem é descrito
 * na seção 3.1 da especificação.
 *
 * @author renatojuniortmp
 */
class DataMessage {

    private final String source;
    private final String destination;
    private final String payload;

    private static final String DATA_MESSAGE_TYPE = "data";

    public DataMessage(String s, String d, String p) {
        this.source = s;
        this.destination = d;
        this.payload = p;
    }

    public String getMessageJson() {
        JSONObject json = new JSONObject();
        json.put("type", DATA_MESSAGE_TYPE);
        json.put("source", this.source);
        json.put("destination", this.destination);
        json.put("payload", this.payload);
        return json.toString();
    }
}

/**
 * Classe que representa uma mensagem de atualização. Esse tipo de mensagem é
 * descrito na seção 4.1.1 da especificação.
 *
 * @author renatojuniortmp
 */
class UpdateMessage {

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

    public String getMessageJson() {
        JSONObject json = new JSONObject();
        json.put("type", UPDATE_MESSAGE_TYPE);
        json.put("source", this.source);
        json.put("destination", this.destination);
        json.put("distances", distances);
        return json.toString();
    }
}
