package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.commands.ramp.DefaultRampCommand;

public class RampSubsystem extends TSubsystem {

	TPwmSpeedController leftRampMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 0, RobotConst.INVERTED, 1);
	TPwmSpeedController rightRampMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 2, RobotConst.INVERTED, 3);
			
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void setLeftRampSpeed(double speed) {
		leftRampMotor.set(speed);
	}
	public void setRightRampSpeed(double speed) {
		rightRampMotor.set(speed);
	}
	
	
	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("left ramp motor", leftRampMotor.get());
		SmartDashboard.putNumber("right ramp motor", rightRampMotor.get());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DefaultRampCommand());
	}

}
