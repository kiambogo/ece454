package clients;

import ece454.*;

import java.io.IOException;  
import org.apache.thrift.TException; 
import org.apache.thrift.async.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

public class BEPasswordClient{
    private String uri;
    private int port;
    private A1Password.Client client;

    public BEPasswordClient(String uri, int port) { 
        this.uri = uri;
        this.port = port;
        try {
          TTransport transport;
          transport = new TFramedTransport(new TSocket(uri, port));
          TProtocol protocol = new TBinaryProtocol(transport);
          client = new A1Password.Client(protocol);
          transport.open();
        } catch (TException e) {  
          e.printStackTrace();
        } 
    }


  public String hashPassword(String password, short rounds) {
    String hashedPassword = "";
    try {
      hashedPassword = client.hashPassword(password, rounds);
    } catch (TException e) {
      e.printStackTrace(); 
    }
    return hashedPassword;
  }

  public boolean checkPassword(String password, String hash) {
    boolean response = false;
    try {
      response = client.checkPassword(password, hash);
    } catch (TException e) {
      e.printStackTrace(); 
    }
    return response;
  }
}
