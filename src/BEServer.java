import org.apache.thrift.server.*;
import org.apache.thrift.protocol.*; 
import org.apache.thrift.transport.*;
import org.apache.thrift.TProcessorFactory;  
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import ece454.*;
import services.*;
import handlers.*;

import java.util.HashMap;

public class BEServer {

  public static BEPasswordHandler handler;

  public static A1Password.Processor processor;

  public static void main(String [] args) {
    final int port = 10100; 
      try {
        handler = new BEPasswordHandler();
        processor = new A1Password.Processor(handler);

        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);  
        THsHaServer.Args arg = new THsHaServer.Args(socket); 
        arg.protocolFactory(new TBinaryProtocol.Factory());  
        arg.transportFactory(new TFramedTransport.Factory()); 
        arg.processorFactory(new TProcessorFactory(processor));  
        arg.workerThreads(5);

        TServer server = new THsHaServer(arg);  
        PerfCountersService countersService = new PerfCountersService();
        countersService.setStartTime();
        System.out.println("HsHa server started on port "+ port);  
        server.serve();  
      } catch (TTransportException e) {  
        e.printStackTrace();  
      } catch (Exception e) {  
    e.printStackTrace();  
      }
  }
}
