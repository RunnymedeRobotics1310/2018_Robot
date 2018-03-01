package robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class TeleopAutomaticIntakeCommand extends Command {	
	
	protected void initialize() {
		Robot.intakeSubsystem.intakeCube();
		Robot.intakeSubsystem.intakeClawOpen();
	}
	
	protected boolean isFinished() {
		if (Robot.intakeSubsystem.isCubeDetected()) {
			Robot.intakeSubsystem.intakeStop();
			Robot.intakeSubsystem.intakeClawClose();
			return true;
		}
		return false;
	}

}
