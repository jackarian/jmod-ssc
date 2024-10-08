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

package net.wimpi.modbus.net;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.io.ModbusTransport;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.util.SerialParameters;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that implements a ModbusTCPListener.<br>
 * If listening, it accepts incoming requests passing them on to be handled.
 * 
 * @author Dieter Wimberger
 * @version @version@ (@date@)
 */
public class ModbusSerialListener implements Runnable {

	// Members
	private final AtomicBoolean m_Listening;
	private SerialConnection m_SerialCon;
	private Thread m_Listener;

	/**
	 * Constructs a new <tt>ModbusSerialListener</tt> instance.
	 * 
	 * @param params
	 *            a <tt>SerialParameters</tt> instance.
	 */
	public ModbusSerialListener(SerialParameters params) {
		m_Listening = new AtomicBoolean(false);
		m_SerialCon = new SerialConnection(params);
	}// constructor

	/**
	 * Starts this <tt>ModbusTCPListener</tt>.
	 */
	public void start() {
		m_Listener = new Thread(this);
		m_Listening.set(true);
		m_Listener.start();
	}// start

	/**
	 * Stops this <tt>ModbusTCPListener</tt>.
	 */
	public void stop() {
		m_Listening.set(false);
		m_Listener.interrupt();
	}// stop

	/**
	 * Runs this <tt>ModbusSerialListener</tt>. Listen to incoming messages.
	 */
	@Override
	public void run() {
		try {
			m_SerialCon.open();
			// System.out.println("Opened Serial connection.");
			ModbusTransport transport = m_SerialCon.getModbusTransport();
			while (m_Listening.get()) {
				try {
					// 1. read the request
					ModbusRequest request = transport.readRequest();
					ModbusResponse response = null;

					// test if Process image exists
					if (ModbusCoupler.getReference().getProcessImage() == null) {
						response = request
								.createExceptionResponse(Modbus.ILLEGAL_FUNCTION_EXCEPTION);
					} else {
						response = request.createResponse();
					}

					if (Modbus.debug)
						System.out
								.println("Request:" + request.getHexMessage());
					if (Modbus.debug)
						System.out.println("Response:"
								+ response.getHexMessage());

					transport.writeMessage(response);

				} catch (ModbusIOException ex) {
					ex.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			// FIXME: this is a major failure, how do we handle this
			e.printStackTrace();
		}
	}// listen

	/**
	 * Tests if this <tt>ModbusTCPListener</tt> is listening and accepting
	 * incoming connections.
	 * 
	 * @return true if listening (and accepting incoming connections), false
	 *         otherwise.
	 */
	public boolean isListening() {
		return m_Listening.get();
	}// isListening

}// class ModbusSerialListener

