package net.wimpi.modbus.io.ORWE515;

public enum OrnoWE515AddressMapping {

	MeterID(0x010,1),
	Frequency(0x130,1),
	Voltage(0x131,1),
	//Single Phase
	CurrentPhase1(0x139,2),
	CurrentPhase2(0x13B,2),
	CurrentPhase3(0x13D,2),
	//Single Phase
	SplitPhaseActivePowerP1(0x140,2),
	
	SplitPhaseActivePowerP2(0x142,2),
	SplitPhaseActivePowerP3(0x144,2),	
	SystemActivePowerPSum(0x146,2),
	//Single Phase
	SplitPhaseReactivePowerQ1(0x148,2),
	SplitPhaseReactivePowerQ2(0x14A,2),
	SplitPhaseReactivePowerQ3(0x14C,2),
	//Single Phase
	SplitPhaseApparentPowerQ1(0x150,2),
	PowerFactor1(0x158,1);

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
