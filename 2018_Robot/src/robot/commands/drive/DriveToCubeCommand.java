package robot.commands.drive;

import robot.Robot;

public class DriveToCubeCommand extends DriveDistanceCommand {
	
	public DriveToCubeCommand(double distance, double direction, double speed, 
			double timeout) {
		
		// Always brake after encountering a cube
		super(distance, direction, speed, timeout, true /* brake */);
		
	}
	
	protected void initialize() {
		super.initialize();
	}

	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}

		// Drive Command ends early if a cube is detected
		if (Robot.intakeSubsystem.isCubeDetected()) { 
			return true;
		}
		
		return false;
	}
}
