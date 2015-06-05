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
import clients.*;

import java.util.HashMap;
import java.util.concurrent.*;
import java.net.UnknownHostException;
import java.time.LocalTime;

public class FEServer {

  public static FEPasswordHandler passwordHandler;
  public static A1Password.Processor passwordProcessor;
  public static FEManagementHandler managementHandler; 
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
      passwordHandler = new FEPasswordHandler();
      passwordProcessor = new A1Password.Processor(passwordHandler);

      TNonblockingServerSocket socket = new TNonblockingServerSocket(port);  
      THsHaServer.Args arg = new THsHaServer.Args(socket); 
      arg.protocolFactory(new TBinaryProtocol.Factory());  
      arg.transportFactory(new TFramedTransport.Factory()); 
      arg.processorFactory(new TProcessorFactory(passwordProcessor));  
      arg.workerThreads(4);

      TServer server = new THsHaServer(arg);  
      System.out.println("Nonblocking FE password server started at "+ hostname +":"+ port+". Cores: "+ nCores);  
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void management(int port) {
      try {
        managementHandler = new FEManagementHandler();
        managementProcessor = new A1Management.Processor(managementHandler);

        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);  
        THsHaServer.Args arg = new THsHaServer.Args(socket); 
        arg.protocolFactory(new TBinaryProtocol.Factory());  
        arg.transportFactory(new TFramedTransport.Factory()); 
        arg.processorFactory(new TProcessorFactory(managementProcessor));  
        arg.workerThreads(1);

        TServer server = new THsHaServer(arg);  

      System.out.println("Nonblocking FE management server started at "+ hostname +":"+ port+". Cores: "+ nCores);  
          PerfCountersService countersService = new PerfCountersService();
          countersService.setStartTime();
          parseSeeds();
          // Only run this if NOT seed node
          ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
          if (!isSeed()) {
            scheduler.scheduleAtFixedRate(new RefreshBEList(), 500, 500, TimeUnit.MILLISECONDS);
            //scheduler.scheduleAtFixedRate(new RefreshBEList(), 2, 2, TimeUnit.SECONDS);
          } else {
            scheduler.scheduleAtFixedRate(new RemoveOldBENodes(), 0, 1, TimeUnit.SECONDS);
          }
          server.serve();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  private static class RefreshBEList implements Runnable {
      NodeService nodeService = new NodeService();
    public void run(){
        for (Heartbeat seedNode : nodeService.seedList) {
            FEManagementClient client = new FEManagementClient(seedNode.hostname, seedNode.managementPort);
            UpdatedNodeList list = client.getUpdatedBEList();
            LocalTime updateTime = LocalTime.parse(list.timestamp);
            if (updateTime.isAfter(nodeService.lastUpdated)) {
              nodeService.lastUpdated = updateTime;
              nodeService.BEList = list.beNodes;
              //System.out.println("Received List: " +list.beNodes);
              //System.out.println("My new BE List: " +nodeService.BEList);
              break;
            }
        }
    }
  }

  private static class RemoveOldBENodes implements Runnable {
      NodeService nodeService = new NodeService();
    public void run(){
      nodeService.removeOldNodes();
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

  private static boolean isSeed() {
    NodeService nodeService = new NodeService();
    boolean isSeed = false;
    for (Heartbeat node: nodeService.seedList) {
      if (node.hostname.equals(hostname) && node.managementPort == mPort) {
        isSeed = true;
        System.out.println("This is a seed node");
      }
    }
    return isSeed;
  }
}
