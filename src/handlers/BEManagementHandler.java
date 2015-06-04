package handlers;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import ece454.*;
import services.*;

import org.mindrot.jbcrypt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BEManagementHandler implements A1Management.Iface {
    PerfCountersService countersService = new PerfCountersService();
    NodeService nodeService = new NodeService();

    public PerfCounters getPerfCounters() throws org.apache.thrift.TException {
        int secondsUp = countersService.getSecondsUp(); 
        int requestsReceived = countersService.getRequestsReceived(); 
        int requestsCompleted = countersService.getRequestsCompleted(); 
        return new PerfCounters(secondsUp, requestsReceived, requestsCompleted);
    }

    public Set<Heartbeat> getUpdatedBEList() throws org.apache.thrift.TException {
        return nodeService.getListOfBENodes(); 
    }

    public void sendHeartbeat(Heartbeat heartbeat) throws org.apache.thrift.TException {
        System.out.println("SENDING HEARTBEAT: ");        
        System.out.println("Hostname: "+heartbeat.hostname);        
        System.out.println("# of Cores: "+heartbeat.numberOfCores);        
        System.out.println("Service Port: "+heartbeat.servicePort);        
        System.out.println("Management Port: "+heartbeat.managementPort);        
    }

    public List<String> getGroupMembers() throws org.apache.thrift.TException {
      ArrayList<String> list = new ArrayList<String>();
      list.add("Christopher Poenaru");
      list.add("Anthony Clark");
      return list;       
    }
}

