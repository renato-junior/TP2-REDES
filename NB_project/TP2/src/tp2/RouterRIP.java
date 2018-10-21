/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author renatojuniortmp
 */
public class RouterRIP {

    private DatagramSocket socket;  // Socket UDP
    private String ip;              // IP do roteador
    private int period;             // Período de atualização

    private static final int PORTA_ROTEADOR = 55151;

    private Map<String, RoutingTableEntry> knownRoutes;

    public RouterRIP(String ip, int period) {
        this.ip = ip;
        this.period = period;
        try {
            this.socket = new DatagramSocket(PORTA_ROTEADOR, InetAddress.getByName(ip));
        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro ao criar o socket! " + ex.getLocalizedMessage());
            System.exit(0);
        }
        this.knownRoutes = new HashMap<>();
    }

    void sendDataMessage(DataMessage m, String ipToSend, String port) {
        try {
            DatagramPacket p = new DatagramPacket(m.getMessageJson().getBytes(), m.getMessageJson().getBytes().length);
            p.setAddress(InetAddress.getByName(ipToSend));
            p.setPort(Integer.parseInt(port));
            socket.send(p);

        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro ao criar o socket! " + ex.getLocalizedMessage());
            System.exit(0);
        } catch (Exception e2) {
            System.out.println("É so isso.. não tem mais jeito... acabou!");
        }
    }

    void addNewRoute(String ipDest, String ipToSend, int dist) {
        if (this.knownRoutes.containsKey(ipDest)) {
            if (this.knownRoutes.get(ipDest).getDistance() > dist) {
                RoutingTableEntry newRoute = new RoutingTableEntry();
                newRoute.setDistance(dist);
                newRoute.setIpDestination(ipDest);
                newRoute.addNextHop(ipDest);
                this.knownRoutes.replace(ipDest, newRoute);
                System.out.println("New best route discovered!");
            }
            if (this.knownRoutes.get(ipDest).getDistance() == dist) {
                this.knownRoutes.get(ipDest).addNextHop(ipToSend);
                System.out.println("New equal route discovered!");
            }
        } else {
            RoutingTableEntry newRoute = new RoutingTableEntry();
            newRoute.setDistance(dist);
            newRoute.setIpDestination(ipDest);
            newRoute.addNextHop(ipDest);
            this.knownRoutes.put(ipDest, newRoute);
            System.out.println("New route discovered!");
        }
    }

}
