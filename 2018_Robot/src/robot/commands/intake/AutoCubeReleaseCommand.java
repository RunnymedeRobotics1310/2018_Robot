package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

/**
 *
 */
public class AutoCubeReleaseCommand extends TSafeCommand {
	private boolean release;
	private enum Step { OUTTAKE, OPEN, FINISH, RELEASE, CATCH }

	private Step curStep = Step.OUTTAKE;

	public AutoCubeReleaseCommand(boolean release) {
		// Use requires() here to declare subsystem dependencies
		this.release = release;
		requires(Robot.intakeSubsystem);
	}
	public AutoCubeReleaseCommand() {
		this(false);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (release) {
			curStep = Step.RELEASE;
		}
		else {
			curStep = Step.OUTTAKE;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		switch (curStep) {
		case RELEASE: {
			Robot.intakeSubsystem.intakeClawOpen();
			if (timeSinceInitialized() > .2) {
				curStep = Step.CATCH;
			}
			break;
		}
		case CATCH: {
			Robot.intakeSubsystem.intakeClawClose();
			if (timeSinceInitialized() > .3) {
				curStep = Step.OUTTAKE;
			}
			break;
		}
		case OUTTAKE:
			Robot.intakeSubsystem.outtakeCube();
			if (timeSinceInitialized() > .75) {
				curStep = Step.OPEN;
			}
			break;

		case OPEN:
			Robot.intakeSubsystem.intakeClawOpen();
			if (timeSinceInitialized() > 1.0) {
				curStep = Step.FINISH;
			}

		case FINISH:
			break;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (super.isFinished()) {
			return true;
		}
		if (curStep == Step.FINISH) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intakeSubsystem.intakeStop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
