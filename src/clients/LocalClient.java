import ece454.*;
import clients.*;

import java.io.IOException;  
import org.apache.thrift.TException;
import org.apache.thrift.async.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

public class LocalClient {

    static volatile boolean finish = false;
    public static void main(String [] args) {
        try {
              TTransport managementTransport;
              TNonblockingTransport passwordTransport = new TNonblockingSocket("localhost", 9091); 
              //passwordTransport = new TFramedTransport(new TSocket("localhost", 9090));
              managementTransport = new TFramedTransport(new TSocket("localhost", 9090));
              managementTransport.open();

              TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
              TProtocol manageProtocol = new  TBinaryProtocol(managementTransport);

              TAsyncClientManager clientManager = new TAsyncClientManager();

              A1Password.AsyncClient passwordClient = new A1Password.AsyncClient(
                      protocolFactory, clientManager, passwordTransport);
              A1Management.Client managementClient = new A1Management.Client(manageProtocol);


            //TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            //TAsyncClientManager clientManager = new TAsyncClientManager();
            //TNonblockingTransport transport = new TNonblockingSocket("localhost", 10100); 
            //A1Password.AsyncClient passwordClient = new A1Password.AsyncClient(
             //         protocolFactory, clientManager, transport);
  
            System.out.println("Calling hash now");
            passwordClient.hashPassword("somesamplepassword", (short)12, new HashPasswordCallBack());
            System.out.println("Called");


      int i = 0;
      while (!finish) {  
        try{Thread.sleep(1000);}catch(InterruptedException e){System.out.println(e);}
        i++;    
        System.out.println("Sleep " + i + " Seconds.");
      }
      managementTransport.close();

        } catch (TException x) {
            x.printStackTrace();
        } catch (IOException e) {  
          e.printStackTrace();
        }
    }

  static class HashPasswordCallBack 
    implements AsyncMethodCallback<A1Password.AsyncClient.hashPassword_call> {
        public void onComplete(A1Password.AsyncClient.hashPassword_call hashCall) {
            try {
                String hash = hashCall.getResult();
                System.out.println("Hash from server: " + hash);
            } catch (TException e) {
                e.printStackTrace();
            }
            finish = true;
        }
    
        public void onError(Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
            finish = true;
        }
  }    
}
