package net.wimpi.modbus.io.ORWE515;

public enum OrnoWE515AddressMapping {

	MeterID(0x0110),
	Voltage(0x0131);

	private int address;

	OrnoWE515AddressMapping(int i) {
		this.address = i;
	}

	public int getAddress() {
		return address;
	}

}
