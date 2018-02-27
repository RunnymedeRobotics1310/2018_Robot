package com.torontocodingcollective.speedcontroller;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.torontocodingcollective.sensors.encoder.TCanEncoder;
import com.torontocodingcollective.sensors.encoder.TEncoder;


public class TCanSpeedController extends TSpeedController {

	private final BaseMotorController canSpeedController;
	
	private boolean isInverted = false;
	
	private double speedSetpoint = 0;
	
	public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress, boolean isInverted, int... followerCanAddresses) {
		this.isInverted = isInverted;
		canSpeedController = newController(controllerType, canAddress);
		
		for (int followerCanAddress: followerCanAddresses) {
			BaseMotorController follower = newController(controllerType, followerCanAddress);
			follower.follow(canSpeedController);
		}
	}
	
	public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress, int... canFollowerAddresses) {
		this(controllerType, canAddress, false, canFollowerAddresses);
	}
	
	public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress, 
			TCanSpeedControllerType followerControllerType, int followerCanAddress) {
		this(controllerType, canAddress, followerControllerType, followerCanAddress, false);
	}
	
	public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress, 
			TCanSpeedControllerType followerControllerType, int followerCanAddress, boolean isInverted) {

		canSpeedController = newController(controllerType, canAddress);
		
		BaseMotorController follower = newController(followerControllerType, followerCanAddress);
		follower.follow(canSpeedController);
		
		this.isInverted = isInverted;
	}
	
	@Override
	public void disable() {
		set(0.0);
	}
	
	@Override
	public double get() {
		
		return speedSetpoint;
/*		double speed = canSpeedController.getMotorOutputPercent();
		
		if (isInverted) {
			speed = -speed;
		}
		return speed;
*/	}
	
	/**
	 * Return an encoder with the same inversion setting as the motor
	 * @return TEncoder attached to this TalonSRX, or {@code null} if this
	 * is not a TalonSRX device.  The encoder is assumed to be a quadrature encoder.
	 */
	public TEncoder getEncoder() {
		if (this.canSpeedController instanceof TalonSRX) {
			return new TCanEncoder((TalonSRX) canSpeedController, isInverted);
		}
		return null;
	}

	@Override
	public boolean getInverted() {
		return isInverted;
	}

	/**
	 * NOTE: This routine is not used in the TorontoFramework but is provided for 
	 *       compile reasons.
	 */
	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public void set(double speed) {
		
		speedSetpoint = speed;
		
		if (isInverted) {
			speed = -speed;
		}
		canSpeedController.set(ControlMode.PercentOutput, speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		// If there is nothing to do, then return
		if (this.isInverted == isInverted) { 
			return;
		}
		
		// Stop the motors before inverting
		set(0.0);
		this.isInverted = isInverted;
	}

	@Override
	public void stopMotor() {
		set(0.0);
	}

	private BaseMotorController newController(TCanSpeedControllerType controllerType, int canAddress) {
		
		switch (controllerType) {
		case VICTOR_SPX: return new VictorSPX(canAddress);
		case TALON_SRX:  
		default:         return new TalonSRX(canAddress);
		}
	}

}
