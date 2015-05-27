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

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

// Generated code
import ece454.*;

import java.util.HashMap;

public class BEServer {

  public static HashServiceHandler handler;

  public static HashService.Processor processor;

  public static void main(String [] args) {
    final int port = 9091; 

    try {
      handler = new HashServiceHandler();
      processor = new HashService.Processor(handler);

      Runnable simple = new Runnable() {
        public void run() {
          threadpool(processor, port);
        }
      };      

      new Thread(simple).start();
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static void threadpool(HashService.Processor processor, Integer port) {
    try {
      TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
      TServer server = new TThreadPoolServer(
              new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the BE (threadpool) server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
