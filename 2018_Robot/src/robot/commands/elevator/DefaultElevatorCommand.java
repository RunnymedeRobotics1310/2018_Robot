package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class DefaultElevatorCommand extends Command {

	public DefaultElevatorCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevatorSubsystem);
	}
	double setLevel;
	public void addHeight(double currentLevel){
		if (currentLevel % 1 != 0) {
			new SetElevatorHeightCommand(currentLevel + 0.5);
		}
		if (currentLevel != 4){
			new SetElevatorHeightCommand(currentLevel + 1.0);
		}

	}
	public void subtractHeight(int currentLevel){
		if (currentLevel % 1 != 0) {
			new SetElevatorHeightCommand(currentLevel - 0.5);
		}
		if (currentLevel != 1){
			new SetElevatorHeightCommand(currentLevel-1.0);
		}
	}
	
	public void testElevator() {
		Robot.elevatorSubsystem.setSpeed(Robot.oi.getElevtorSpeed());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
