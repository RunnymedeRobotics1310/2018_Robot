package robot.commands.elevator;

import robot.Robot;
import robot.commands.drive.TSafeCommand;
import robot.subsystems.ElevatorSubsystem;

public class SetElevatorHeightCommand extends TSafeCommand {
	
	private double setLevel;
	
	public SetElevatorHeightCommand(double setLevel) {
		super(0);
		requires (Robot.elevatorSubsystem);
		this.setLevel = setLevel;
	}
	
	public void initialize() {
	}
	
	protected void execute() {
	
		// In Teleop you can adjust the setpoint within this command
		
		// Increment and decrement.
		if (Robot.oi.getElevatorUp()) {
			setLevel++;
			if (setLevel > ElevatorSubsystem.MAX_LEVEL) {
				setLevel = ElevatorSubsystem.MAX_LEVEL;
			}
		}

		if (Robot.oi.getElevatorDown()) {
			setLevel--;
			if (setLevel < ElevatorSubsystem.MIN_LEVEL) {
				setLevel = ElevatorSubsystem.MIN_LEVEL;
			}
			if (setLevel == ElevatorSubsystem.MIN_LEVEL /* &&  !Robot.intakeSubsystem.isIntakeExtended() */) {
				setLevel = ElevatorSubsystem.MIN_LEVEL +1;
			}
		}

		if (Robot.elevatorSubsystem.getLevel() > setLevel) {
			Robot.elevatorSubsystem.setSpeed(-1.0);
		}
		if (Robot.elevatorSubsystem.getLevel() < setLevel) {
			Robot.elevatorSubsystem.setSpeed(1.0);
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
