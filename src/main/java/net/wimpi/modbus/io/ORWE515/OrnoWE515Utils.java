package net.wimpi.modbus.io.ORWE515;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.io.sdm120M.SDMAddressMapping;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.util.ModbusUtil;

public class OrnoWE515Utils {

	private static float factor=0.01f;
	public  static void printVoltage(ModbusSerialMaster msm,int slaveId) throws ModbusException {
		InputRegister[] inputs = null;
		inputs = msm.readMultipleRegisters(slaveId, OrnoWE515AddressMapping.Voltage.getAddress(), 1);
		
		if (inputs != null) {
			
			float test = ModbusUtil.registerToShort(buildBufferToReadForFloat(1, inputs));
			System.out.println("Valore Volt: " + test*factor);
		}
	}
	
	public static byte[] buildBufferToReadForFloat(int dim,InputRegister[] inputs) {
		byte[] buffer  = new byte[2];
		byte[] low  = inputs[0].toBytes();
		
		
		buffer[0] = low[0];
		buffer[1] = low[1];
		
		return buffer;
	}
}
