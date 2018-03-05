package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class DriveDistanceCommand extends DriveDirectionCommand {
	
	double distance = 0; // in inches 
	double stopDistanceEncoderCounts = 0; // encoder counts to stop at

	private final double STOPPING_ENCODER_COUNTS = 20; // to reduce stopping overshoot
	
	public DriveDistanceCommand(double distance, double direction, double speed, 
			double timeout, boolean brakeWhenFinished) {
		
		super(direction, speed, timeout, brakeWhenFinished);
		
		this.distance = distance;
		this.stopDistanceEncoderCounts = 
				distance * RobotConst.ENCODER_COUNTS_PER_INCH - STOPPING_ENCODER_COUNTS;
		//System.out.println(stopDistanceEncoderCounts);
	}
	
	protected void initialize() {
		super.initialize();
		//System.out.println("starting drive");
		Robot.chassisSubsystem.resetEncoders();
	}
	
	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}
	
		if (Robot.chassisSubsystem.getEncoderDistance() > stopDistanceEncoderCounts) {
			//System.out.println("ending drive");
			return true;
		}
		
		return false;
	}
}
