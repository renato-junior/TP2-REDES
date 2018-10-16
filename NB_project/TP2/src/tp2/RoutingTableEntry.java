/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

/**
 *
 * @author renatojuniortmp
 */
public class RoutingTableEntry {
    
    private String ipDestination;
    private int distance;
    private String nextHop;
    
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

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }
    
}
