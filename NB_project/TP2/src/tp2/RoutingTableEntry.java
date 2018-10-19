/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;
import java.util.ArrayList;
/**
 *
 * @author renatojuniortmp
 */
public class RoutingTableEntry {
    
    private String ipDestination;
    private int distance;
    final private ArrayList<String> nextHop = new ArrayList<String>();
    
    public String getIpDestination() {
        return ipDestination;
    }

    public void setIpDestination(String ipDestination) {
        this.ipDestination = ipDestination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ArrayList<String> getNextHop() {
        return nextHop;
    }

    public void addNextHop(String nextHop) {
        this.nextHop.add(nextHop);
    }
    
}
