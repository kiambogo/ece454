package handlers;

import ece454.*;
import clients.*;
import services.*;

import org.apache.thrift.TException;
import org.apache.thrift.async.*;

public class FEPasswordHandler implements A1Password.Iface {
    PerfCountersService countersService = new PerfCountersService();
    NodeService nodeService = new NodeService();

    public String hashPassword(String password, short logRounds) throws ServiceUnavailableException, org.apache.thrift.TException {
      try {
      // Increment request counter
      countersService.incrementRequestsReceived();
      // Calculate which BE to connect to
      Heartbeat beNode = nodeService.getBE();
      // Send request to that BE
      BEPasswordClient BEPassClient = new BEPasswordClient(beNode.hostname, beNode.servicePort);
      return BEPassClient.hashPassword(password, logRounds);
      } finally {
        countersService.incrementRequestsCompleted();
      }
    }

    public boolean checkPassword(String password, String hash) throws org.apache.thrift.TException {
      try {
        // Increment request counter
        countersService.incrementRequestsReceived();
        // Calculate which BE to connect to
        Heartbeat beNode = nodeService.getBE();
        // Send request to that BE
        BEPasswordClient BEPassClient = new BEPasswordClient(beNode.hostname, beNode.servicePort);
        return BEPassClient.checkPassword(password, hash);
      } finally {
        countersService.incrementRequestsCompleted();
      }
    }
}
