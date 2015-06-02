import ece454.*;

import java.util.Set;
import java.io.IOException;  
import org.apache.thrift.TException; 
import org.apache.thrift.async.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

public class FEManagementClient {
  private static String uri;
  private static int port;

  public FEManagementClient(String uri, int port) { 
    uri = uri;
    port = port;
  }

  public static void getPerfCounters() {
    try {
      TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
      TAsyncClientManager clientManager = new TAsyncClientManager();
      TNonblockingTransport transport = new TNonblockingSocket(uri, port); 
      A1Management.AsyncClient client = new A1Management.AsyncClient(
          protocolFactory, clientManager, transport);

      client.getPerfCounters(new PerfCountersCallBack());
    } catch (TException x) {
      x.printStackTrace();
    } catch (IOException e) {  
      e.printStackTrace();
    } 
  }

  public static void getUpdatedBEList() {
      try {
          TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
          TAsyncClientManager clientManager = new TAsyncClientManager();
          TNonblockingTransport transport = new TNonblockingSocket(uri, port); 
          A1Management.AsyncClient client = new A1Management.AsyncClient(
            protocolFactory, clientManager, transport);

          client.getPerfCounters(new BEListCallBack());
      } catch (TException x) {
        x.printStackTrace();
      } catch (IOException e) {  
        e.printStackTrace();
      } 
  }

  public static void sendHeartbeat(Heartbeat heartbeat) {
      try {
          TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
          TAsyncClientManager clientManager = new TAsyncClientManager();
          TNonblockingTransport transport = new TNonblockingSocket(uri, port); 
          A1Management.AsyncClient client = new A1Management.AsyncClient(
          protocolFactory, clientManager, transport);

          client.sendHeartbeat(heartbeat, new SendHeartbeatCallBack());
      } catch (TException x) {
        x.printStackTrace();
      } catch (IOException e) {  
        e.printStackTrace();
      } 
  }

  static class PerfCountersCallBack 
    implements AsyncMethodCallback<A1Management.AsyncClient.getPerfCounters_call> {
        public void onComplete(A1Management.AsyncClient.getPerfCounters_call perfCountersCall) {
            try {
                PerfCounters perfCounters = perfCountersCall.getResult();
                System.out.println("Perf counters from server: " + perfCounters);
            } catch (TException e) {
                e.printStackTrace();
            }
        }
    
        public void onError(Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
        }
  }    

  static class BEListCallBack 
      implements AsyncMethodCallback<A1Management.AsyncClient.getUpdatedBEList_call> {
      public void onComplete(A1Management.AsyncClient.getUpdatedBEList_call beListCall) {
        try {
          Set<Heartbeat> beList = beListCall.getResult();
          System.out.println("BE List from server: " + beList);
        } catch (TException e) {
          e.printStackTrace();
        }
      }

      public void onError(Exception e) {
        System.out.println("Error: ");
        e.printStackTrace();
      }
  }    

  static class SendHeartbeatCallBack  
      implements AsyncMethodCallback<A1Management.AsyncClient.sendHeartbeat_call> {
      public void onComplete(A1Management.AsyncClient.sendHeartbeat_call heartbeatCall) {
        System.out.println("Heartbeat sent");
      }

      public void onError(Exception e) {
        System.out.println("Error: ");
        e.printStackTrace();
      }
  }    
}
