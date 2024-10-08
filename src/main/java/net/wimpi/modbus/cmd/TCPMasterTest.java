/***
 * Copyright 2002-2013 jamod development team
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

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.util.BitVector;

import java.net.InetAddress;


/**
 * Sample of a Modbus/TCP master. Intended to connect
 * to the TCPSlaveTest.  Just runs for a bit and makes
 * a few requests, then exits.
 * 
 * @author Charles Hache
 * @version @version@ (@date@)
 */
public class TCPMasterTest {

	private static int requestNumber = 1;
	
	
	public static void main(String[] args) {
		int port = 4196;
		int unitId = 1; 
		try {
			
			InetAddress addy = InetAddress.getByName("192.168.178.20");
			TCPMasterConnection connection = new TCPMasterConnection(addy);
			connection.setTimeout(10000);
			connection.setPort(port);
			System.out.println("Trying to connect to "+addy.getCanonicalHostName()+" on port "+port);
			connection.connect();
			
			ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
			
			ModbusRequest request;
			requestNumber = 1;
			
			//if((request = getNextRequest()) != null) {
			    request = writeSingleCoil(0x00, true);
				request.setUnitID(unitId);
				transaction.setRequest(request);
				transaction.execute();
				ModbusResponse response = transaction.getResponse();				
				gotResponse(response);
				request = writeSingleCoil(0x01, false);
				request.setUnitID(unitId);
				transaction.setRequest(request);
				transaction.execute();
				 response = transaction.getResponse();				
				gotResponse(response);
				request = writeSingleCoil(0x02, false);
				request.setUnitID(unitId);
				transaction.setRequest(request);
				transaction.execute();
				response = transaction.getResponse();				
				gotResponse(response);
				request = readCoils(0x00, 8);
				request.setUnitID(unitId);
				transaction.setRequest(request);
				transaction.execute();
				response = transaction.getResponse();				
				gotResponse(response);
				
			//}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void gotResponse(ModbusResponse response) {
		System.out.println("Got response: "+ModbusResponse.createModbusResponse(response.getFunctionCode()));
	}

	private static ModbusRequest getNextRequest() {
		//Note: simple process image uses 0-based register addresses
		switch (requestNumber) {
		case 0:
			return new WriteCoilRequest(0X00,false);
		case 1:
			return new ReadCoilsRequest(0x00, 1);
		case 2:
			return new ReadInputDiscretesRequest(0,4);
		case 3:
			return new ReadInputRegistersRequest(0,1);
		case 4:
			return new ReadMultipleRegistersRequest(0,1);
		case 5:
			Register r = ModbusCoupler.getReference().getProcessImageFactory().createRegister();
			r.setValue(420);
			return new WriteSingleRegisterRequest(0,r);
		case 6:
			return new ReadMultipleRegistersRequest(0,1);
		default:
			return null;
		}
	}
	
	public static ModbusRequest writeSingleCoil(int address,boolean value) {
		return new WriteCoilRequest(address,value);
	}
	public static ModbusRequest readCoils(int address,int numberOfBytes) {
		return new ReadCoilsRequest(address, numberOfBytes);
	}
	
	public static ModbusRequest writeMultipleCoils(int address,byte[] value) {
		
		WriteMultipleCoilsRequest req = new WriteMultipleCoilsRequest();	
		BitVector vb  = new BitVector(value.length);
		vb.setBytes(value);
		req.setReference(address);
		req.setCoils(vb);
		
		return req;
		
	}
}
