package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.commands.ramp.DefaultRampCommand;

public class RampSubsystem extends TSubsystem {

	TPwmSpeedController leftRampMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 0, RobotConst.INVERTED, 1);
	TPwmSpeedController rightRampMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 2, RobotConst.INVERTED, 3);
	DigitalInput leftTopLimit = new DigitalInput(0);
	DigitalInput leftBottomLimit = new DigitalInput(1);
	DigitalInput rightTopLimit = new DigitalInput(2);
	DigitalInput rightBottomLimit = new DigitalInput(3);

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	private boolean atLimit (DigitalInput limit) {
		return !limit.get();
	}


	// Should deny bad speeds while at limit whilst still allowing for movement in the opposite direction
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
