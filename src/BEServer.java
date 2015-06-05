package ece454s15a1;

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

  public static String hostname;
  public static int pPort;
  public static int mPort;
  public static int nCores;
  public static String seeds;

  public static void main(String [] args) {
    int i = 0, j;
    String arg;
    char flag;
    boolean vflag = false;
    String outputfile = "";

    while (i < args.length && args[i].startsWith("-")) {
      arg = args[i++];

      if (arg.equals("-host")) {
        if (i < args.length)
          hostname = args[i++];
        else
          System.err.println("-host requires a defined hostname");
      } else if (arg.equals("-pport")) {
        if (i < args.length)
          pPort = Integer.parseInt(args[i++]);
        else
          System.err.println("-pport requires a defined port");
      } else if (arg.equals("-mport")) {
        if (i < args.length)
          mPort = Integer.parseInt(args[i++]);
        else
          System.err.println("-mport requires a defined port");
      } else if (arg.equals("-ncores")) {
        if (i < args.length)
          nCores = Integer.parseInt(args[i++]);
        else
          System.err.println("-ncores requires a number of cores");
      } else if (arg.equals("-seeds")) {
        if (i < args.length)
          seeds = args[i++];
        else
          System.err.println("-seeds requires a comma seperated list of seeds");
      }
    }

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
      System.out.println("HsHa BE password server started at "+ hostname +":"+ port+". Cores: "+ nCores);  
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
      parseSeeds();
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      scheduler.scheduleAtFixedRate(new HeartbeatBroadcast(), 100, 100, TimeUnit.MILLISECONDS);
      System.out.println("HsHa BE management server started at "+ hostname +":"+ port+". Cores: "+ nCores);  

      server.serve();  

    } catch (TTransportException e) {  
      e.printStackTrace();  
    } catch (Exception e) {  
      e.printStackTrace();  
    }
}

  private static class HeartbeatBroadcast implements Runnable {
    public void run(){
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

  private static void parseSeeds() {
    if (seeds != null) {
      NodeService nodeService = new NodeService();
      String [] nodes = seeds.split(",");
      for (String node : nodes) {
        String [] parts = node.split(":");
        Heartbeat hb = new Heartbeat(parts[0], 0, 0, Integer.parseInt(parts[1]));
        nodeService.seedList.add(hb);
      }
    }
  }
}
