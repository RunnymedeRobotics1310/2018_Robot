package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class BackupCommand extends TSafeCommand {
	
	private final double distanceEncoderCounts;
	private final double speed;
	
	public BackupCommand(double distance) {
		this(distance, -.25);
	}
		
	public BackupCommand(double distance, double speed) {
			
		this.distanceEncoderCounts =  
				Math.abs(distance) * RobotConst.ENCODER_COUNTS_PER_INCH;
		this.speed = - Math.abs(speed);
	}
	
	protected void initialize() {
		super.initialize();
		Robot.chassisSubsystem.resetEncoders();
		Robot.chassisSubsystem.setSpeed(speed, speed);
	}
	
	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}
	
		if (Robot.chassisSubsystem.getEncoderDistance() < -distanceEncoderCounts) {
			return true;
		}
		
		return false;
	}
}
