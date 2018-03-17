package com.torontocodingcollective.sensors.gyro;

import com.ctre.phoenix.sensors.PigeonIMU;

public class TPigeonImu extends TGyro {

	private final PigeonIMU gyro;
	
	public TPigeonImu(int canId) {
		this(canId, false);
	}
	
	public TPigeonImu(int canId, boolean inverted) {
		super(inverted);
		this.gyro = new PigeonIMU(canId);
	}
	
	@Override
	public void calibrate() {
		super.setGyroAngle(0);
	}

	@Override
	public double getAngle() {
		return super.getAngle(gyro.getAbsoluteCompassHeading());
	}

	@Override
	public double getRate() {
		return 0;
	}

	@Override
	public void reset() {
		super.setGyroAngle(0);
	}
	
}
