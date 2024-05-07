package net.wimpi.modbus.io.ORWE515;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.util.ModbusUtil;
import static net.wimpi.modbus.io.ORWE515.OrnoWE515AddressMapping.*;

public class OrnoWE515Utils {

	private static float factorVoltage = 0.01f;
	private static float factorCurrent = 0.001f;

	public static void printVoltage(ModbusSerialMaster msm, int slaveId) throws ModbusException {
		InputRegister[] inputs = null;
		inputs = msm.readMultipleRegisters(slaveId, OrnoWE515AddressMapping.Voltage.getAddress(),
				OrnoWE515AddressMapping.Voltage.getNumByte());

		if (inputs != null) {

			float test = ModbusUtil.registerToShort(buildBufferToReadForFloat(1, inputs));
			System.out.println("Valore Volt: " + test * factorVoltage);
		}
	}

	public static void printCurrent(ModbusSerialMaster msm, int slaveId) throws ModbusException {
		InputRegister[] inputs = null;
		inputs = msm.readMultipleRegisters(slaveId, OrnoWE515AddressMapping.CurrentPhase1.getAddress(), 1);

		if (inputs != null) {

			Short test = ModbusUtil.registerToShort(buildBufferToReadForFloat(1, inputs));
			System.out.println("CurrentPhase1: " + test * factorCurrent);
		}
		inputs = msm.readMultipleRegisters(slaveId, OrnoWE515AddressMapping.SplitPhaseActivePowerP1.getAddress(), 2);

		if (inputs != null) {

			Integer test = ModbusUtil.registersToInt(buildBufferToReadForFloatV2(1, inputs));
			System.out.println("SplitPhaseActivePowerP1: " + test * factorCurrent);
		}

		inputs = msm.readMultipleRegisters(slaveId, OrnoWE515AddressMapping.Frequency.getAddress(), 1);

		if (inputs != null) {

			Short test = ModbusUtil.registerToShort(buildBufferToReadForFloat(1, inputs));
			System.out.println("Frequenza : " + test * factorVoltage);
		}
		inputs = msm.readMultipleRegisters(slaveId, SplitPhaseReactivePowerQ1.getAddress(),
				SplitPhaseReactivePowerQ1.getNumByte());

		if (inputs != null) {

			Float test = ModbusUtil.registersToFloat2(buildBufferToReadForFloatV2(1, inputs));
			System.out.println("SplitPhaseReasctivePowerQ1 : " + test * factorCurrent);
		}

		inputs = msm.readMultipleRegisters(slaveId,PowerFactor1.getAddress(), PowerFactor1.getNumByte());
		if (inputs != null) {

			Short test = ModbusUtil.registerToShort(buildBufferToReadForFloat(1, inputs));
			System.out.println("PowerFactor1 : " + test * factorCurrent);
		}
		inputs = msm.readMultipleRegisters(slaveId,
				SplitPhaseApparentPowerQ1.getAddress(), SplitPhaseApparentPowerQ1.getNumByte());
		if (inputs != null) {

			Short test = ModbusUtil.registerToShort(buildBufferToReadForFloat(1, inputs));
			System.out.println("SplitPhaseApparentPowerQ1 : " + test * factorCurrent);
		}
	}

	public static byte[] buildBufferToReadForFloat(int dim, InputRegister[] inputs) {
		byte[] buffer = new byte[2];
		byte[] low = inputs[0].toBytes();

		buffer[0] = low[0];
		buffer[1] = low[1];

		return buffer;
	}

	public static byte[] buildBufferToReadForFloatV2(int dim, InputRegister[] inputs) {
		byte[] buffer = new byte[4];
		byte[] low1 = inputs[0].toBytes();
		byte[] low2 = inputs[1].toBytes();

		buffer[0] = low1[0];
		buffer[1] = low1[1];
		buffer[2] = low2[0];
		buffer[3] = low2[1];

		return buffer;
	}
}
