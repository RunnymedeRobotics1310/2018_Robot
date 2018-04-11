package com.torontocodingcollective.sensors.encoder;

public abstract class TEncoder {

	boolean isInverted = false;
	int offset = 0;
	
	public TEncoder() {
		this(false);
	}

	public TEncoder(boolean isInverted) {
		this.isInverted = isInverted;
	}
	
	/**
	 * Reset the encoder counts for this encoder
	 */
	public void reset() {
		// set the offset for this encoder in order to 
		// get the distance to zero
		// clear the previous offset
		offset = 0;
		
		// set the offset to the current angle
		// in order to zero the output.
		offset = -get();
	}
	public void set(int encoderCount) {
		offset = 0;
		offset = -get() + encoderCount;

	}

	
	/** 
	 * Set the encoder to the passed in inversion value;
	 * @param isInverted
	 */
	public void setInverted(boolean isInverted) {
		this.isInverted = isInverted;
		reset();
	}
	
	/**
	 * Get the distance of this encoder
	 * @return distance in encoder counts
	 */
	public abstract int get();
	
	/**
	 * Get the rate (speed) of this encoder
	 * @return speed in encoder counts/second
	 */
	public abstract double getRate();
	
	protected int get(int rawDistance) {
		
		if (isInverted) {
			rawDistance = - rawDistance;
		}
		
		return rawDistance + offset;
	}
	
	protected double getRate(double rawRate) {
		
		if (isInverted) {
			return -rawRate;
		}
		
		return rawRate;
	}
	
}
