package robot.subsystems;


import com.torontocodingcollective.limitswitch.TLimitSwitch;
import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import robot.RobotMap;

public class IntakeSubsystem extends TSubsystem {

	public TLimitSwitch cubeDetectedSwitch = new TLimitSwitch(RobotMap.LIMIT_SWITCH_CUBE_DETECT_DIO_PORT, TLimitSwitch.DefaultState.FALSE);
	public TLimitSwitch cubeInsideSwitch = new TLimitSwitch(RobotMap.LIMIT_SWITCH_CUBE_INSIDE_DIO_PORT, TLimitSwitch.DefaultState.FALSE);
	public TLimitSwitch cubeLoadedSwitch = new TLimitSwitch(RobotMap.LIMIT_SWITCH_CUBE_LOADED_DIO_PORT, TLimitSwitch.DefaultState.FALSE);

	private TCanSpeedController rightIntakeArm = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_INTAKE_CAN_ADDRESS);
	private TCanSpeedController leftIntakeArm = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_INTAKE_CAN_ADDRESS);

	private TEncoder rightArmEncoder = ((TCanSpeedController) rightIntakeArm) .getEncoder();
	private TEncoder leftArmEncoder = ((TCanSpeedController) leftIntakeArm) .getEncoder();

	// Max setpoints for the intake arms
	private final double LEFT_ARM_MAX_SETPOINT = 3000;
	private final double RIGHT_ARM_MAX_SETPOINT = 3000;

	// the encoder count at which it is safe for both arms to exit without interferring
	private final double SAFE_ARM_OFFSET = 1800; 


	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public double getRightArmEncoderCount() {
		return rightArmEncoder.getRate();
	}

	public double getLeftArmEncoderCount() {
		return leftArmEncoder.getRate();
	}


	//sensors
	public boolean isCubeLoaded() {
		return cubeLoadedSwitch.atLimit();
	}
	public boolean isCubeInside() {
		return cubeInsideSwitch.atLimit();
	}
	public boolean isCubeDetected() {
		return cubeDetectedSwitch.atLimit();
	}


	public void openForeArms() {

		// start moving left arm at speed 0.4
		// after the encoder count reaches x, start moving left arm at 0.8
		// start moving right arm at 0.8
		// once it reaches it's limit, stop both arms

		if (getLeftArmEncoderCount() <= SAFE_ARM_OFFSET) {
			leftIntakeArm.set(0.4);
		} else {
			leftIntakeArm.set(0.8);
			rightIntakeArm.set(0.8);
		}


		if (getRightArmEncoderCount() >= RIGHT_ARM_MAX_SETPOINT) {
			rightIntakeArm.set(0);
		}
		if (getLeftArmEncoderCount() >= LEFT_ARM_MAX_SETPOINT) {
			leftIntakeArm.set(0);
		}

	}
	/*
	 * if cubedetected = true
	 * else
	 * run this code	
	 */
	public void closeForeArms() {

		if (getLeftArmEncoderCount() <= LEFT_ARM_MAX_SETPOINT - SAFE_ARM_OFFSET) {
			leftIntakeArm.set(-0.4);
		} else {
			leftIntakeArm.set(-0.8);
			rightIntakeArm.set(-0.8);
		}


		if (getRightArmEncoderCount() >= 0 && getLeftArmEncoderCount() <= 20) {
			rightIntakeArm.set(0);
		}
		if (getLeftArmEncoderCount() >= 0 && getLeftArmEncoderCount() <= 20) {
			leftIntakeArm.set(0);
		}
	}
	
	public void ejectCubeForward() {
		unloadCube();
		openForeArms();
	}
	
	
	public void ejectCubeBackward() {
		
	}
	
	public void loadCube() {
		
	}
	
	public void unloadCube() {
		
	}
	


	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {


	}


}

