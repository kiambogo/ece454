package handlers;

import ece454.*;
import clients.*;
import services.*;

import org.apache.thrift.TException;
import org.apache.thrift.async.*;

import java.util.Date;
import java.util.Timer;

public class FEPasswordHandler implements A1Password.Iface {
    PerfCountersService countersService = new PerfCountersService();
    NodeService nodeService = new NodeService();

    public String hashPassword(String password, short logRounds) throws ServiceUnavailableException, org.apache.thrift.TException {
        // Increment request counter
        countersService.incrementRequestsReceived();
        String output;
        // timer
        long start_time = System.nanoTime();
        long end_time = start_time;

        while ((end_time - start_time) < (60L * 1000000000L)) {
            try {
                // Calculate which BE to connect to
                Heartbeat beNode = nodeService.getBE();
                // Send request to that BE
                BEPasswordClient BEPassClient = new BEPasswordClient(beNode.hostname, beNode.servicePort);

                output = BEPassClient.hashPassword(password, logRounds);
                countersService.incrementRequestsCompleted();
                return output;

            } catch (Exception e) {
                try {
                    // Avoid polling too frequently if BEs are down
                    Thread.sleep(200);
                } catch (InterruptedException e2) {
                }
            } 
            end_time = System.nanoTime();
        }
        throw new ServiceUnavailableException("Unreachable for >60 seconds");
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
