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
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.2) {
			Robot.elevatorSubsystem.setSpeed(Robot.oi.getElevatorSpeed());
		}
		else {
			// if the robot is not at the bottom, then set the motor
			// speed to 0.15 to lock the elevator from falling by gravity
			if (Robot.elevatorSubsystem.getLevel() >= 1.0) {
				if (Robot.intakeSubsystem.isCubeDetected()) {
					Robot.elevatorSubsystem.setSpeed(0.2);
				} else {
					Robot.elevatorSubsystem.setSpeed(0.15);
				}
			} else {
				Robot.elevatorSubsystem.setSpeed(0);
			}
		}


		// Increment and decrement.
		int elevatorMove = Robot.oi.getElevatorMove();
		
		if (elevatorMove == 0) {
			addHeight();
		}

		if (elevatorMove == 180) {
			subtractHeight();
		}

		if (Robot.oi.getElevatorSwitch()) {
			Scheduler.getInstance().add(new SetElevatorHeightCommand(2));		
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
