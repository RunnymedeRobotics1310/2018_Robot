package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.subsystems.ElevatorSubsystem;

public class DefaultElevatorCommand extends Command {

	public DefaultElevatorCommand() {
		requires(Robot.elevatorSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		// Read the joystick 
		// If the joystick is pressed, then 
		// override the elevator movement.
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.1) {
			Robot.elevatorSubsystem.setSpeed(Robot.oi.getElevatorSpeed());
			return;
		}
		
		// Increment and decrement.
		if (Robot.oi.getElevatorUp()) {
			addHeight();
		}

		if (Robot.oi.getElevatorDown()) {
			subtractHeight();
		}
		if (Robot.oi.reset()){
			Robot.elevatorSubsystem.resetEncoders();
		}
		
	}
	
	public void addHeight() {
		
		double setLevel = Math.round(Robot.elevatorSubsystem.getLevel() + 1);
		
		if (setLevel > ElevatorSubsystem.MAX_LEVEL) {
			setLevel = ElevatorSubsystem.MAX_LEVEL;
		}
		
		Scheduler.getInstance().add(
				new SetElevatorHeightCommand(setLevel));
	}
	
	
	public void subtractHeight() {
		
		double setLevel = Math.round(Robot.elevatorSubsystem.getLevel() - 1);

		if (setLevel < ElevatorSubsystem.MIN_LEVEL) {
			setLevel = ElevatorSubsystem.MIN_LEVEL;
		}	

		Scheduler.getInstance().add(
				new SetElevatorHeightCommand(setLevel));
	}
	

	@Override
	protected boolean isFinished() {
		return false;
	}

}
