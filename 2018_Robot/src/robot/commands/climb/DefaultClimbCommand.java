package robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class DefaultClimbCommand extends Command{

	public DefaultClimbCommand() {
		requires(Robot.climbSubsystem);	
	}

	protected void execute() {
		if (Robot.oi.getClimbArmUp()) {
			Robot.climbSubsystem.setArmSpeed(0.2);
		}
		else if (Robot.oi.getClimbArmDown()) {
			Robot.climbSubsystem.setArmSpeed(-0.2);
		}
		else {
			Robot.climbSubsystem.setArmSpeed(0);
		}
		
		if (Robot.oi.getWinchUp()){
			Robot.climbSubsystem.setWinchSpeed(-1);
		}
		else if (Robot.oi.getWinchDown()){
			Robot.climbSubsystem.setWinchSpeed(1);
		}
		else {
			Robot.climbSubsystem.setWinchSpeed(0);
		}
	}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
