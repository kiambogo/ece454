namespace java ece454 

struct PerfCounters {
  1:i32 numSecondsUp,
  2:i32 numRequestsReceived,
  3:i32 numRequestsCompleted
}

struct ServerUpBroadcast {
  1:i32 portNumber,
  2:i32 numberOfCores
}

struct ServerHealthcheck {
  1:i32 portNumber
}

exception ServiceUnavailableException {
  1:string msg
}

service HashService {
  string hashPassword (1:string password, 2:i16 logRounds) throws (1: ServiceUnavailableException e)
  bool checkPassword (1:string password, 2:string hash)
  PerfCounters getPerfCounters ()
}

service BroadcastService {
  void sendHealthcheck(1:i16 numberOfCores)
  void sendServerUp(1:i16 portNumber, 2:i16 numberOfCores)
  void receiveHealthCheck(1:ServerHealthcheck healthCheck)
  void receiveServerUp(1:ServerUpBroadcast broadcast)
}

