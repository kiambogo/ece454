import ece454.*;

import java.io.IOException;  
import org.apache.thrift.TException; 
import org.apache.thrift.async.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

public class BEPasswordClient{
  private static String uri;
  private static int port;

  public BEPasswordClient(String uri, int port) { 
    uri = uri;
    port = port;
  }

  public static void hashPassword(String password, short rounds) {
      try {
          TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
          TAsyncClientManager clientManager = new TAsyncClientManager();
          TNonblockingTransport transport = new TNonblockingSocket(uri, port); 
          A1Password.AsyncClient client = new A1Password.AsyncClient(
            protocolFactory, clientManager, transport);

          client.hashPassword(password, rounds, new HashPasswordCallBack());
      } catch (TException x) {
        x.printStackTrace();
      } catch (IOException e) {  
        e.printStackTrace();
      } 
  }

  public static void checkPassword(String password, String hash) {
      try {
          TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
          TAsyncClientManager clientManager = new TAsyncClientManager();
          TNonblockingTransport transport = new TNonblockingSocket(uri, port); 
          A1Password.AsyncClient client = new A1Password.AsyncClient(
            protocolFactory, clientManager, transport);

          client.checkPassword(password, hash, new CheckPasswordCallBack());
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
        }
    
        public void onError(Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
        }
  }    

  static class CheckPasswordCallBack
    implements AsyncMethodCallback<A1Password.AsyncClient.checkPassword_call> {
        public void onComplete(A1Password.AsyncClient.checkPassword_call checkPasswordCall) {
            try {
                Boolean result = checkPasswordCall.getResult();
                System.out.println("Response from server: " + result);
            } catch (TException e) {
                e.printStackTrace();
            }
        }

        public void onError(Exception e) {
          System.out.println("Error: ");
          e.printStackTrace();
        }
    }
}
