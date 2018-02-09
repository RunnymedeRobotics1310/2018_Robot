package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.commands.elevator.DefaultElevatorCommand;
import robot.commands.ramp.DefaultRampCommand;

public class ElevatorSubsystems extends TSubsystem {
double encoderCount=0;
	TPwmSpeedController elevatorMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 0, RobotConst.INVERTED, 1);


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
	

	public int getLevel(){
		if (encoderCount == 0) {
			return 1;
		}
		if (encoderCount == 10) {
			return 2;
		}
		if (encoderCount == 20) {
			return 3;
		}
		if (encoderCount == 30) {
			return 4;
		}
		return 0;
	}
	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("left ramp motor", getLevel());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated mthod stub
	//	setDefaultCommand(new DefeaultElevatorCommand());
	}

}
