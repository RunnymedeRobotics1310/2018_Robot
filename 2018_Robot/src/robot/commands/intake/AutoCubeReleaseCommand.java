package robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;

/**
 *
 */
public class AutoCubeReleaseCommand extends Command {


	public AutoCubeReleaseCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		// If it's been 0.5 seconds, put the arm down
		if (timeSinceInitialized() < 0.1) {
			Robot.intakeSubsystem.intakeClawOpen();
		} else {
			Robot.intakeSubsystem.intakeClawClose();
		}

		
		if (timeSinceInitialized() > 0.1 && timeSinceInitialized() < 1.2) {
			Robot.intakeSubsystem.outtakeCube();
		} else {
			Robot.intakeSubsystem.intakeStop();
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
