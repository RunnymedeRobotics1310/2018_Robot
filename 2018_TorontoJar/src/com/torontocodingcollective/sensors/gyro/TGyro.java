package com.torontocodingcollective.sensors.gyro;

import edu.wpi.first.wpilibj.GyroBase;

public abstract class TGyro extends GyroBase {

	private boolean isInverted;
	private double offset = 0;
	
	public TGyro(boolean inverted) {
		this.isInverted = inverted;
	}
	
	public abstract double getAngle();
	
	public double getPitch() {
		return 0;
	}
	
	public abstract double getRate();
	public void resetGyroAngle() {
		setGyroAngle(0);
	}

	public void setGyroAngle(double angle) {
		
		// clear the previous offset
		offset = 0;
		
		// set the offset to the current angle
		// in order to zero the output.
		offset = -getAngle();
		
		// This offset will result in an output
		// of zero.  Add the passed in angle
		// to make the desired angle
		offset += angle;
	}

	public boolean supportsPitch() {
		return false;
	}
	
	private double getNormalizedAngle(double rawAngle) {
		
		double angle = rawAngle % 360.0;
		
		if (angle < 0) {
			angle += 360.0;
		}
		
		return angle;
	}
	
	protected double getAngle(double rawAngle) {
		
		// Invert before subtracting the offset.
		if (isInverted) {
			rawAngle = -rawAngle;
		}

		return getNormalizedAngle(rawAngle + offset);
	}
	
	protected double getRate(double rawRate) {
		if (isInverted) {
			return -rawRate;
		}
		
		return rawRate;
	}
	
	protected boolean isInverted() {
		return isInverted;
	}
	
}
