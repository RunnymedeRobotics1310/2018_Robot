package com.torontocodingcollective.sensors.encoder;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TCanEncoder extends TEncoder {

	private TalonSRX canEncoder;
	
	/**
	 * Encoder constructor. Construct a Encoder given a TalonSRX device.  The encoder
	 * will be plugged into the TalonSRX.
	 * <p>
	 * The encoder is not inverted.
	 * @param canAddress
	 *
	 */
	public TCanEncoder(TalonSRX canEncoder, boolean isInverted) {
		super(isInverted);
		this.canEncoder = canEncoder;
		canEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}

	@Override
	public int get() {
		return super.get(canEncoder.getSelectedSensorPosition(0));
	}

	@Override
	public double getRate() {
		return super.getRate(canEncoder.getSelectedSensorVelocity(0));
	}

}
