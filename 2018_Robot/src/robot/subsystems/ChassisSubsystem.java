package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.gyro.TAnalogGyro;
import com.torontocodingcollective.sensors.gyro.TNavXGyro;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TGryoDriveSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.drive.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends TGryoDriveSubsystem {

	private DigitalInput frontLimitSwitch = new DigitalInput(4);

	private Solenoid shifter = new Solenoid(0);

	public boolean LOW_GEAR = false;
	public boolean HIGH_GEAR = true;

	private boolean turboEnabled = false;


	public ChassisSubsystem() {

		// Uncomment this block to use CAN based speed controllers
		// Uncomment this constructor to use PWM based Speed controllers
		super(	
				(RobotConst.robot == 1311 ? new TNavXGyro() : new TAnalogGyro(0)),
				//			new TAnalogGyro(0),
				new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_MOTOR_CAN_ADDRESS,  
						RobotConst.LEFT_MOTOR_ORIENTATION,  RobotMap.LEFT_FOLLOWER_CAN_ADDRESS), 
				new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_MOTOR_CAN_ADDRESS, 
						RobotConst.RIGHT_MOTOR_ORIENTATION, RobotMap.RIGHT_FOLLOWER_CAN_ADDRESS),
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

		// Uncomment this constructor to use PWM based Speed controllers
		//		super(
		//				new TAnalogGyro(0, RobotConst.INVERTED),
		//				new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, RobotMap.LEFT_MOTOR_PWM_PORT,  RobotConst.LEFT_MOTOR_ORIENTATION, RobotMap.LEFT_FOLLOWER_PWM_PORT), 
		//				new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, RobotMap.RIGHT_MOTOR_PWM_PORT, RobotConst.RIGHT_MOTOR_ORIENTATION,RobotMap.RIGHT_FOLLOWER_PWM_PORT), 
		//				new TPwmEncoder(0, 1, RobotConst.LEFT_ENCODER_ORIENTATION),
		//				new TPwmEncoder(2, 3, RobotConst.RIGHT_ENCODER_ORIENTATION),
		//				RobotConst.DRIVE_SPEED_PID_KP,
		//				RobotConst.MAX_LOW_GEAR_SPEED,
		//				RobotConst.DRIVE_GYRO_PID_KP, 
		//				RobotConst.DRIVE_GYRO_PID_KI);
	}

	@Override
	public void init() {

		if (RobotConst.robot == 1321) {
			TAnalogGyro gyro = (TAnalogGyro) super.gyro;
			gyro.setSensitivity(0.0017);
		}
		disableTurbo();
	};

	// Initialize the default command for the Chassis subsystem.
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultChassisCommand());
	}

	//********************************************************************************************************************
	// Turbo routines
	//********************************************************************************************************************
	public void enableTurbo() {
		turboEnabled = true;
		setMaxEncoderSpeed(RobotConst.MAX_HIGH_GEAR_SPEED);
		shifter.set(HIGH_GEAR);
	}

	public void disableTurbo() {
		turboEnabled = false;
		setMaxEncoderSpeed(RobotConst.MAX_LOW_GEAR_SPEED);
		shifter.set(LOW_GEAR);
	}

	public boolean isTurboEnabled() {
		return turboEnabled;
	}

	//********************************************************************************************************************
	// Limit Switch routines
	//********************************************************************************************************************
	/**
	 * At front limit
	 * @return {@literal true} if at the limit, {@literal false} otherwise.
	 */
	public boolean atFrontLimit() {
		// The limit switch we are using is wired to return true when 
		// not activated and false otherwise.
		return !frontLimitSwitch.get();
	}

	//********************************************************************************************************************
	// Update the SmartDashboard
	//********************************************************************************************************************
	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		//automatic high gear
		/*double leftSpeed = super.leftMotor.get();
		double rightSpeed = super.rightMotor.get();
		//System.out.println(leftSpeed + " " + rightSpeed + " " + getEncoderSpeed());
		/*if (Math.abs(getEncoderSpeed()) >= robot.RobotConst.MAX_LOW_GEAR_SPEED*0.6) {
			if ((Math.abs(leftSpeed) > 0.9 || Math.abs(rightSpeed) > 0.9) 
					&& ((leftSpeed >= 0 && rightSpeed >= 0) || (leftSpeed <= 0 && rightSpeed <= 0))) {
				enableTurbo();
				//System.out.println("enable turbo");
			}
		}
		
		if (Math.abs(getEncoderSpeed()) <= robot.RobotConst.MAX_LOW_GEAR_SPEED*0.4) {
			disableTurbo();
		//	System.out.println("disable turbo 1");
		}
		if (Math.abs(leftSpeed) < 0.9 && Math.abs(rightSpeed) < 0.9) {
			disableTurbo();
			//System.out.println("disable turbo 2");
		}
		if (!((leftSpeed >= 0 && rightSpeed >= 0) || (leftSpeed <= 0 && rightSpeed <= 0))) {
			disableTurbo();
			//System.out.println("disable turbo 3");
		}*/

		super.updatePeriodic();

		SmartDashboard.putBoolean("Turbo Enabled", turboEnabled);
		SmartDashboard.putBoolean("At Front Limit", atFrontLimit());

	}

}
