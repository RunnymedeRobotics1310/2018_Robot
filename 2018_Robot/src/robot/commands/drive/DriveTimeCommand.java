package robot.commands.drive;

import robot.Robot;

/**
 *
 */
public class DriveTimeCommand extends TSafeCommand {

	private final boolean brakeAtEnd;
	
    public DriveTimeCommand(double maxTimeSec, double speed, boolean brakeAtEnd) {
    	super(maxTimeSec);
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
		this.brakeAtEnd = brakeAtEnd;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassisSubsystem.setSpeed(.1, .1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if (super.isFinished()) { return true; }

    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (brakeAtEnd) {
    		Robot.chassisSubsystem.setSpeed(0, 0);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
