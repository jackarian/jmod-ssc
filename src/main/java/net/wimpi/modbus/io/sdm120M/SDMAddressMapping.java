package net.wimpi.modbus.io.sdm120M;

public enum SDMAddressMapping {
	//30001 Voltage Volts Float 00 00
	Voltage(0x00),
	//30007 Current Amps Float 00 06
	CurrentAmps(0x06),
	//30013 Active power Watts Float 00 0C
	ActivePowerWatts(0x0C),
	//30019 Apparent power VA  Float 00 12
	ApparentPower(0x12),
	//30025 Reactive power VAr Float 00 18
	ReactivePower(0x18),
	//30031 Power factor None  Float 00 1E
	PowerFactor(0x1e),	
	//30071 Frequency Hz       Float 00 46
	Frequency(0x46),
	//30073 Import active energy kWh Float 00 48
	ImportActiEnergy(0x48),
	//30075 Export active energy kWh Float 00 4A
	ExportActiveEnergy(0x4a),
	//30077 Import reactive energy kvarh Float 00 4C
	ImporReactiveEnergy(0x4c),
	//30079 Export reactive energy kvarh Float 00 4E
	ExportReactiveEnergy(0x4e),
	//30343 Total active energy kWh Float 01 56
	TotalActiveEnergy(0x56),
	//30345 Total reactive energy Kvarh Float 01 58
	TotalReactiveEnergy(0x58),
	/**
	 * Function code 10 to set holding parameter ，function code 03 to read holding parameter
	 */
	/*40013	Relay Pulse	Width	Float	00 0C	Write relay on period in
			milliseconds: 60, 100 or 200,
			default 100ms.*/
	RelayPulseWidth(0x0c),
	/*40019	Network Parity	Stop	Float	00 12
		Write the network port parity/stop	bits for MODBUS Protocol, where:
		0 = One stop bit and no parity,
		default. 1 = One stop bit and even
		parity. 2 = One stop bit and odd
		parity.3 = Two stop bits and no
		parity.Requires a restart to become effective.*/
	NetworkParity(0x12),
	
	/*40021 Meter ID Float 00 14 Ranges from 1 to 247,	.Default ID is 1.*/
	
	MeterID(0x14),
	/*40029 Baud rate Float 00 1C 
	0:2400bps(default)
	1:4800bps
	2:9600bps
	5:1200bps */
	BaudRate(0x1c), 
	
	/*40087 Pulse 1 output	mode	Hex 00 56 
     	0001: Import active energy,
		0002: Import + export active	energy,
		0004: Export active energy,	(default).
		0005: Import reactive energy,
		0006: Import + export reactive	energy,
		0008: Export reactive energy,*/
	Pulse1OutputMode(0x56),
	/*463745 Time of scroll	display	BCD F9 00 0-30s
	Default 0:does not display in turns*/
	TimeOfScroll(0x00),
	/*463761 Pulse 1 output Hex F9 10 0000:0.001kWh/imp(default)
	0001:0.01kWh/imp
	0002:0.1kWh/imp
	0003:1kWh/imp */
	Pulse1Output(0x10),
	/*463777 Measurement	mode	Hex F9 20 Data Format：Hex
	0001:mode 1(total = import)
	0002:mode 2
	(total = import + export）(default)
	0003:mode 3
	(total = import - export) */
	Measurement(0x20);
    private int address;
	
	SDMAddressMapping(int i) {
		this.address = i;
	}

	public int getAddress() {
		return address;
	}
}
