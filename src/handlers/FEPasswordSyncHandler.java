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
     FEPasswordHandler passHandler = new FEPasswordHandler();
      BEPasswordSyncClient BEPassClient = new BEPasswordSyncClient("localhost", 10100);
      return BEPassClient.hashPassword(password, logRounds);
    }

    public boolean checkPassword(String password, String hash) throws org.apache.thrift.TException {
      return true;
    }
}
