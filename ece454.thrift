namespace java ece454 

struct PerfCounters {
  1:i32 numSecondsUp,
  2:i32 numRequestsReceived,
  3:i32 numRequestsCompleted
}

exception ServiceUnavailableException {
  1:string msg
}

service HashService{
  string hashPassword (1:string password, 2:i16 logRounds) throws (1: ServiceUnavailableException e)
  bool checkPassword (1:string password, 2:string hash)
  PerfCounters getPerfCounters ()
}

