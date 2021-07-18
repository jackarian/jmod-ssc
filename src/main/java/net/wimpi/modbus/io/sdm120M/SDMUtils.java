package net.wimpi.modbus.io.sdm120M;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.facade.ModbusSerialMaster;

import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleInputRegister;
import net.wimpi.modbus.util.ModbusUtil;

public class SDMUtils {

	/**
	 * Impostazione baud rate.
	 * 
	 * @param lowByte
	 * @param highByte
	 * @param slaveId
	 * @param msm
	 * @throws ModbusException
	 */
	public static void impostaBaudRateSDM(int lowByte, int highByte, int slaveId, ModbusSerialMaster msm)
			throws ModbusException {
		Register[] regsValues = new Register[2];
		regsValues[0] = new SimpleInputRegister(lowByte);
		regsValues[1] = new SimpleInputRegister(highByte);
		msm.writeMultipleRegisters(slaveId, SDMAddressMapping.BaudRate.getAddress(), regsValues);

	}
	/**
	 * Lettura valore baude rate.
	 * 
	 * @param slaveId
	 * @param msm
	 * @return
	 * @throws ModbusException
	 */
	public static Float leggiBaudRateSDM(int slaveId, ModbusSerialMaster msm)
			throws ModbusException {
		Float test=null; 
		Register[] regs = msm.readMultipleRegisters(slaveId, SDMAddressMapping.BaudRate.getAddress(), 2);
		if (regs != null) {
			test  = ModbusUtil.registersToFloat(buildBufferToRead(2, regs));
			System.out.println("Valore leggi baud rate: " + test);
		}
		return test;
	}
	/**
	 * Lettura valore id modbus
	 * @param slaveId
	 * @param msm
	 * @return
	 * @throws ModbusException
	 */
	public static Float leggMeterId(int slaveId, ModbusSerialMaster msm)
			throws ModbusException {
		Float test=null; 
		Register[] regs = msm.readMultipleRegisters(slaveId, SDMAddressMapping.MeterID.getAddress(), 2);
		
		
	  if (regs != null) {
			
			test  = ModbusUtil.registersToFloat(buildBufferToRead(2, regs));
			System.out.println("Valore meter id: " + test);
		}
		return test;
	}
	
	/**
	 * Stampa il valore in volt
	 * @param msm
	 * @param slaveId
	 * @throws ModbusException
	 */
	public  static void printVoltage(ModbusSerialMaster msm,int slaveId) throws ModbusException {
		InputRegister[] inputs = null;
		inputs = msm.readInputRegisters(slaveId, SDMAddressMapping.Voltage.getAddress(), 2);
		
		if (inputs != null) {
			
			float test = ModbusUtil.registersToFloat(buildBufferToRead(2, inputs));
			System.out.println("Valore Volt: " + test);
		}
	}
	public static byte[] buildBufferToRead(int dim,InputRegister[] inputs) {
		byte[] buffer  = new byte[4];
		byte[] low  = inputs[0].toBytes();
		byte[] high = inputs[1].toBytes();
		
		buffer[0] = low[0];
		buffer[1] = low[1];
		buffer[2] = high[0];
		buffer[3] = high[1];
		return buffer;
	}
}
