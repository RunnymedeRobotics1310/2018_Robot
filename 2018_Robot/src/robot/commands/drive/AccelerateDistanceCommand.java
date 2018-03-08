package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class AccelerateDistanceCommand extends DriveDirectionCommand {
	
	double distance = 0; // in inches 
	double stopDistanceEncoderCounts = 0; // encoder counts to stop at
	double slowDistanceEncoderCounts = 0;

	double speed;
	double curSpeed = 0;
	
	private final double STOPPING_ENCODER_COUNTS = 20; // to reduce stopping overshoot
	
	public AccelerateDistanceCommand(double distance, double direction, double speed, 
			double timeout, boolean brakeWhenFinished) {
		
		super(direction, speed, timeout, brakeWhenFinished);
		this.speed = speed;
		this.distance = distance;
		this.stopDistanceEncoderCounts = 
				distance * RobotConst.ENCODER_COUNTS_PER_INCH - STOPPING_ENCODER_COUNTS;
		this.slowDistanceEncoderCounts = 
				(distance-30) * RobotConst.ENCODER_COUNTS_PER_INCH - STOPPING_ENCODER_COUNTS;
	}
	
	protected void initialize() {
		super.initialize();
		//System.out.println("starting drive");
		Robot.chassisSubsystem.resetEncoders();
		curSpeed = 0;
	}
	
	protected void execute() {
		if (Robot.chassisSubsystem.getEncoderDistance() > slowDistanceEncoderCounts) {
			if (curSpeed > speed/2) {
				curSpeed = Math.max(curSpeed-.02, speed/2);
			}
		}
		else {
			if (curSpeed < speed) {
				curSpeed = Math.min(curSpeed + .02, speed);
			}
		}
		super.setSpeed(curSpeed);
		super.execute();
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
