package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import robot.Robot;
import robot.RobotMap;
import robot.commands.climb.DefaultClimbCommand;

public class ClimbSubsystem extends TSubsystem{
	TCanSpeedController winchMotor = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX, RobotMap.CLIMB_WINCH_MOTOR_CAN_ADDRESS);
	TCanSpeedController climbArmMotor = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX, RobotMap.CLIMB_ARM_MOTOR_CAN_ADDRESS);


	public void setSpeed(double speed){
		climbArmMotor.set(speed);
	}

	public void setWinch(double speed){
		winchMotor.set(speed);
	}

	@Override
	public void init() {

	}

	@Override
	public void updatePeriodic() {

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DefaultClimbCommand());
	}



}
