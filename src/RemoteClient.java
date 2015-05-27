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

// Generated code
import ece454.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class RemoteClient {
  public static void main(String [] args) {

    // 1st args is "simple", 2nd args is server address
    if (args.length != 2 || !args[0].contains("simple")) {
      System.out.println("Please enter 'simple' ");
      System.exit(0);
    }

    try {
      TTransport transport;
      transport = new TFramedTransport(new TSocket(args[1], 10300));
      transport.open();

      TProtocol protocol = new  TBinaryProtocol(transport);
      HashService.Client client = new HashService.Client(protocol);

      perform(client);

      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }

  private static void perform(HashService.Client client) throws TException {
    String response = client.hashPassword("", (short)9);
    System.out.println(""+response);
    System.out.println("shits working yo");
  }
}
