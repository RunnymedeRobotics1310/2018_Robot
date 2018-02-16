package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;

public class IntakeSubsystem extends TSubsystem {

	// Limit switches to detect if there is a cube loaded
	public TLimitSwitch cubeDetectedSwitch = new TLimitSwitch(RobotMap.CUBE_DETECT_LIMIT_DIO_PORT,
			TLimitSwitch.DefaultState.TRUE);
	public TLimitSwitch cubeInsideSwitch = new TLimitSwitch(RobotMap.CUBE_INSIDE_LIMIT_DIO_PORT,
			TLimitSwitch.DefaultState.TRUE);
	public TLimitSwitch cubeLoadedSwitch = new TLimitSwitch(RobotMap.CUBE_LOADED_LIMIT_DIO_PORT,
			TLimitSwitch.DefaultState.TRUE);

	// Arm motors used to move the arms in and out
	private TCanSpeedController rightIntakeArm = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX,
			RobotMap.RIGHT_INTAKE_ARM_MOTOR_CAN_ADDRESS);
	private TCanSpeedController leftIntakeArm = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX,
			RobotMap.LEFT_INTAKE_ARM_MOTOR_CAN_ADDRESS);

	private TEncoder rightArmEncoder = rightIntakeArm.getEncoder();
	private TEncoder leftArmEncoder = leftIntakeArm.getEncoder();

	// Intake wheels used to draw in or remove  a cube
	private TCanSpeedController leftIntakeWheels = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX,
			RobotMap.LEFT_INTAKE_WHEEL_MOTOR_CAN_ADDRESS);
	private TCanSpeedController rightIntakeWheels = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX,
			RobotMap.RIGHT_INTAKE_WHEEL_MOTOR_CAN_ADDRESS);

	
	// Max setpoints for the intake arms
	private final double LEFT_ARM_MAX_SETPOINT = 3000;
	private final double RIGHT_ARM_MAX_SETPOINT = 3000;

	// The encoder count at which it is safe for both arms to exit without
	// interferring
	private final double SAFE_ARM_OFFSET = 1800;

	
	@Override
	public void init() {
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

	public void intakeCube() {
		leftIntakeWheels.set(0.8);
		rightIntakeWheels.set(0.8);
	}

	public void outtakeCube() {
		leftIntakeWheels.set(-0.8);
		rightIntakeWheels.set(-0.8);
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		SmartDashboard.putNumber("Left Intake Wheels", leftIntakeWheels.get());
		SmartDashboard.putNumber("Right Intake Wheels", rightIntakeWheels.get());
		SmartDashboard.putNumber("Left Intake Arm", leftIntakeArm.get());
		SmartDashboard.putNumber("Right Intake Arm", rightIntakeArm.get());
		SmartDashboard.putNumber("Left Intake Encoder", leftArmEncoder.get());
		SmartDashboard.putNumber("Right Intake Encoder", rightArmEncoder.get());

		SmartDashboard.putBoolean("Cube Loaded", isCubeLoaded());
		SmartDashboard.putBoolean("Cube Detected", isCubeDetected());
		SmartDashboard.putBoolean("Cube Inside", isCubeInside());
	}

}
