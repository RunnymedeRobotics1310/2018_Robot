package robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class DefaultClimbCommand extends Command{

	public DefaultClimbCommand() {
		requires(Robot.climbSubsystem);	
	}

	protected void execute() {
		if (Robot.oi.getClimbArmUp()) {
			Robot.climbSubsystem.setSpeed(0.5);
		}
		if (Robot.oi.getClimbArmDown()) {
			Robot.climbSubsystem.setSpeed(-0.5);
		}
		
		if (Robot.oi.getWinchUp()){
			Robot.climbSubsystem.setWinch(0.5);
		}
		if (Robot.oi.getWinchDown()){
			Robot.climbSubsystem.setWinch(-0.5);
		}
	}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
