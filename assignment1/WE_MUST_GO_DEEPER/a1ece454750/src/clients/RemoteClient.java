import ece454.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class RemoteClient {
  public static void main(String [] args) {

    // 1st args is "simple", 2nd args is server address
    if (args.length != 2 || !args[0].contains("simple")) {
      System.out.println("Please enter 'simple' ");
      System.exit(0);
    }

    try {
      TTransport transport;
      transport = new TFramedTransport(new TSocket(args[1], 10300));
      transport.open();

      TProtocol protocol = new  TBinaryProtocol(transport);
      A1Password.Client client = new A1Password.Client(protocol);

      perform(client);

      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }

  private static void perform(A1Password.Client client) throws TException {
    String response = client.hashPassword("sampletext", (short)9);
    System.out.println(""+response);
  }
}
