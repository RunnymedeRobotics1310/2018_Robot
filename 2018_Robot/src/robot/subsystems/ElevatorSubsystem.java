package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.elevator.DefaultElevatorCommand;

public class ElevatorSubsystem extends TSubsystem {
	
	public static double MIN_LEVEL = 1;
	public static double MAX_LEVEL = 4;
	
	double encoderCount = 0;
	TCanSpeedController elevatorMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.ELEVATOR_MOTOR_CAN_ADDRESS);
	TEncoder encoder = elevatorMotor.getEncoder();
	
	DigitalInput bottom = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_LIMIT_DIO_PORT);
	DigitalInput top = new DigitalInput(RobotMap.ELEVATOR_TOP_LIMIT_DIO_PORT);
	DigitalInput maxHeight = new DigitalInput(RobotMap.ELEVATOR_MAXHEIGHT_LIMIT_DIO_PORT);

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
		
		return 4.5;
	}
	
	private boolean atLimit (DigitalInput limit) {
		return !limit.get();
	}
	
	public double getElevatorEncoder() {
		return encoder.get();
	}
	
	public void setSpeed(double speed) {
		
		// If the elevator is at the top and the
		// speed is positive, then set the speed
		// to zero.
		
		// If the elevator is at the bottom and the
		// speed is negative, then do not go down
		elevatorMotor.set(speed);
	} 
	
	public boolean getBottomProx() {
		return atLimit(bottom); // bottom prox. sensor value	
	}
	
	public boolean getTopProx() {
		return atLimit(top); // top prox. sensor value	
	}
	
	public boolean getMaxHeightProx() {
		return atLimit(maxHeight); // Max height prox. sensor value	
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
		
		// Safety check the elevator speed every loop and
		// stop if on a limit.
		
		
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Elevator Level", getLevel());
		SmartDashboard.putNumber("Elevator Encoder Count", getElevatorEncoder());
	}

}
