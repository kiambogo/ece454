package services;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import ece454.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class NodeService {
    public static long lastUpdated = new Date().getTime();
    public static List<TimedHeartbeat> BEList = new CopyOnWriteArrayList<TimedHeartbeat>();
    private static Random random = new Random();
    public static List<Heartbeat> seedList = new ArrayList<Heartbeat>();

    public List<TimedHeartbeat> getListOfBENodes() {
        return BEList; 
    }

    public void addBENodeToList(Heartbeat beNode) {
      boolean exists = false;
        for (TimedHeartbeat node : BEList) {
          if (node.heartbeat.hostname.equals(beNode.hostname) && node.heartbeat.managementPort == beNode.managementPort) {
            BEList.remove(node);
          }
        }
        BEList.add(new TimedHeartbeat(new Date().getTime(), beNode));
    }

    public void removeOldNodes() {
      long currentTime = new Date().getTime();
      long testTime = currentTime - 1000;
      for (TimedHeartbeat node: BEList) {
        if (node.timestamp < testTime) {
          BEList.remove(node);
        }
      }
    }

    public Heartbeat getBE() {
        // BE hashmap to list
        int total_cores = 0;
        int counter;

        // Calculate total cores
        for(TimedHeartbeat node : BEList){
            total_cores += node.heartbeat.numberOfCores;
        }
        counter = (int)(random.nextDouble() * total_cores );

        for(TimedHeartbeat node: BEList){
            counter -= node.heartbeat.numberOfCores;
            if(counter <= 0){
                return node.heartbeat;
            }
        }
        return null;
    }
}
