package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.intake.DefaultIntakeCommand;
import robot.commands.ramp.DefaultRampCommand;

public class IntakeSubsystem extends TSubsystem {

	// Limit switches to detect if there is a cube loaded
	public TLimitSwitch cubeDetectedSwitch = new TLimitSwitch(RobotMap.CUBE_DETECT_LIMIT_DIO_PORT, TLimitSwitch.DefaultState.TRUE);
	public TLimitSwitch cubeInsideSwitch = new TLimitSwitch(RobotMap.CUBE_INSIDE_LIMIT_DIO_PORT, TLimitSwitch.DefaultState.TRUE);
	public TLimitSwitch cubeLoadedSwitch = new TLimitSwitch(RobotMap.CUBE_LOADED_LIMIT_DIO_PORT, TLimitSwitch.DefaultState.TRUE);

	// Arm motors used to move the arms in and out (open/close claw)
	private TCanSpeedController rightIntakeArmMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_INTAKE_ARM_MOTOR_CAN_ADDRESS);
	private TCanSpeedController leftIntakeArmMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_INTAKE_ARM_MOTOR_CAN_ADDRESS);
	

	private TEncoder rightIntakeArmEncoder = rightIntakeArmMotor.getEncoder();
	private TEncoder leftIntakeArmEncoder = leftIntakeArmMotor.getEncoder();

	// The motors in the claw (arm) that "sucks" in the cube 
	private TCanSpeedController rightIntakeClawMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_INTAKE_CLAW_WHEELS_CAN_ADDRESS);
	private TCanSpeedController leftIntakeClawMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_INTAKE_CLAW_WHEELS_CAN_ADDRESS);
	
	// Intake wheels used to draw in or remove a cube (the wheels inside the robot)
	private TCanSpeedController leftIntakeMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_INTAKE_RAIL_WHEELS_CAN_ADDRESS);
	private TCanSpeedController rightIntakeMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_INTAKE_RAIL_WHEELS_CAN_ADDRESS);

	
	// Max setpoints for the intake arms
	private final double LEFT_ARM_MAX_SETPOINT = 3000;
	private final double RIGHT_ARM_MAX_SETPOINT = 3000;

	// The encoder count at which it is safe for both arms to exit without
	// interferring
	private final double SAFE_ARM_OFFSET = 1800;

	
	@Override
	public void init() {

	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultIntakeCommand());

	}

	public double getRightArmEncoderCount() {
		return rightIntakeArmEncoder.getRate();
	}

	public double getLeftArmEncoderCount() {
		return leftIntakeArmEncoder.getRate();
	}

	public boolean isCubeLoaded() {
		return cubeLoadedSwitch.atLimit();
	}

	public boolean isCubeInside() {
		return cubeInsideSwitch.atLimit();
	}

	public boolean isCubeDetected() {
		return cubeDetectedSwitch.atLimit();
	}
	
	
	public void openTestLeftClaw(double speed) {
		leftIntakeArmMotor.set(speed);
	}
	
	/**
	 * Open the forearms
	 * It opens the left one first, and then the right one as soon as it's clear to open
	 */
	public void _openForeArms() {

		// start moving left arm at speed 0.4
		// after the encoder count reaches x, start moving left arm at 0.8
		// start moving right arm at 0.8
		// once it reaches it's limit, stop both arms

		if (getLeftArmEncoderCount() <= SAFE_ARM_OFFSET) {
			leftIntakeArmMotor.set(0.4);
		} else {
			leftIntakeArmMotor.set(0.8);
			rightIntakeArmMotor.set(0.8);
		}

		if (getRightArmEncoderCount() >= RIGHT_ARM_MAX_SETPOINT) {
			rightIntakeArmMotor.set(0);
		}
		if (getLeftArmEncoderCount() >= LEFT_ARM_MAX_SETPOINT) {
			leftIntakeArmMotor.set(0);
		}
		
		// Set the claw motor speed so it can intake
		rightIntakeClawMotor.set(0.5);
		leftIntakeClawMotor.set(0.5);

	}
	
	
	
	
	public void openForeArms(double speed) {
		// for now we set the speed manually
		leftIntakeArmMotor.set(speed);
		rightIntakeArmMotor.set(speed);
		
		// Start the sucking motors
		rightIntakeClawMotor.set(1.0); // not working
		leftIntakeClawMotor.set(1.0);

	}
	
	public void stopClawMotors() {
		rightIntakeClawMotor.set(0);
		leftIntakeClawMotor.set(0);
		leftIntakeArmMotor.set(0.0);
		rightIntakeArmMotor.set(0.0);
	}
	
	public void closeForeArms(double speed) {
		
	}

	public void _closeForeArms() {

		if (getLeftArmEncoderCount() <= LEFT_ARM_MAX_SETPOINT - SAFE_ARM_OFFSET) {
			leftIntakeArmMotor.set(-0.4);
		} else {
			leftIntakeArmMotor.set(-0.8);
			rightIntakeArmMotor.set(-0.8);
		}

		if (getRightArmEncoderCount() >= 0 && getLeftArmEncoderCount() <= 20) {
			rightIntakeArmMotor.set(0);
		}
		if (getLeftArmEncoderCount() >= 0 && getLeftArmEncoderCount() <= 20) {
			leftIntakeArmMotor.set(0);
		}
	}

	public void intakeCube(double speed) {
		leftIntakeMotor.set(speed);
		rightIntakeMotor.set(speed);
	}
	
	public void _intakeCube(double speed) {
		
		rightIntakeMotor.set(speed);
	}
	
	public void stopIntake() { 
		rightIntakeMotor.set(0);
		leftIntakeMotor.set(0);
	}

	public void outtakeCube(double speed) {
		leftIntakeMotor.set(-speed);
		rightIntakeMotor.set(-speed);
	}
	
	public void resetEncoders() {
		leftIntakeArmEncoder.reset();
		rightIntakeArmEncoder.reset();
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
//		SmartDashboard.putNumber("Left Intake Wheels", leftIntakeMotor.get());
//		SmartDashboard.putNumber("Right Intake Wheels", rightIntakeMotor.get());
//		SmartDashboard.putNumber("Left Intake Arm", leftIntakeArmMotor.get());
//		SmartDashboard.putNumber("Right Intake Arm", rightIntakeArmMotor.get());
//		SmartDashboard.putNumber("Left Intake Encoder", leftIntakeArmEncoder.get());
//		SmartDashboard.putNumber("Right Intake Encoder", rightIntakeArmEncoder.get());
		
//		SmartDashboard.putNumber("Left Intake Claw Motor", leftIntakeClawMotor.get());
//		SmartDashboard.putNumber("Right Intake Claw Motor", rightIntakeClawMotor.get());
		
		
//
//		SmartDashboard.putBoolean("Cube Loaded", isCubeLoaded());
//		SmartDashboard.putBoolean("Cube Detected", isCubeDetected());
//		SmartDashboard.putBoolean("Cube Inside", isCubeInside());
	}

}
