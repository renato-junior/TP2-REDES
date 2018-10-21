
package tp2;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renatojuniortmp
 */
public class RouterRIP {

    private DatagramSocket socket;  // Socket UDP
    private String ip;              // IP do roteador
    private int period;             // Período de atualização

    public static final int ROUTER_PORT = 55151;

    private List<RoutingTableEntry> knownRoutes;

    public RouterRIP(String ip, int period) {
        this.ip = ip;
        this.period = period;
        try {
            this.socket = new DatagramSocket(ROUTER_PORT, InetAddress.getByName(ip));
        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro ao criar o socket! " + ex.getLocalizedMessage());
            System.exit(0);
        }
        this.knownRoutes = new ArrayList<>();
    }

    void sendMessage(Message m, String ipToSend) {
        try {
            DatagramPacket p = new DatagramPacket(m.getMessageJson().getBytes(), m.getMessageJson().getBytes().length);
            p.setAddress(InetAddress.getByName(ipToSend));
            p.setPort(ROUTER_PORT);
            socket.send(p);

        } catch (IOException ex ) {
            System.out.println("Erro ao enviar o pacote! " + ex.getLocalizedMessage());
            System.out.println("É so isso.. não tem mais jeito... acabou!");
            System.exit(0);
        }
    }

    /**
     * Adiciona uma nova entrada na tabela de roteamento.
     * @param ipDest o ip do destino.
     * @param ipToSend o ip do next hop.
     * @param dist o peso do enlace;
     */
    void addNewRoute(String ipDest, String ipToSend, int dist) {
        RoutingTableEntry newRoute = new RoutingTableEntry();
        newRoute.setDistance(dist);
        newRoute.setIpDestination(ipDest);
        newRoute.setNextHop(ipDest);
        newRoute.setAddTime(System.currentTimeMillis());
        this.knownRoutes.add(newRoute);
        System.out.println("New route discovered!");
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public String getIp() {
        return ip;
    }

    public int getPeriod() {
        return period;
    }

    public List<RoutingTableEntry> getKnownRoutes() {
        return knownRoutes;
    }
}
