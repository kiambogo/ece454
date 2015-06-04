package handlers;

import ece454.A1Password;
import ece454.ServiceUnavailableException;
import org.mindrot.jbcrypt.BCrypt;
import services.PerfCountersService;

public class BEPasswordHandler implements A1Password.Iface {
    PerfCountersService countersService = new PerfCountersService();

    public String hashPassword(String password, short logRounds) throws ServiceUnavailableException, org.apache.thrift.TException {
        countersService.incrementRequestsReceived();

        System.out.println("BE received request to hash. Hashing.");
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
        System.out.println("hashing...");
        while (hashed == null) { }

        countersService.incrementRequestsCompleted();
      return hashed;
    }

    public boolean checkPassword(String password, String hash) throws org.apache.thrift.TException {
        countersService.incrementRequestsReceived();

        boolean check = BCrypt.checkpw(password, hash);

        countersService.incrementRequestsCompleted();
        return check;

    }
}

