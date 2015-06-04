package handlers;

import org.apache.thrift.TException;

import ece454.*;
import services.*;

import org.mindrot.jbcrypt.*;

public class BEPasswordHandler implements A1Password.Iface {
    PerfCountersService countersService = new PerfCountersService();

    public String hashPassword(String password, short logRounds) throws ServiceUnavailableException, org.apache.thrift.TException {
        System.out.println("BE received request to hash. Hashing.");
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
        System.out.println("hashing...");
        while (hashed == null) { }
      return hashed;
    }

    public boolean checkPassword(String password, String hash) throws org.apache.thrift.TException {
        return BCrypt.checkpw(password, hash) ? true : false;
    }
}

