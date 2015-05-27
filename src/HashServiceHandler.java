/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

// Generated code
import ece454.*;

import org.mindrot.jbcrypt.*;
import java.util.HashMap;

public class HashServiceHandler implements HashService.Iface {

  private HashMap<Integer,String> map;

  public HashServiceHandler() {
    map = new HashMap<Integer, String>();
  }

  public String hashPassword(String password, short logRounds) throws ServiceUnavailableException, TException {
    return BCrypt.hashpw(password, BCrypt.gensalt((int)logRounds));
  }

  public boolean checkPassword(String password, String hash) throws TException {
    return true;
  }

  public PerfCounters getPerfCounters() throws TException {
    return new PerfCounters();
    }
}

