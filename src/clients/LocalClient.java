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
              TTransport passwordTransport;
              managementTransport = new TFramedTransport(new TSocket("localhost", 9090));
              passwordTransport = new TFramedTransport(new TSocket("localhost", 9091)); 
              managementTransport.open();
              passwordTransport.open();

              TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
              TProtocol manageProtocol = new  TBinaryProtocol(managementTransport);
              TProtocol passwordProtocol = new  TBinaryProtocol(passwordTransport);

              A1Password.Client passwordClient = new A1Password.Client(passwordProtocol);
              A1Management.Client managementClient = new A1Management.Client(manageProtocol);

              System.out.println(managementClient.getGroupMembers());
              System.out.println(passwordClient.hashPassword("somesamplepassword", (short)12));
              
              managementTransport.close();
              passwordTransport.close();

        } catch (TException x) {
          x.printStackTrace();
        }
    }
}
