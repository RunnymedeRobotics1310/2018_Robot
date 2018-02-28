package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.intake.DefaultIntakeCommand;

public class IntakeSubsystem extends TSubsystem {

	// Limit switches to detect if there is a cube loaded
	public TLimitSwitch leftCubeDetectedSwitch = new TLimitSwitch(RobotMap.LEFT_CUBE_DETECT_DIO_PORT,
			TLimitSwitch.DefaultState.TRUE);
	public TLimitSwitch rightCubeDetectedSwitch = new TLimitSwitch(RobotMap.RIGHT_CUBE_DETECT_DIO_PORT,
			TLimitSwitch.DefaultState.TRUE);

	// Motor that moves the roller to suck in the cube
	private TCanSpeedController intakeRollerMotor = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX,
			RobotMap.INTAKE_ROLLER_MOTOR_CAN_ADDRESS);
	// Motor that moves the arm up and down
	private TCanSpeedController intakeLiftMotor = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX,
			RobotMap.INTAKE_LIFT_MOTOR_CAN_ADDRESS);

	private TEncoder intakeLiftEncoder = intakeLiftMotor.getEncoder();

	// The intake clamp
	private Solenoid intakeClamp = new Solenoid(RobotMap.INTAKE_CLAMP_PNEUMATIC_PORT);
	

	// // The motors in the claw (arm) that "sucks" in the cube
	// private TCanSpeedController rightIntakeClawMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.RIGHT_INTAKE_CLAW_WHEELS_CAN_ADDRESS);
	// private TCanSpeedController leftIntakeClawMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.LEFT_INTAKE_CLAW_WHEELS_CAN_ADDRESS);
	//
	// // Intake wheels used to draw in or remove a cube (the wheels inside the
	// robot)
	// private TCanSpeedController leftIntakeMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.LEFT_INTAKE_RAIL_WHEELS_CAN_ADDRESS);
	// private TCanSpeedController rightIntakeMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.RIGHT_INTAKE_RAIL_WHEELS_CAN_ADDRESS);

	// Encoder count for when the lift motors are at the highest and the lowest
	private final double LIFT_UP_ENCODER_COUNT = 100;
	private final double LIFT_DOWN_ENCODER_COUNT = 0;


	@Override
	public void init() {

	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultIntakeCommand());
	}

	public void intakeClampOpen() {
		if (intakeClamp.get()) {
			intakeClamp.set(true);
		}
	}

	public void intakeClampClose() {
		if (!intakeClamp.get()) {
			intakeClamp.set(false);
		}
	}

	public void intakeCube() {
		intakeRollerMotor.set(1.0);
	}

	public void outtakeCube() {
		intakeRollerMotor.set(-1.0);
	}
	
	public void liftIntakeArmUp() {
		if (getLiftEncoderCount() < LIFT_UP_ENCODER_COUNT) {
			intakeLiftMotor.set(0.8);
		}
	}
	
	public void liftIntakeArmDown() {
		if (getLiftEncoderCount() > LIFT_DOWN_ENCODER_COUNT) {
			intakeLiftMotor.set(-0.8);
		}
	}

	public boolean isCubeDetected() {
		// Cube is detected if both limit switches have detected something, aka a cube
		return leftCubeDetectedSwitch.atLimit() && rightCubeDetectedSwitch.atLimit();
	}


	public void intakeStop() {
		intakeRollerMotor.stopMotor();
	}
	
	public boolean isIntakeMotorRunning() {
		return intakeRollerMotor.get() > 0.1;
	}

	public int getLiftEncoderCount() {
		return intakeLiftEncoder.get();
	}

	public void resetEncoders() {
		intakeLiftEncoder.reset();
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		SmartDashboard.putNumber("Intake lift Encoder", getLiftEncoderCount());
		SmartDashboard.putBoolean("Intake Clamp Open", intakeClamp.get());
		SmartDashboard.putBoolean("Intake Cube Detected", isCubeDetected());
		// SmartDashboard.putNumber("Left Intake Wheels", leftIntakeMotor.get());
		// SmartDashboard.putNumber("Right Intake Wheels", rightIntakeMotor.get());
		// SmartDashboard.putNumber("Left Intake Arm", leftIntakeArmMotor.get());
		// SmartDashboard.putNumber("Right Intake Arm", rightIntakeArmMotor.get());
		// SmartDashboard.putNumber("Left Intake Encoder", leftIntakeArmEncoder.get());
		// SmartDashboard.putNumber("Right Intake Encoder",
		// rightIntakeArmEncoder.get());

		// SmartDashboard.putNumber("Left Intake Claw Motor",
		// leftIntakeClawMotor.get());
		// SmartDashboard.putNumber("Right Intake Claw Motor",
		// rightIntakeClawMotor.get());

		//
		// SmartDashboard.putBoolean("Cube Loaded", isCubeLoaded());
		// SmartDashboard.putBoolean("Cube Detected", isCubeDetected());
		// SmartDashboard.putBoolean("Cube Inside", isCubeInside());
	}

}
