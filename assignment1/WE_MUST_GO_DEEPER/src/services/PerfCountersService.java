package services;

import ece454.*;
import java.util.Date;

public class PerfCountersService {
    private static Date startTime;
    private static int requestsReceived;
    private static int requestsCompleted;

    public int getSecondsUp() {
      Date now = new Date();
      int hours = now.getHours() - startTime.getHours();
      int minutes = now.getMinutes() - startTime.getMinutes(); 
      int seconds = now.getSeconds() - startTime.getSeconds(); 
      return hours*3600 + minutes*60 + seconds;
    }

    public void setStartTime() {
      this.startTime = new Date(); 
    }

    public int getRequestsReceived() {
      return requestsReceived;
    }

    public int getRequestsCompleted() {
      return requestsCompleted;
    }

    public int incrementRequestsReceived() {
      return requestsReceived += 1;
    }

    public int incrementRequestsCompleted() {
      return requestsCompleted += 1;
    }
}
