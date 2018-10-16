package tp2;


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.json.*;

public class Router {

    public static void main(String[] args) {

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
            System.out.println("Erro ao criar o socket! Encerrando...");
            System.exit(0);
        }
    }

}

class DataMessage {

    private String source;
    private String destination;
    private String payload;

    public DataMessage(String s, String d, String p) {
        this.source = s;
        this.destination = d;
        this.payload = p;
    }

    public String getMessageJson() {
        return "Hello!";
    }
}
