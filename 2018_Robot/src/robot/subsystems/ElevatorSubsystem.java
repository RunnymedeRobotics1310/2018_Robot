package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.encoder.TPwmEncoder;
import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.commands.elevator.DefaultElevatorCommand;

public class ElevatorSubsystem extends TSubsystem {
	double encoderCount=0;
	TSpeedController elevatorMotor = new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, 0, RobotConst.INVERTED, 1);
	TEncoder encoder = new TPwmEncoder(pwmChannelA, pwmChannelB);
	
	elevatorMotor.set("getElevtorSpeed");
	
	public double getLevel(){
		if (encoderCount <= 2) {
			return 1;
		}
		if (encoderCount < 8) {
			return 1.5;
		}
		if (encoderCount <=12) {
			return 2;
		}
		if (encoderCount < 18) {
			return 2.5;
		}
		if (encoderCount <=22) {
			return 3;
		}
		if (encoderCount < 28) {
			return 3.5;
		}
		if (encoderCount <=32) {
			return 4;
		}
		return 0;
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated mthod stub
		setDefaultCommand(new DefaultElevatorCommand());
	}

	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Elevator Level", getLevel());
	}

}
