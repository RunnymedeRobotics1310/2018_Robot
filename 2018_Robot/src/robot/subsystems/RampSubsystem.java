package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.ramp.DefaultRampCommand;

public class RampSubsystem extends TSubsystem {

	// Speed Controllers
	TSpeedController leftFrontRampMotor  = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_FRONT_RAMP_MOTOR_CAN_ADDRESS, RobotConst.INVERTED);
	TSpeedController leftRearRampMotor  = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_REAR_RAMP_MOTOR_CAN_ADDRESS, RobotConst.NOT_INVERTED);
//	TSpeedController rightFrontRampMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_FRONT_RAMP_MOTOR_CAN_ADDRESS, RobotConst.INVERTED);
//	TSpeedController rightRearRampMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_REAR_RAMP_MOTOR_CAN_ADDRESS, RobotConst.NOT_INVERTED);

	// Limit Switches
	DigitalInput leftBottomLimit  = new DigitalInput(RobotMap.LEFT_RAMP_BOTTOM_LIMIT_DIO_PORT);
	DigitalInput rightBottomLimit = new DigitalInput(RobotMap.RIGHT_RAMP_BOTTOM_LIMIT_DIO_PORT);
	Solenoid rampRelease = new Solenoid(RobotMap.RAMP_RELEASE); 

	@Override
	public void init() {
	}

	private boolean atLimit (DigitalInput limit) {
		return !limit.get();
	}

	public void setRampRelease(boolean direction) {
		rampRelease.set(direction);  
	}

	public void setLeftRampSpeed(double speed) {

		if (atLimit(leftBottomLimit))
		{
			if (speed>0) {
				leftFrontRampMotor.set(0);
//				leftRearRampMotor.set(0);
			}
			else {
				leftFrontRampMotor.set(speed);
//				leftRearRampMotor.set(speed);
			}

		}
		else {
			leftFrontRampMotor.set(speed);
//			leftRearRampMotor.set(speed);
		}
	}


	public void setRightRampSpeed(double speed) {

/*		if (atLimit(rightBottomLimit))
		{
			if (speed>0) {
				rightFrontRampMotor.set(0);
				rightRearRampMotor.set(0);
			}
			else {
				rightFrontRampMotor.set(speed);
				rightRearRampMotor.set(speed);
			}
		}
		else {
			rightFrontRampMotor.set(speed);
			rightRearRampMotor.set(speed);
		}
*/	}

	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("left ramp motor", leftFrontRampMotor.get());
//		SmartDashboard.putNumber("right ramp motor", rightFrontRampMotor.get());
		SmartDashboard.putBoolean("left bottom limit switch", atLimit(leftBottomLimit));
		SmartDashboard.putBoolean("right bottom limit switch", atLimit(rightBottomLimit));
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DefaultRampCommand());
	}

}
