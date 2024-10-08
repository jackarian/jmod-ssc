/***
 * Copyright 2002-2010 jamod development team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***/

package net.wimpi.modbus.cmd;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusUDPTransaction;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.net.UDPMasterConnection;

import java.net.InetAddress;

/**
 * Class that implements a simple commandline tool for reading a digital input.
 * 
 * @author Dieter Wimberger
 * @version @version@ (@date@)
 */
public class UDPDITest {

	public static void main(String[] args) {

		UDPMasterConnection conn = null;
		ModbusUDPTransaction trans = null;
		ReadInputDiscretesRequest req = null;
		ReadInputDiscretesResponse res = null;

		InetAddress addr = null;
		int ref = 0;
		int count = 0;
		int repeat = 1;
		int port = Modbus.DEFAULT_PORT;

		try {

			// 1. Setup the parameters
			if (args.length < 3) {
				printUsage();
				System.exit(1);
			} else {
				try {
					String astr = args[0];
					int idx = astr.indexOf(':');
					if (idx > 0) {
						port = Integer.parseInt(astr.substring(idx + 1));
						astr = astr.substring(0, idx);
					}
					addr = InetAddress.getByName(astr);
					ref = Integer.parseInt(args[1]);
					count = Integer.parseInt(args[2]);
					if (args.length == 4) {
						repeat = Integer.parseInt(args[3]);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					printUsage();
					System.exit(1);
				}
			}

			// 2. Open the connection
			conn = new UDPMasterConnection(addr);
			conn.setPort(port);
			conn.connect();

			// 3. Prepare the request
			req = new ReadInputDiscretesRequest(ref, count);
			req.setUnitID(0);
			if (Modbus.debug)
				System.out.println("Request: " + req.getHexMessage());

			// 4. Prepare the transaction
			trans = new ModbusUDPTransaction(conn);
			trans.setRequest(req);

			// 5. Execute the transaction repeat times
			int k = 0;
			do {
				trans.execute();

				res = (ReadInputDiscretesResponse) trans.getResponse();
				if (Modbus.debug)
					System.out.println("Response: " + res.getHexMessage());
				System.out.println("Digital Inputs Status="
						+ res.getDiscretes().toString());
				k++;
			} while (k < repeat);

			// 6. Close the connection
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// main

	private static void printUsage() {
		System.out
				.println("java net.wimpi.modbus.cmd.UDPDITest <address{:<port>} [String]> <register [int16]> <bitcount [int16]> {<repeat [int]>}");
	}// printUsage

}// class DITest
