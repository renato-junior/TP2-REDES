
package tp2;

/**
 *
 * @author renatojuniortmp
 */
public class RoutingTableEntry {

    private String ipDestination;
    private int distance;
    private String nextHop;
    private long addTime;

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

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

}
