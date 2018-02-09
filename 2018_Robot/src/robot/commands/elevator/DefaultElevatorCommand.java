package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class DefaultElevatorCommand extends Command {
	
	public DefaultElevatorCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.rampSubsystem);
	}

	
	public void addHeight(int currentLevel){
		if (currentLevel != 4){
			setHeight(currentLevel, currentLevel+1);
		}

	}
	public void subtractHeight(int currentLevel){
		if (currentLevel != 1){
			setHeight(currentLevel, currentLevel-1);
		}
	}
	
	public void setHeight(int currentLevel, int setLevel){

	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
