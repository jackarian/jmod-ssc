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

//////////////////////////////////////////////////////////////////////////
//
//  File:  SerialFacadeTest.java
//
//  Description: Unit test driver to exerecise the methods for
//  ModbusSerialMaster class.
//
//  Programmer:  JDC (CCC), Wed Feb  4 11:54:23 2004
//
//  Change History: 
//
//  $Log: SerialFacadeTest.java,v $
//  Revision 1.2  2004/10/21 16:44:36  wimpi
//  Please see status file for changes.
//
//  Revision 1.1  2004/09/30 01:45:38  jdcharlton
//  Test driver for ModbusSerialMaster facade
//
//
//
//////////////////////////////////////////////////////////////////////////

package net.wimpi.modbus.cmd;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.io.ORWE515.OrnoWE515Utils;
import net.wimpi.modbus.io.sdm120M.SDMUtils;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleInputRegister;
import net.wimpi.modbus.util.BitVector;
import net.wimpi.modbus.util.SerialParameters;

public class SerialFacadeTest {

	
	
	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		System.out.println("System :"+osName);
		int inChar = -1;
		int result = 0;
		boolean finished = false;
		int slaveId = 5;
		String portname = null;
		ModbusSerialMaster msm = null;

		try {
			// 1. Setup the parameters
			if (args.length < 2) {
				printUsage();
				System.exit(1);
			} else {
				try {
					portname = args[0];
					slaveId = Integer.parseInt(args[1]);
				} catch (Exception ex) {
					ex.printStackTrace();
					printUsage();
					System.exit(1);
				}
			}

			System.out.println(" sending test messages to slave: " + slaveId);
			System.out.println("net.wimpi.modbus.debug set to: " + System.getProperty("net.wimpi.modbus.debug"));



			// 2. Setup serial parameters
			SerialParameters params = new SerialParameters();
			params.setPortName(portname);
			params.setBaudRate(9600);
			params.setDatabits(8);
			params.setParity("None");
			params.setStopbits(1);
			params.setEncoding("rtu");			
			params.setEcho(false);
			
			if (Modbus.debug)
				System.out.println("Encoding [" + params.getEncoding() + "]");

			// 3. Create the master facade
			msm = new ModbusSerialMaster(params);
			msm.connect();

			System.out.println("Tentativo lettura slave: " + slaveId);
				try {
					   OrnoWE515Utils.printVoltage(msm, slaveId);
					   OrnoWE515Utils.printCurrent(msm, slaveId);

				} catch (Exception e) {
					System.out.println("Fallimento lettura slave: " + slaveId);
					e.printStackTrace();
				}
				Thread.sleep(2000);



			
			

		} catch (Exception e) {
			System.err.println("SerialFacadeTest driver: " + e);
			e.printStackTrace();
		}
        assert msm != null;
        msm.disconnect();
	}

	
	
	private static void printUsage() {
		System.out.println("java net.wimpi.modbus.cmd.SerialAITest <portname [String]>  <Unit Address [int8]>");
	}// printUsage

	
	
	/**
	 * 
	 * @param value
	 * @param msm
	 * @param registro
	 * @param slaveId
	 * @throws ModbusException
	 */
	public static void impostatUsciteModuloYotta(BitVector value, ModbusSerialMaster msm, int registro, int slaveId)
			throws ModbusException {
		msm.writeMultipleCoils(slaveId, registro, value);
	}
}
