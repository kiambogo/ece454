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

public class TestClient2 {

  static String hostname;
  static int pPort;
  static int mPort;
  static volatile boolean finish = false;
  public static void main(String [] args) {
    int i = 0, j;
    String arg;
    char flag;
    boolean vflag = false;
    String outputfile = "";

    while (i < args.length && args[i].startsWith("-")) {
      arg = args[i++];

      if (arg.equals("-pport")) {
        if (i < args.length)
          pPort = Integer.parseInt(args[i++]);
        else
          System.err.println("-pport requires a defined port");
      } else if (arg.equals("-mport")) {
        if (i < args.length)
          mPort = Integer.parseInt(args[i++]);
        else
          System.err.println("-mport requires a defined port");
      }
      try {
        TTransport managementTransport;
        TTransport passwordTransport;
        passwordTransport = new TFramedTransport(new TSocket("localhost", 10500)); 
        managementTransport = new TFramedTransport(new TSocket("localhost", 10501));
        managementTransport.open();
        passwordTransport.open();

        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        TProtocol manageProtocol = new  TBinaryProtocol(managementTransport);
        TProtocol passwordProtocol = new  TBinaryProtocol(passwordTransport);

        A1Password.Client passwordClient = new A1Password.Client(passwordProtocol);
        A1Management.Client managementClient = new A1Management.Client(manageProtocol);

        System.out.println(managementClient.getPerfCounters());
        System.out.println(managementClient.getGroupMembers());
 //       System.out.println(passwordClient.hashPassword("somesamplepassword", (short)12));
//        System.out.println(passwordClient.checkPassword("somesamplepassword", "$2a$12$1I2Fld0pGlqblbj7z7zA0ufwULhWkOD9bEJ1rz3jFJYDNM1lXYXim"));
  //      System.out.println(managementClient.getPerfCounters());

        managementTransport.close();
        passwordTransport.close();

      } catch (TException x) {
        x.printStackTrace();
      }
    }
  }
}
