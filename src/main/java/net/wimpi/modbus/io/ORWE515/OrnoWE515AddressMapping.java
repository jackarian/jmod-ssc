package net.wimpi.modbus.io.ORWE515;

public enum OrnoWE515AddressMapping {

	MeterID(0x0110,1),
	Frequency(0x0130,1),
	Voltage(0x0131,1),
	CurrentPhase1(0x0139,2),
	CurrentPhase2(0x013B,2),
	CurrentPhase3(0x013D,2),
	SplitPhaseActivePowerP1(0x0140,2),
	SplitPhaseActivePowerP2(0x0142,2),
	SplitPhaseActivePowerP3(0x0144,2),
	SystemActivePowerPSum(0x0146,2),
	SplitPhaseReasctivePowerQ1(0x140,2),
	SplitPhaseReasctivePowerQ2(0x140,2),
	SplitPhaseReasctivePowerQ31(0x140,2);

	private int address;
	private int numByte;

	OrnoWE515AddressMapping(int i,int nuymByte
			) {
		this.address = i;
		this.numByte =nuymByte;
	}

	public int getAddress() {
		return address;
	}

	/**
	 * @return the numByte
	 */
	public int getNumByte() {
		return numByte;
	}
	

}
