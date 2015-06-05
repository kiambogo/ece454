package services;

import ece454.*;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class NodeService {
    public static ConcurrentHashMap<LocalTime, Heartbeat> BEMap = new ConcurrentHashMap<LocalTime, Heartbeat>(); 
    public static List<Heartbeat> seedList = new ArrayList<Heartbeat>();

    public NodeService() {
      this.seedList = new ArrayList<Heartbeat>();
    }

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
}
