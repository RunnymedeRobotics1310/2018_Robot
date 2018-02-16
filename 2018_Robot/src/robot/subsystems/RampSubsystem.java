package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.ramp.DefaultRampCommand;

public class RampSubsystem extends TSubsystem {

	// Speed Controllers
	TPwmSpeedController leftRampMotor  = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 0, RobotConst.INVERTED, 1);
	TPwmSpeedController rightRampMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 2, RobotConst.INVERTED, 3);
	
	// Limit Switches
	DigitalInput leftTopLimit     = new DigitalInput(RobotMap.LEFT_TOP_LIMIT_DIO_PORT);
	DigitalInput leftBottomLimit  = new DigitalInput(RobotMap.LEFT_BOTTOM_LIMIT_DIO_PORT);
	DigitalInput rightTopLimit    = new DigitalInput(RobotMap.RIGHT_TOP_LIMIT_DIO_PORT);
	DigitalInput rightBottomLimit = new DigitalInput(RobotMap.RIGHT_BOTTOM_LIMIT_DIO_PORT);
	
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
		if (atLimit(leftTopLimit))
		{
			if (speed>0)
				leftRampMotor.set(0);
			else
				leftRampMotor.set(speed);
		}
		else if (atLimit(leftBottomLimit))
		{
			if (speed<0)
				leftRampMotor.set(0);
			else
				leftRampMotor.set(speed);
		}
		else
			leftRampMotor.set(speed);
	}


	public void setRightRampSpeed(double speed) {
		if (atLimit(rightTopLimit))
		{
			if (speed>0)
				rightRampMotor.set(0);
			else
				rightRampMotor.set(speed);
		}
		else if (atLimit(rightBottomLimit))
		{
			if (speed<0)
				rightRampMotor.set(0);
			else
				rightRampMotor.set(speed);
		}
		else
			rightRampMotor.set(speed);
	}


	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("left ramp motor", leftRampMotor.get());
		SmartDashboard.putNumber("right ramp motor", rightRampMotor.get());
		SmartDashboard.putBoolean("left Top Limit", atLimit(leftTopLimit));
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DefaultRampCommand());
	}

}
