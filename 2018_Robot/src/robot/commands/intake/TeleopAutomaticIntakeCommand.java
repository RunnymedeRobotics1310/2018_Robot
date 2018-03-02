package robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class TeleopAutomaticIntakeCommand extends Command {	
	
	public TeleopAutomaticIntakeCommand(){
		requires(Robot.intakeSubsystem);
	}
	
	protected void initialize() {
		Robot.intakeSubsystem.intakeCube();
		Robot.intakeSubsystem.intakeClawOpen();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		if (Robot.intakeSubsystem.isCubeDetected()) {
			Robot.intakeSubsystem.intakeStop();
			Robot.intakeSubsystem.intakeClawClose();
			return true;
		}
		if (Robot.oi.getCancelCommand()) {
			return true;
		}
		return false;
	}

}
