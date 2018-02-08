package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.motion.profile.Path;
import robot.motion.profile.PathDescriptor;
import robot.motion.profile.Point;


public class MotionProfileCommand extends Command {

	public Path path;
	public double velocity, time = 0;

	public MotionProfileCommand(double velocity) {

		this.velocity = velocity;
		// Use requires() here to declare subsystem dependencies
//		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

		// Now we’ll try some ‘noisy’ data
		double[] xPts = { 1.8, 7.7, 15, 20.5};
		double[] yPts = new double[] { 7.6, 10.4, 12, 17.1};

		PathDescriptor testPath = new PathDescriptor(Point.getPointSet(4, xPts, yPts), 10);

		path = new Path(testPath);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		double leftSpeed = path.getLeftMotorSpeed(this.time, velocity);
		double rightSpeed = path.getRightMotorSpeed(this.time, velocity);

		System.out.println("Left speed: " + leftSpeed);
		System.out.println("Right Speed" + rightSpeed);


		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
		this.time += 0.20;
		

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return this.time >= path.timeMax;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.chassisSubsystem.setSpeed(0,0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
