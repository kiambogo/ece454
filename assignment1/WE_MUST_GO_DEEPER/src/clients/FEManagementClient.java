package clients;

import ece454.*;

import java.util.Set;
import java.io.IOException;  
import org.apache.thrift.TException; 
import org.apache.thrift.async.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

import java.util.ArrayList;

public class FEManagementClient {
    private String uri;
    private int port;

    public FEManagementClient(String uri, int port) { 
        this.uri = uri;
        this.port = port;
    }

  public PerfCounters getPerfCounters() {
      PerfCounters result = new PerfCounters(0, 0, 0);
      try {
          TTransport transport;
          transport = new TFramedTransport(new TSocket(uri, port));
          TProtocol protocol = new TBinaryProtocol(transport);
          transport.open();
          A1Management.Client client = new A1Management.Client(protocol);
          result = client.getPerfCounters();
          transport.close();
      } catch (TException x) {
          x.printStackTrace();
      }
      return result;
  }

  public UpdatedNodeList getUpdatedBEList() {
    UpdatedNodeList result = new UpdatedNodeList(0, new ArrayList<TimedHeartbeat>());
      try {
          TTransport transport;
          transport = new TFramedTransport(new TSocket(uri, port));
          TProtocol protocol = new TBinaryProtocol(transport);
          transport.open();
          A1Management.Client client = new A1Management.Client(protocol);
          result = client.getUpdatedBEList();
          transport.close();
      } catch (TException x) {
          x.printStackTrace();
      }
      return result;
  }

  public void beat(Heartbeat heartbeat) {
      try {
          TTransport transport;
          transport = new TFramedTransport(new TSocket(uri, port));
          TProtocol protocol = new TBinaryProtocol(transport);
          A1Management.Client client = new A1Management.Client(protocol);
          transport.open();
          client.beat(heartbeat);
          transport.close();
      } catch (TException x) {
          x.printStackTrace();
      }
  }
}
