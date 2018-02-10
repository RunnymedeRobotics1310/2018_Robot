package robot.commands.elevator;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class SetElevatorHeightCommand extends TSafeCommand {
	
	double setLevel;

	public SetElevatorHeightCommand(double setLevel) {
		super(5);
		this.setLevel = setLevel;
	}
	
	public void initialize() {
		if (Robot.elevatorSubsystem.getLevel() > setLevel) {
			Robot.elevatorSubsystem.setSpeed(-0.5);
		}
		if (Robot.elevatorSubsystem.getLevel() < setLevel) {
			Robot.elevatorSubsystem.setSpeed(0.5);
		}
	}
	
	//public double speedCalc(double Robot.elevatorSubsystem.getElevatorEncoder()) {
		
	//}
	
	protected boolean isFinished() {
		if (Robot.elevatorSubsystem.getLevel() == setLevel) {
			return true;
		}
		return false;
	}
}
