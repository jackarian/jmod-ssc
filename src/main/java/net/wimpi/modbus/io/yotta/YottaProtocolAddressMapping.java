package net.wimpi.modbus.io.yotta;

/**
 * Versione dei moduli
 * A-1057/1058/1068/1069/1051/1055/1055S/1060 Address Mapping
 * @author jackarian
 *
 */
public enum YottaProtocolAddressMapping {
	//40065
	CommunicationFailSafeTime(0x40),
	//40211 Module Name 1
	ModuleName1(0xd2),
	//40212 Module Name 2
	ModuleName2(0xd3),
	//40213 Version 1
	Version1(0xd4),
	//40214 Version 2
	Version2(0xd5),
	//40300 Module's ID In Normal Mode
	ModuleID(0x12b),
	//40301 Protocol
	Protocol(0x12c),
	//40302 Baud Rate In Normal Mode
	BaudRate(0x12d),
	//40303 Parity Option In Normal Mode
	Parity(0x12e),
	//40304 Stop Bits In Normal Mode
	StopBits(0x12f),
	//40305 Time Out Setting In Normal Mode
	TimeOutSetting(0x130);
	
	private int address;
	
	YottaProtocolAddressMapping(int i) {
		this.address = i;
	}

	public int getAddress() {
		return address;
	}
	
	
	

}
