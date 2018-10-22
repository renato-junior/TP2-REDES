
package tp2;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
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

    public void receiveMessage(){
        byte[] buf = new byte[1024];
        DatagramPacket p = new DatagramPacket(buf,buf.length);
        try{            
            socket.receive(p);
            JSONObject messageJson = new JSONObject(new String(p.getData()));
            if(messageJson.getString("type").equals("data")){
                if(messageJson.getString("destination").equals(this.ip)){
                    System.out.println(messageJson.getString("payload"));
                }
                else{
                    DataMessage m = new DataMessage(messageJson.getString("source"),messageJson.getString("destination"),messageJson.getString("payload"));
                    for(RoutingTableEntry i:this.knownRoutes){
                        if(i.getIpDestination().equals(messageJson.getString("destination"))){
                            sendMessage(m,i.getNextHop());
                            break;
                        }
                    }
                }
            }
            else if(messageJson.getString("type").equals("update")){
                if(messageJson.getString("destination").equals(this.ip)){
                    messageJson.getJSONObject("distances");
                    Map<String,Object> mMap = messageJson.getJSONObject("distances").toMap();
                    HashMap<String,Integer> dists =  new HashMap<String,Integer>();
                    for(String key:mMap.keySet()){
                        dists.put(key,(Integer) mMap.get(key));
                    }
                    for(String key:dists.keySet()){
                        if(this.knownRoutes.contains(key)){
                            for(RoutingTableEntry i:this.knownRoutes){
                                if(i.getIpDestination().equals(key) && 
                                   i.getDistance() > dists.get(key)){
                                    i.setNextHop(messageJson.getString("source"));
                                    i.setDistance(dists.get(key));
                                }
                            }
                        }
                        else{
                            addNewRoute(key,messageJson.getString("source"),dists.get(key));
                        }
                        
                    }
                }
            }
            else if(messageJson.getString("type").equals("trace")){
                if(messageJson.getString("destination").equals(this.ip)){
                    DataMessage mData = new DataMessage(this.ip,messageJson.getString("source"),messageJson.getString("payload"));
                    for(RoutingTableEntry i:this.knownRoutes){
                        if(i.getIpDestination().equals(messageJson.getString("source"))){
                            sendMessage(mData,i.getNextHop());
                            break;
                        }
                    }                    
                }
                else{
                    TraceMessage mTrace = new TraceMessage(messageJson.getString("source"),messageJson.getString("destination"));
                    for(int i = 0; i < messageJson.getJSONArray("hops").length(); i++){
                        mTrace.addHop(messageJson.getJSONArray("hops").getString(i));
                    }
                    mTrace.addHop(this.ip);
                }
            }
            
        }catch(Exception e0){
            System.out.println("Error: " + e0.getMessage());
        }
    }
    
    public void sendMessage(Message m, String ipToSend) {
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
