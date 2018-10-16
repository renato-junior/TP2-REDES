package tp2;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Router {

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
    }

}