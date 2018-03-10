package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class ArcCommand extends TSafeCommand {
	
	private double dist;
	private double startDirection;
	private double endDirection;
	private double turnangle;
	private double rWidth = 69.69125;//cm
	private double rSpeed;
	private double lSpeed;
	private double speed;
	private boolean brake;
	private boolean stopAtDistance;
	
	public ArcCommand(double dist, double startDirection, double endDirection, double speed, boolean brake) {
		this(dist, startDirection, endDirection, speed, brake, false);
	}
	public ArcCommand(double dist, double startDirection, double endDirection, double speed, boolean brake,
			boolean stopAtDistance) {
		super(15);
		this.brake = brake;
		this.dist = dist;
		this.startDirection = startDirection;
		this.endDirection = endDirection;
		this.speed = speed;
		this.turnangle = (this.endDirection - this.startDirection);
		if(this.turnangle > 180){
			this.turnangle -= 360;
		}
		else if(this.turnangle < -180) {
			this.turnangle += 360;
		}
		if (this.endDirection < 0) {
			this.endDirection += 360;
		}
		this.stopAtDistance = stopAtDistance;
		
		//System.out.println(this.turnangle);
		//System.out.println(this.endDirection);
		requires(Robot.chassisSubsystem);
	}
	
	public void initialize() {
		
		Robot.chassisSubsystem.resetEncoders();
		
		double radius = this.dist/Math.abs(Math.toRadians(this.turnangle));
		double fastSpeed = this.speed;
		double slowSpeed = ((radius-(this.rWidth/2))* Math.toRadians(this.turnangle)) / 
				((radius+(this.rWidth/2))* Math.toRadians(this.turnangle)) * this.speed;
		
		//System.out.println((radius-(this.rWidth/2))* Math.toRadians(this.turnangle)); 
		//System.out.println((radius+(this.rWidth/2))* Math.toRadians(this.turnangle));
		//System.out.println(fastSpeed);
		//System.out.println(slowSpeed);
		if (this.turnangle < 0) {
			this.lSpeed = slowSpeed;
			this.rSpeed = fastSpeed;
		}
		else if (this.turnangle > 0) {
			this.lSpeed = fastSpeed;
			this.rSpeed = slowSpeed;
		}
		//System.out.println(this.rSpeed); 
		//System.out.println(this.lSpeed);
		Robot.chassisSubsystem.setSpeed(lSpeed, rSpeed);
	}
	protected void end() {
		if (brake) {
			Robot.chassisSubsystem.setSpeed(0, 0);
		}
	}
	
	protected boolean isFinished() {
		
		if (super.isFinished()) {
			return true;
		}
		
		// If stopAtDistance, make sure the distance is complete
		if (stopAtDistance) {
			double encoderDistance = Robot.chassisSubsystem.getEncoderDistance();
			// Note: this command runs in centimeters instead of inches?
			if (encoderDistance * RobotConst.ELEVATOR_ENCODER_COUNTS_PER_INCH * 2.54 > this.dist) {
				return true;
			}
		}
		else {
			double error = this.endDirection - Robot.chassisSubsystem.getGryoAngle();
			
			if (Robot.chassisSubsystem.isTurboEnabled()) {
				if (this.turnangle < 0) {
					error += 26;
				}
				else if (this.turnangle > 0) {
					error -= 50;
				}
			}
			else {
				if (this.turnangle < 0) {
					error += 2;
				}
				else if (this.turnangle > 0) {
					error -= 2;
				}
			}
	
			if (error > 180) {
				error -= 360;
			}
			else if (error < -180) {
				error += 360;
			}
			
			if (Math.abs(error) <= 2) {
				return true;
			}
		}
		return false;
	}

}
