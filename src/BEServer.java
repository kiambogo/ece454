import org.apache.thrift.server.*;
import org.apache.thrift.protocol.*; 
import org.apache.thrift.transport.*;
import org.apache.thrift.TProcessorFactory;  
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.TException;

import ece454.*;
import services.*;
import handlers.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.*;
import java.util.HashMap;

public class BEServer {

  public static BEPasswordHandler passwordHandler;
  public static A1Password.Processor passwordProcessor;
  public static BEManagementHandler managementHandler;
  public static A1Management.Processor managementProcessor;
  public static int pPort;
  public static int mPort;

  public static void main(String [] args) {

//    if (args.length < 5 || 
//        !args.contains("host") || !args.contains("pport") || !args.contains("mport") ||
//        !args.contains("ncores") || !args.contains("seeds")) {
//      System.out.println("Please enter 'simple' ");
//      System.exit(0);
//    }

    pPort = 10100; 
    mPort = 10101; 
      try {
        Runnable a1Password = new Runnable() {
          public void run() {
            password(pPort);
          }
        };      
        Runnable a1Management = new Runnable() {
          public void run() {
            management(mPort);
          }
        };

        new Thread(a1Password).start();
        new Thread(a1Management).start();

        } catch (Exception x) {
          x.printStackTrace();
        }
  }

  private static void password(int port) {
    try {
      passwordHandler = new BEPasswordHandler();
      passwordProcessor = new A1Password.Processor(passwordHandler);

      TNonblockingServerSocket socket = new TNonblockingServerSocket(port);  
      THsHaServer.Args arg = new THsHaServer.Args(socket); 
      arg.protocolFactory(new TBinaryProtocol.Factory());  
      arg.transportFactory(new TFramedTransport.Factory()); 
      arg.processorFactory(new TProcessorFactory(passwordProcessor));  
      arg.workerThreads(5);

      TServer server = new THsHaServer(arg);  
      PerfCountersService countersService = new PerfCountersService();
      countersService.setStartTime();
      System.out.println("HsHa BE password server started on port "+ port);  
      server.serve();  
    } catch (TTransportException e) {  
      e.printStackTrace();  
    } catch (Exception e) {  
      e.printStackTrace();  
    }
  }

  private static void management(int port) {
    try {
      managementHandler = new BEManagementHandler();
      managementProcessor = new A1Management.Processor(managementHandler);

      TNonblockingServerSocket socket = new TNonblockingServerSocket(port);  
      THsHaServer.Args arg = new THsHaServer.Args(socket); 
      arg.protocolFactory(new TBinaryProtocol.Factory());  
      arg.transportFactory(new TFramedTransport.Factory()); 
      arg.processorFactory(new TProcessorFactory(managementProcessor));  
      arg.workerThreads(1);

      TServer server = new THsHaServer(arg);  
      PerfCountersService countersService = new PerfCountersService();
      countersService.setStartTime();
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      scheduler.scheduleAtFixedRate(new HeartbeatBroadcast(), 2, 1, TimeUnit.SECONDS);
      System.out.println("HsHa BE management server started on port "+ port);  

      server.serve();  

    } catch (TTransportException e) {  
      e.printStackTrace();  
    } catch (Exception e) {  
      e.printStackTrace();  
    }
}

  private static class HeartbeatBroadcast implements Runnable {
    public void run(){
//      System.out.println("Broadcasting hearbeat");
      try {
        managementHandler = new BEManagementHandler();
        String hostname = InetAddress.getLocalHost().getHostName();
        int cores = Runtime.getRuntime().availableProcessors();
        Heartbeat hb = new Heartbeat(hostname, cores, pPort, mPort);
        managementHandler.beat(hb);
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (TException e) {  
        e.printStackTrace();  
      }
    }
  }
}
