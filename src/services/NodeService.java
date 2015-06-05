package services;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import ece454.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NodeService {
    public static ConcurrentHashMap<LocalTime, Heartbeat> BEMap = new ConcurrentHashMap<LocalTime, Heartbeat>(); 
    public static List<Node> seedList = new ArrayList<Node>();
    private static Random random = new Random();

    public Set<Heartbeat> getListOfBENodes() {
        Set<Heartbeat> nodeSet = new HashSet<Heartbeat>(BEMap.values()); 
        return nodeSet; 
    }

    public void addBENodeToList(Heartbeat beNode) {
        BEMap.put(LocalTime.now(), beNode);
    }

    public void removeOldNodes() {
        System.out.println("Removing old nodes");
        Iterator<LocalTime> timestamps = (BEMap.keySet()).iterator();
        timestamps.next();
        LocalTime currentTime = LocalTime.now();
        while (timestamps.hasNext()) {
          LocalTime timestamp = timestamps.next();
          if (timestamp.isBefore(currentTime.minusSeconds(1)))
            BEMap.remove(timestamp);
        }
    }

    public Heartbeat getBE(){
        // BE hashmap to list
        List<Heartbeat> BEArrayList = Arrays.asList( BEMap.values().toArray(new Heartbeat[0]) );
        int total_cores = 0;
        int counter;

        // Calculate total cores
        for(Heartbeat hb : BEArrayList){
            total_cores += hb.numberOfCores;
        }
        counter = (int)(random.nextDouble() * total_cores );

        for(Heartbeat hb : BEArrayList){
            counter -= hb.numberOfCores;
            if(counter <= 0){
                return hb;
            }
        }
        return null;
    }


    public class Node {
        String uri;
        int port;

        public Node(String uri, int port) {
           this.uri = uri;
           this.port = port;
        }
    }
}
