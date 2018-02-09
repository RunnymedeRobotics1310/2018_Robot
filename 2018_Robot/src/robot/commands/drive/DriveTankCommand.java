package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class DriveTankCommand extends TSafeCommand {
	
	double distance = 0; // in inches 
	double stopDistanceEncoderCounts = 0; // encoder counts to stop at
	double leftSpeed = 0;
	double rightSpeed = 0;
	boolean brakeWhenFinished = true;
	
	private final double STOPPING_ENCODER_COUNTS = 20; // to reduce stopping overshoot
	
	public DriveTankCommand(double distance, double leftSpeed, double rightSpeed, 
			double timeout, boolean brakeWhenFinished) {
		
		super(timeout);
		
		this.brakeWhenFinished = brakeWhenFinished;
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
		this.distance = distance;
		
		this.stopDistanceEncoderCounts = 
				distance * RobotConst.ENCODER_COUNTS_PER_INCH - STOPPING_ENCODER_COUNTS;
		
		
		//System.out.println(stopDistanceEncoderCounts);
	}
	
	protected void initialize() {
		//System.out.println("starting drive");
		Robot.chassisSubsystem.resetEncoders();
		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
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
	 protected void end() {	    	
			if (brakeWhenFinished) {
	    		Robot.chassisSubsystem.setSpeed(0, 0);
	    	}
	    	
	    }
}
