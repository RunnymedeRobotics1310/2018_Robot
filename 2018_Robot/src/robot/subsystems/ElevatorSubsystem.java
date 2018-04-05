package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.elevator.DefaultElevatorCommand;

public class ElevatorSubsystem extends TSubsystem {

	public static double MIN_LEVEL = 0;
	public static double MAX_LEVEL = 5;

	
	TCanSpeedController elevatorMotor = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.ELEVATOR_MOTOR_CAN_ADDRESS,
			TCanSpeedControllerType.VICTOR_SPX, RobotMap.ELEVATOR_MOTOR_FOLLOWER_CAN_ADDRESS);
	TEncoder elevatorEncoder = elevatorMotor.getEncoder();

	TLimitSwitch bottom = new TLimitSwitch(RobotMap.ELEVATOR_BOTTOM_LIMIT_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch top = new TLimitSwitch(RobotMap.ELEVATOR_TOP_LIMIT_DIO_PORT, DefaultState.TRUE);

	public double getLevel(){
		double encoderCount = elevatorEncoder.get();
		if (encoderCount <= 2) {//pickup level
			return 0;
		}

		if (encoderCount < 700) {
			return 0.5;
		}
		if (encoderCount <= 1000) {// resting intake level
			return 1;
		}
		if (encoderCount < 2400) {
			return 1.5;
		}
		if (encoderCount <=2700) {//switch deposit level
			return 2;
		}
		if (encoderCount < 5900) {
			return 2.5;
		}
		if (encoderCount <=6200) {//low scale level
			return 3;
		}
		if (encoderCount < 7300) {
			return 3.5;
		}
		if (encoderCount <= 7700) {//normal scale level
			return 4;
		}
		if (encoderCount < 8500) {
			return 4.5;
		}
		if (encoderCount < 8700) {//high scale level
			return 5;
		}
		
		return 5.5;
	}
	public void resetEncoders() {

		if (elevatorEncoder == null) { return; } 

		elevatorEncoder.reset();
	}

	public double getElevatorEncoder() {
		return elevatorEncoder.get();
	}

	public void setSpeed(double speed) {
		
		// If the elevator is at the top and the
		// speed is positive, then set the speed
		// to zero.
		if (top.atLimit() && speed > 0) {
			elevatorMotor.set(0);
		}
		// If the elevator is at the bottom and the
		// speed is negative, then do not go down
		else if (bottom.atLimit() && speed < 0) {
			elevatorMotor.set(0);
		}
		else {
			elevatorMotor.set(speed);
		}
	} 

	public boolean atLowerLimit() {
		return bottom.atLimit(); // bottom prox. sensor value	
	}

	public boolean atUpperLimit() {
		return top.atLimit(); // top prox. sensor value	
	}

	@Override
	public void init() {
		elevatorEncoder.setInverted(true);
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
		if (atLowerLimit()) {
			resetEncoders();
			if (elevatorMotor.get() < 0) {
				setSpeed(0);
			}
		}

		if (atUpperLimit() && elevatorMotor.get() > 0) {
			setSpeed(0);
		}

		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Elevator Level", getLevel());
		SmartDashboard.putNumber("Elevator Encoder Count", getElevatorEncoder());
		SmartDashboard.putBoolean("Bottom Elevator Limit", atLowerLimit());
		SmartDashboard.putBoolean("Top Elevator Limit", atUpperLimit());
		SmartDashboard.putNumber("Elevator Motor", elevatorMotor.get());
	}

}
