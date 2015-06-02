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
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

// Generated code
import ece454.*;

import org.mindrot.jbcrypt.*;
import java.util.HashMap;

public class BEServiceHandler implements A1Management.Iface {
    public PerfCounters getPerfCounters() throws org.apache.thrift.TException {
        return new PerfCounters(1, 1, 1);
    }

    public Set<Heartbeat> getUpdatedBEList() throws org.apache.thrift.TException {
        return new ConcurrentSkipListSet<Heartbeat>(); 
    }

    public void sendHeartbeat(Heartbeat heartbeat) throws org.apache.thrift.TException {
        
    }

    public void receiveHeartbeat(Heartbeat heartbeat) throws org.apache.thrift.TException {

    }
}

