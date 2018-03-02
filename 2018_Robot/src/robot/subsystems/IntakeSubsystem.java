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
			RobotMap.INTAKE_ROLLER_MOTOR_CAN_ADDRESS, true);
	// Motor that moves the arm up and down
	private TCanSpeedController intakeTiltMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
			RobotMap.INTAKE_TILT_MOTOR_CAN_ADDRESS);

	private TEncoder intakeTiltEncoder = intakeTiltMotor.getEncoder();

	// The intake clamp
	private Solenoid intakeClaw = new Solenoid(RobotMap.INTAKE_CLAW_PNEUMATIC_PORT);
	

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

	public void intakeClawOpen() {
		if (!intakeClaw.get()) {
			intakeClaw.set(true);
		}
	}

	public void intakeClawClose() {
		if (intakeClaw.get()) {
			intakeClaw.set(false);
		}
	}

	public void intakeCube() {
		intakeRollerMotor.set(1.0);
	}

	public void outtakeCube() {
		intakeRollerMotor.set(-1.0);
	}
	
	public void tiltIntakeArmUp() {
//		if (getTiltEncoderCount() < LIFT_UP_ENCODER_COUNT) {
			intakeTiltMotor.set(0.8);
//		}
	}
	
	public void tiltIntakeArmDown() {
//		if (getTiltEncoderCount() > LIFT_DOWN_ENCODER_COUNT) {
			intakeTiltMotor.set(-0.8);
//		}
	}


	
	public void setIntakeTiltSpeed(double speed) {
		intakeTiltMotor.set(speed);
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

	public int getTiltEncoderCount() {
		return intakeTiltEncoder.get();
	}

	public void resetEncoders() {
		intakeTiltEncoder.reset();
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		SmartDashboard.putNumber("Intake Tilt Encoder", getTiltEncoderCount());
		SmartDashboard.putBoolean("Intake Claw Open", intakeClaw.get());
		SmartDashboard.putBoolean("Intake Cube Detected", isCubeDetected());
	}

}
