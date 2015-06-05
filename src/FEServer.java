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
      passwordHandler = new FEPasswordSyncHandler();
      passwordProcessor = new A1Password.Processor(passwordHandler);

      managementHandler = new FEManagementHandler();
      managementProcessor = new A1Management.Processor(managementHandler);

      Runnable a1Password = new Runnable() {
        public void run() {
          password(passwordProcessor, pPort);
        }
      };      
      Runnable a1Management = new Runnable() {
        public void run() {
          management(managementProcessor, mPort);
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

      System.out.println("Nonblocking FE password server started at "+ hostname +":"+ port+". Cores: "+ nCores);  
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

      System.out.println("Nonblocking FE management server started at "+ hostname +":"+ port+". Cores: "+ nCores);  
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
