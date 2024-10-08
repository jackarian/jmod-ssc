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

package net.wimpi.modbus.msg;

import net.wimpi.modbus.Modbus;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Provides an implementation of a <tt>ModbusRequest</tt> which is created for
 * illegal or non implemented function codes.
 * <p>
 * This is just a helper class to keep the implementation patterns the same for
 * all cases.
 * 
 * @author Dieter Wimberger
 * @version @version@ (@date@)
 */
public class IllegalFunctionRequest extends ModbusRequest {

	/**
	 * Constructs a new <tt>IllegalFunctionRequest</tt> instance for a given
	 * function code.
	 * 
	 * @param fc
	 *            the function code as <tt>int</tt>.
	 */
	public IllegalFunctionRequest(int fc) {
		setFunctionCode(fc);
	}// constructor

	@Override
	public ModbusResponse createResponse() {
		return this.createExceptionResponse(Modbus.ILLEGAL_FUNCTION_EXCEPTION);
	}// createResponse

	@Override
	public void writeData(DataOutput dout) throws IOException {
		throw new RuntimeException();
	}// writeData

	@Override
	public void readData(DataInput din) throws IOException {
		// skip all following bytes
		int length = getDataLength();
		for (int i = 0; i < length; i++) {
			din.readByte();
		}
	}// readData

	@Override
	public int getReference() {
		return 0;
	}

}// IllegalFunctionRequest
