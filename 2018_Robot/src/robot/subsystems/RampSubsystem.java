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

		// If the speed is set to go up and at the 
	    // limit, then stop driving.
		if (   atLimit(leftBottomLimit)
			&& speed > 0) { 
			speed = 0;
		}

		leftFrontRampMotor.set(speed);
		
		// For some unknown reason, the rear motor turns faster
		// so scale the speed down.
		leftRearRampMotor.set(speed * .97);
	}

	public void adjustRightRearRamp(double speed) {
//		rightRearRampMotor.set(speed);
	}

	public void adjustLeftRearRamp(double speed) {
		leftRearRampMotor.set(speed);
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
		SmartDashboard.putNumber("left front ramp motor", leftFrontRampMotor.get());
		SmartDashboard.putNumber("left rear ramp motor", leftRearRampMotor.get());
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
