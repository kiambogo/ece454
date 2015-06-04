import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import ece454.*;
import services.*;
import handlers.*;

import java.util.HashMap;
import java.util.concurrent.*;
import java.net.UnknownHostException;

public class FEServer {

  public static FEPasswordSyncHandler passwordHandler;
  public static A1Password.Processor passwordProcessor;
  public static FEManagementHandler managementHandler; 
    public static A1Management.Processor managementProcessor;

  public static void main(String [] args) {
    final int port = 9090; 

    try {
      passwordHandler = new FEPasswordSyncHandler();
      passwordProcessor = new A1Password.Processor(passwordHandler);

      managementHandler = new FEManagementHandler();
      managementProcessor = new A1Management.Processor(managementHandler);

      Runnable a1Password = new Runnable() {
        public void run() {
          password(passwordProcessor, port);
        }
      };      
      Runnable a1Management = new Runnable() {
        public void run() {
          management(managementProcessor, port);
        }
      };

      new Thread(a1Password).start();
      new Thread(a1Management).start();

    } catch (Exception x) {
        x.printStackTrace();
    }
  }

  private static void password(A1Password.Processor processor, int port) {
    try {
      TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9091);
      TServer server = new TNonblockingServer(
              new TNonblockingServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the FE password server on port 9091 "  );
      PerfCountersService countersService = new PerfCountersService();
      countersService.setStartTime();
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void management(A1Management.Processor processor, int port) {
      try {
          TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
          TServer server = new TNonblockingServer(
                  new TNonblockingServer.Args(serverTransport).processor(processor));

          System.out.println("Starting the FE management server on port " + port);
          PerfCountersService countersService = new PerfCountersService();
          countersService.setStartTime();
          ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
          scheduler.scheduleAtFixedRate(new HeartbeatBroadcast(), 100, 100, TimeUnit.MILLISECONDS);
          server.serve();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  private static class HeartbeatBroadcast implements Runnable {
    public void run(){
        managementHandler = new FEManagementHandler();
        //String hostname = InetAddress.getLocalHost().getHostName();
        //int cores = Runtime.getRuntime().availableProcessors();
        //Heartbeat hb = new Heartbeat(hostname, cores, pPort, mPort);
        //managementHandler.sendHeartbeat(hb);
    }
  }
}
