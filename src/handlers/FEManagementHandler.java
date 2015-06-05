package handlers;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import java.util.Set;
import java.time.LocalTime;

import ece454.*;
import services.*;

import java.util.List;
import java.util.ArrayList;

public class FEManagementHandler implements A1Management.Iface {
    PerfCountersService countersService = new PerfCountersService();
    NodeService nodeService = new NodeService();

    public PerfCounters getPerfCounters() throws org.apache.thrift.TException {
        int secondsUp = countersService.getSecondsUp(); 
        int requestsReceived = countersService.getRequestsReceived(); 
        int requestsCompleted = countersService.getRequestsCompleted(); 
        return new PerfCounters(secondsUp, requestsReceived, requestsCompleted);
    }

    public UpdatedNodeList getUpdatedBEList() throws org.apache.thrift.TException {
        UpdatedNodeList list = new UpdatedNodeList(LocalTime.now().toString(), nodeService.getListOfBENodes()); 
        return list; 
    }

    public void beat(Heartbeat heartbeat) throws org.apache.thrift.TException {
      nodeService.addBENodeToList(heartbeat);        
    }

    public List<String> getGroupMembers() throws org.apache.thrift.TException {
      ArrayList<String> list = new ArrayList<String>();
      list.add("cpoenaru");
      list.add("am3clark");
      return list;       
    }
}

