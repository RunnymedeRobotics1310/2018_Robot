package robot.commands.elevator;

import robot.Robot;
import robot.commands.drive.TSafeCommand;
import robot.subsystems.ElevatorSubsystem;

public class SetElevatorHeightCommand extends TSafeCommand {
	
	double setLevel;
	
	boolean wasElevatorUpPreviouslyPressed = false;
	boolean wasElevatorDownPreviouslyPressed = false;

	public SetElevatorHeightCommand(double setLevel) {
		super(5);
		this.setLevel = setLevel;
	}
	
	public void initialize() {
		wasElevatorUpPreviouslyPressed = Robot.oi.getElevatorUp();
		wasElevatorDownPreviouslyPressed = Robot.oi.getElevatorDown();
	}
	
	protected void execute() {
	
		// In Teleop you can adjust the setpoint within this command
		
		// Increment and decrement.
		if (wasElevatorUpPreviouslyPressed) {
			wasElevatorUpPreviouslyPressed = Robot.oi.getElevatorUp(); 
		}
		else {
			if (Robot.oi.getElevatorUp()) {
				setLevel++;
				if (setLevel > ElevatorSubsystem.MAX_LEVEL) {
					setLevel = ElevatorSubsystem.MAX_LEVEL;
				}
				wasElevatorUpPreviouslyPressed = true;
			}
		}

		if (wasElevatorDownPreviouslyPressed) {
			wasElevatorDownPreviouslyPressed = Robot.oi.getElevatorDown(); 
		}
		else {
			if (Robot.oi.getElevatorDown()) {
				setLevel--;
				if (setLevel < ElevatorSubsystem.MIN_LEVEL) {
					setLevel = ElevatorSubsystem.MIN_LEVEL;
				}
				wasElevatorDownPreviouslyPressed = true;
			}
		}

		if (Robot.elevatorSubsystem.getLevel() > setLevel) {
			Robot.elevatorSubsystem.setSpeed(-0.5);
		}
		if (Robot.elevatorSubsystem.getLevel() < setLevel) {
			Robot.elevatorSubsystem.setSpeed(0.5);
		}
	}
	
	protected boolean isFinished() {
		
		if (super.isFinished()) {
			return true;
		}
		
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.1) {
			return true;
		}
		
		if (Robot.elevatorSubsystem.getLevel() == setLevel) {
			return true;
		}
		return false;
	}
	
	protected void end() {
		Robot.elevatorSubsystem.setSpeed(0);
	}
}
