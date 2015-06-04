package handlers;

import ece454.*;
import clients.*;
import services.*;

import org.apache.thrift.TException;
import org.apache.thrift.async.*;

public class FEPasswordSyncHandler implements A1Password.Iface {
    PerfCountersService countersService = new PerfCountersService();
    public volatile String hashed = "";
    static volatile boolean finish = false;

    public String hashPassword(String password, short logRounds) throws ServiceUnavailableException, org.apache.thrift.TException {
      try {
      // Increment request counter
      countersService.incrementRequestsReceived();
      // Calculate which BE to connect to
      //
      BEPasswordSyncClient BEPassClient = new BEPasswordSyncClient("localhost", 10100);
      // Send request to that BE
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
        //
        BEPasswordSyncClient BEPassClient = new BEPasswordSyncClient("localhost", 10100);
        // Send request to that BE
        return BEPassClient.checkPassword(password, hash);
      } finally {
        countersService.incrementRequestsCompleted();
      }
    }
}
