package com.torontocodingcollective.speedcontroller;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DMC60;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class TPwmSpeedController extends TSpeedController {

	private List<PWMSpeedController> speedControllerList = new ArrayList<PWMSpeedController>();
	
	private boolean isInverted = false;
	
	public TPwmSpeedController(TPwmSpeedControllerType controllerType, int pwmPort, int... followerPwmPorts) {
		this(controllerType, pwmPort, false, followerPwmPorts);
	}
	
	public TPwmSpeedController(TPwmSpeedControllerType controllerType, int pwmPort, boolean isInverted, int... followerPwmPorts) {
		this.isInverted = isInverted;
		speedControllerList.add(newController(controllerType, pwmPort));
		for (int followerPort: followerPwmPorts) {
			speedControllerList.add(newController(controllerType, followerPort));
		}
	}
	
	public TPwmSpeedController(TPwmSpeedControllerType controllerType, int pwmPort, 
			TPwmSpeedControllerType followerControllerType, int followerPwmPort) {
		this(controllerType, pwmPort, followerControllerType, followerPwmPort, false);
	}
	
	public TPwmSpeedController(TPwmSpeedControllerType controllerType1, int pwmPort1, 
			TPwmSpeedControllerType controllerType2, int pwmPort2, boolean isInverted) {
		speedControllerList.add(newController(controllerType1, pwmPort1));
		speedControllerList.add(newController(controllerType2, pwmPort2));
		this.isInverted = isInverted;
	}
	
	private PWMSpeedController newController(TPwmSpeedControllerType controllerType, int pwmPort) {
		
		switch (controllerType) {
		case DMC60:      return new DMC60(pwmPort);
		case SD540:      return new SD540(pwmPort);
		case SPARK:      return new Spark(pwmPort);
		case JAGUAR:     return new Jaguar(pwmPort);
		case TALON:      return new Talon(pwmPort);
		case TALON_SRX:  return new PWMTalonSRX(pwmPort);
		case VICTOR:     return new Victor(pwmPort);
		case VICTOR_SP:  return new VictorSP(pwmPort);
		case VICTOR_SPX: return new PWMVictorSPX(pwmPort);
		default:         return new VictorSP(pwmPort);
		}
	}
	@Override
	public void set(double speed) {
		if (isInverted) {
			speed = -speed;
		}
		for (PWMSpeedController speedController: speedControllerList) {
			speedController.set(speed);
		}
	}

	@Override
	public double get() {
		
		if (speedControllerList.isEmpty()) { return 0; }
		
		double speed = speedControllerList.get(0).get();
		
		if (isInverted) {
			speed = -speed;
		}
		return speed;
	}

	@Override
	public void setInverted(boolean isInverted) {
		// Stop the motors before inverting
		set(0.0);
		this.isInverted = isInverted;
	}

	@Override
	public boolean getInverted() {
		return isInverted;
	}

	@Override
	public void disable() {
		set(0);
	}

	@Override
	public void stopMotor() {
		set(0);
	}

	/**
	 * NOTE: This routine is not used in the TorontoFramework but is provided for 
	 *       compile reasons.
	 */
	@Override
	public void pidWrite(double output) {
		set(output);
	}

}
