package tp2;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoutingTableEntry other = (RoutingTableEntry) obj;
        if (this.distance != other.distance) {
            return false;
        }
        if (!Objects.equals(this.ipDestination, other.ipDestination)) {
            return false;
        }
        if (!Objects.equals(this.nextHop, other.nextHop)) {
            return false;
        }
        return true;
    }

}
