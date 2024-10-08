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
 * 
 * Original implementation by jamod development team.
 * This file modified by Charles Hache <chache@brood.ca>
 ***/

package net.wimpi.modbus.cmd;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusUDPTransaction;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.net.UDPMasterConnection;

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
public class UDPDOTest {

	public static void main(String[] args) {

		UDPMasterConnection conn = null;
		ModbusUDPTransaction trans = null;
		WriteCoilRequest req = null;

		InetAddress addy = null;

		int ref = 1;
		boolean set = false;
		int repeat = 1;
		int port = Modbus.DEFAULT_PORT;

		try {

			addy = InetAddress.getByName("modbus");

			// 2. Open the connection
			conn = new UDPMasterConnection(addy);
			conn.setPort(port);
			conn.connect();

			// 3. Prepare a request
			req = new WriteCoilRequest(0x00, true);
			req.setUnitID(1);
			if (Modbus.debug)
				System.out.println("Request: " + req.getHexMessage());

			// 4. Prepare the transaction
			trans = new ModbusUDPTransaction(conn);
			trans.setRequest(req);

			// 5. Execute the transaction repeat times
			int k = 0;
			do {
				trans.execute();

				if (Modbus.debug) {
					if(trans.getResponse()!=null)
					System.out.println("Response: "
							+ trans.getResponse().getHexMessage());
				}
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
				.println("java net.wimpi.modbus.cmd.UDPDOTest <address{:<port>} [String]> <register [int16]> <state [boolean]> {<repeat [int]>}");
	}// printUsage

}// class DOTest
