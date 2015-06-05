namespace java ece454 

struct PerfCounters {
  1:i32 numSecondsUp,
  2:i32 numRequestsReceived,
  3:i32 numRequestsCompleted
}

struct Heartbeat {
  1:string hostname,
  2:i32 numberOfCores,
  3:i32 servicePort,
  4:i32 managementPort 
}

struct TimedHeartbeat {
  1:i64 timestamp,
  2:Heartbeat heartbeat
}

struct UpdatedNodeList {
  1:i64 timestamp,
  3:list<TimedHeartbeat> beNodes
}

struct ServerHealthcheck {
  1:i32 portNumber
}

exception ServiceUnavailableException {
  1:string msg
}

service A1Password {
  string hashPassword (1:string password, 2:i16 logRounds) throws (1: ServiceUnavailableException e)
  bool checkPassword (1:string password, 2:string hash)
}

service A1Management {
  PerfCounters getPerfCounters()
  UpdatedNodeList getUpdatedBEList()
  void beat(1:Heartbeat heartbeat)
  list<string> getGroupMembers()
}

