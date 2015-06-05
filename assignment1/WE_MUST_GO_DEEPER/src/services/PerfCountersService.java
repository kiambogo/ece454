package services;

import ece454.*;
import java.time.LocalTime;

public class PerfCountersService {
    private static LocalTime startTime;
    private static int requestsReceived;
    private static int requestsCompleted;

    public int getSecondsUp() {
      LocalTime now = LocalTime.now();
      int hours = (now.minusHours(startTime.getHour())).getHour(); 
      int minutes = (now.minusMinutes(startTime.getMinute())).getMinute(); 
      int seconds = (now.minusSeconds(startTime.getSecond())).getSecond(); 
      return hours*3600 + minutes*60 + seconds;
    }

    public void setStartTime() {
      this.startTime = LocalTime.now(); 
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
