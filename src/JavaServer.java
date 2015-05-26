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
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TNonblockingServer;
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

public class JavaServer {

  public static HashServiceHandler handler;

  public static HashService.Processor processor;

  public static void main(String [] args) {
    final String port; 

    if (args.length != 0) {
      port = args[0];
    } else {
      port = "9090";
    }

    try {
      handler = new HashServiceHandler();
      processor = new HashService.Processor(handler);

      Runnable simple = new Runnable() {
        public void run() {
          nonblocking(processor, Integer.parseInt(port));
        }
      };      

      new Thread(simple).start();
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static void nonblocking(HashService.Processor processor, Integer port) {
    try {
      TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
      TServer server = new TNonblockingServer(
              new TNonblockingServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the nonblocking server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
