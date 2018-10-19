package tp2;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.HashMap;


public class Router {
    private Map<String, RoutingTableEntry> known_routes = new HashMap<String, RoutingTableEntry>();
    private RouterRIP conections;
    
    public static void main(String[] args) {
        DataMessage dm = new DataMessage("Source IP", "Destination IP", "Data Payload");
        System.out.println(dm.getMessageJson());
        UpdateMessage um = new UpdateMessage("Source IP", "Destination IP");
        um.addDistance("IP 1", 2);
        um.addDistance("IP 2", 5);
        um.addDistance("IP 3", 10);
        System.out.println(um.getMessageJson());
        TraceMessage tm = new TraceMessage("Source IP", "Destination IP");
        tm.addHop("IP 1");
        tm.addHop("IP 2");
        tm.addHop("IP 3");
        System.out.println(tm.getMessageJson());
        Router a = new Router();
    }
    
    void add_new_route(String ip_dest,String ip_to_send,int dist){
        if(this.known_routes.containsKey(ip_dest)){
            if(this.known_routes.get(ip_dest).getDistance() > dist){
                RoutingTableEntry new_route = new RoutingTableEntry();
                new_route.setDistance(dist);
                new_route.setIpDestination(ip_dest);
                new_route.addNextHop(ip_dest);
                this.known_routes.replace(ip_dest, new_route);
                System.out.println("New best route discovered!");
            }
            if(this.known_routes.get(ip_dest).getDistance() == dist){
                this.known_routes.get(ip_dest).addNextHop(ip_to_send);
                System.out.println("New equal route discovered!");
            }
        }
        else{
            RoutingTableEntry new_route = new RoutingTableEntry();
            new_route.setDistance(dist);
            new_route.setIpDestination(ip_dest);
            new_route.addNextHop(ip_dest);
            this.known_routes.put(ip_dest,new_route);
            System.out.println("New route discovered!");
        }
    }
        
}