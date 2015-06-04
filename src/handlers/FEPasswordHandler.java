package handlers;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

import ece454.*;
import clients.*;
import services.*;

import org.mindrot.jbcrypt.*;
import java.util.HashMap;

public class FEPasswordHandler implements A1Password.AsyncIface {
    PerfCountersService countersService = new PerfCountersService();

    public void hashPassword(String password, short logRounds, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      // Increment request counter
      countersService.incrementRequestsReceived();
      // Calculate which BE to connect to
      System.out.println("FE node received request to hash. Contacting BE");
      BEPasswordSyncClient BEPassClient = new BEPasswordSyncClient("localhost", 10100);
      BEPassClient.hashPassword(password, logRounds);
    }

    public void checkPassword(String password, String hash, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      // Increment request counter
      countersService.incrementRequestsReceived();
      // Calculate which BE to connect to
      BEPasswordClient BEPassClient = new BEPasswordClient("localhost", 10100);
      BEPassClient.checkPassword(password, hash);
    }
}

