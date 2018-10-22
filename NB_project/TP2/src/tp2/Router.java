package tp2;
import java.util.ArrayList;

public class Router {
    public static void main(String[] args) {
        ArrayList<RouterRIP> routers = new ArrayList<RouterRIP>();
        routers.add(new RouterRIP("127.0.0.1",10));
        routers.add(new RouterRIP("127.0.0.2",10));
        routers.add(new RouterRIP("127.0.0.3",10));
        
        routers.get(0).addNewRoute("127.0.0.3","127.0.0.2",4);
        routers.get(1).addNewRoute("127.0.0.3","127.0.0.3",3);
        DataMessage dataMessage = new DataMessage("127.0.0.1","127.0.0.3","OLA!");
        routers.get(0).sendMessage(dataMessage,"127.0.0.2");
        routers.get(1).receiveMessage();
        routers.get(2).receiveMessage();
    }

}