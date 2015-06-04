import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TThreadPoolServer;
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

public class FEServer {

  public static FEPasswordHandler passwordHandler;
  public static A1Password.AsyncProcessor passwordProcessor;
  public static FEManagementHandler managementHandler; 
    public static A1Management.Processor managementProcessor;

  public static void main(String [] args) {
    final int port = 9090; 

    try {
      passwordHandler = new FEPasswordHandler();
      passwordProcessor = new A1Password.AsyncProcessor(passwordHandler);

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

  private static void password(A1Password.AsyncProcessor processor, int port) {
    try {
      TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9091);
      TServer server = new TNonblockingServer(
              new TNonblockingServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the FE (nonblocking) server...");
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

          System.out.println("Starting the FE (nonblocking) server...");
          PerfCountersService countersService = new PerfCountersService();
          countersService.setStartTime();
          server.serve();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
