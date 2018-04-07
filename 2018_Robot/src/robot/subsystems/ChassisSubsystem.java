package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.gyro.TAnalogGyro;
import com.torontocodingcollective.sensors.gyro.TNavXGyro;
import com.torontocodingcollective.sensors.gyro.TSpiGyro;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TGryoDriveSubsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.drive.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends TGryoDriveSubsystem {

	private Solenoid shifter = new Solenoid(RobotMap.SHIFTER_PNEUMATIC_PORT);

	public boolean LOW_GEAR = false;
	public boolean HIGH_GEAR = true;

	private boolean turboEnabled = false;

	private double leftSpeedSetpoint = 0;
	private double rightSpeedSetpoint = 0;
	
	public ChassisSubsystem() {

		// Uncomment this block to use CAN based speed controllers
				// Uncomment this constructor to use PWM based Speed controllers
				super(	
						((RobotConst.robot == 1310) ? new TSpiGyro() : new TNavXGyro()),
						//			new TAnalogGyro(0),
						new TCanSpeedController(
								TCanSpeedControllerType.TALON_SRX, 
								RobotMap.LEFT_DRIVE_MOTOR_CAN_ADDRESS,
								TCanSpeedControllerType.VICTOR_SPX,
								RobotMap.LEFT_DRIVE_FOLLOWER_CAN_ADDRESS,
								RobotConst.LEFT_MOTOR_ORIENTATION), 
						new TCanSpeedController(
								TCanSpeedControllerType.TALON_SRX, 
								RobotMap.RIGHT_DRIVE_MOTOR_CAN_ADDRESS, 
								TCanSpeedControllerType.VICTOR_SPX,
								RobotMap.RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS,
								RobotConst.RIGHT_MOTOR_ORIENTATION),
						RobotConst.DRIVE_GYRO_PID_KP,
						RobotConst.DRIVE_GYRO_PID_KI);

				// Get the encoders attached to the CAN bus speed controller.
				TEncoder leftEncoder  = ((TCanSpeedController) super.leftMotor) .getEncoder();
				TEncoder rightEncoder = ((TCanSpeedController) super.rightMotor).getEncoder();

				super.setEncoders(
						leftEncoder,  RobotConst.LEFT_ENCODER_ORIENTATION,
						rightEncoder, RobotConst.RIGHT_ENCODER_ORIENTATION,
						RobotConst.DRIVE_SPEED_PID_KP,
						RobotConst.MAX_LOW_GEAR_SPEED);

	}

	@Override
	public void init() {
		disableTurbo();
	};

	// Initialize the default command for the Chassis subsystem.
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultChassisCommand());
	}

	// ********************************************************************************************************************
	// Slew rate on acceleration (takes at least 1 second to stop)
	// ********************************************************************************************************************
	@Override
	public void setSpeed(double leftSpeedSetpoint, double rightSpeedSetpoint) {

		this.leftSpeedSetpoint = leftSpeedSetpoint;
		this.rightSpeedSetpoint = rightSpeedSetpoint;
		
		updateSpeed();
	}
	
	private void updateSpeed() {
		
		// If the PIDs are enabled, then bypass the soft drive code
		if (speedPidsEnabled()) {
			super.setSpeed(leftSpeedSetpoint, rightSpeedSetpoint);
			return;
		}
		
		double leftSpeed = leftMotor.get();
		double rightSpeed = rightMotor.get();
		
		if (Math.abs(leftSpeedSetpoint - leftSpeed) < .02) {
			leftSpeed = leftSpeedSetpoint;
		}
		else if (Math.abs(leftSpeedSetpoint - leftSpeed) > .8) {
			if (leftSpeedSetpoint > leftSpeed) {
				leftSpeed += .05;
			}
			else {
				leftSpeed -= .05;
			}
		}
		else {
			if (leftSpeedSetpoint > leftSpeed) {
				leftSpeed += .03;//.02
			}
			else {
				leftSpeed -= .03;
			}
		}

		if (Math.abs(rightSpeedSetpoint - rightSpeed) < .02) {
			rightSpeed = rightSpeedSetpoint;
		}
		else if (Math.abs(rightSpeedSetpoint - rightSpeed) > .8) {
			if (rightSpeedSetpoint > rightSpeed) {
				rightSpeed += .05;
			}
			else {
				rightSpeed -= .05;
			}
		}
		else {
			if (rightSpeedSetpoint > rightSpeed) {
				rightSpeed += .03;
			}
			else {
				rightSpeed -= .03;
			}
		}
		
		super.setSpeed(leftSpeed, rightSpeed);
	}
	
	// ********************************************************************************************************************
	// Turbo routines
	// ********************************************************************************************************************
	public void enableTurbo() {
		turboEnabled = true;
		//System.out.println("Turbo enabled");
		setMaxEncoderSpeed(RobotConst.MAX_HIGH_GEAR_SPEED);
		shifter.set(HIGH_GEAR);
	}

	public void disableTurbo() {
		turboEnabled = false;
		//System.out.println("Turbo disabled");
		setMaxEncoderSpeed(RobotConst.MAX_LOW_GEAR_SPEED);
		shifter.set(LOW_GEAR);
	}

	public boolean isTurboEnabled() {
		return turboEnabled;
	}

	// ********************************************************************************************************************
	// Update the SmartDashboard
	// ********************************************************************************************************************
	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {

		super.updatePeriodic();

		SmartDashboard.putBoolean("Turbo Enabled", isTurboEnabled());
	}

}
