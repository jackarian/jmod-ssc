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
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.net.InetAddress;

/**
 * Class that implements a simple commandline tool for writing to a digital
 * output.
 * <p>
 * Note that if you write to a remote I/O with a Modbus protocol stack, it will
 * most likely expect that the communication is <i>kept alive</i> after the
 * first write message.<br>
 * This can be achieved either by sending any kind of message, or by repeating
 * the write message within a given period of time.<br>
 * If the time period is exceeded, then the device might react by turning pos
 * all signals of the I/O modules. After this timeout, the device might require
 * a reset message.
 * 
 * @author Dieter Wimberger
 * @version @version@ (@date@)
 */
public class DOTest {

	public static void main(String[] args) {

		InetAddress addr = null;
		TCPMasterConnection con = null;
		ModbusRequest req = null;
		ModbusTransaction trans = null;
		int ref = 0;
		boolean count = false;
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
					count = "true".equals(args[2]);
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
			con = new TCPMasterConnection(addr);
			con.setPort(port);
			con.connect();

			if (Modbus.debug)
				System.out.println("Connected to " + addr.toString() + ":"
						+ con.getPort());

			// 3. Prepare the request
			req = new WriteCoilRequest(ref, count);
			req.setUnitID(0);
			if (Modbus.debug)
				System.out.println("Request: " + req.getHexMessage());

			// 4. Prepare the transaction
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);

			// 5. Execute the transaction repeat times
			int k = 0;
			do {
				trans.execute();

				if (Modbus.debug)
					System.out.println("Response: "
							+ trans.getResponse().getHexMessage());
				k++;
			} while (k < repeat);

			// 6. Close the connection
			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// main

	private static void printUsage() {
		System.out
				.println("java net.wimpi.modbus.cmd.DOTest <address{:<port>} [String]> <register [int16]> <state [boolean]> {<repeat [int]>}");
	}// printUsage

}// class DOTest
