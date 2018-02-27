package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.drive.DriveDirectionCommand;
import robot.subsystems.ChassisSubsystem;

/**
 *
 */
public class DefaultChassisCommand extends Command {

	public DefaultChassisCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {


		if (Robot.oi.getTurboOn()) {
			Robot.chassisSubsystem.enableTurbo();
		}
		else {
			Robot.chassisSubsystem.disableTurbo();
		}

		if (Robot.oi.reset()){
			Robot.chassisSubsystem.resetGyroAngle();
			Robot.chassisSubsystem.resetEncoders();
		}

		if (Robot.oi.getSpeedPidEnabled()) {
			Robot.chassisSubsystem.enableSpeedPids();
		}
		else {
			Robot.chassisSubsystem.disableSpeedPids();
		}



		/*if (Robot.oi.getForwardThrust()) {
			Scheduler.getInstance().add(new DriveDistanceCommand(50, Robot.chassisSubsystem.getGryoAngle(), 0.5, 5.0, true));
		}

		if (Robot.oi.getStartDriveDirection()) {
			Scheduler.getInstance().add(new DriveDirectionCommand(0, .8, 10));
		}
		if (Robot.oi.getArcCommand() == 90){
			Scheduler.getInstance().add(new ArcCommand(200, Robot.chassisSubsystem.getGryoAngle(), Robot.chassisSubsystem.getGryoAngle() + 90, 1));
		}
		if (Robot.oi.getArcCommand() == 270){
			Scheduler.getInstance().add(new ArcCommand(200, Robot.chassisSubsystem.getGryoAngle(), Robot.chassisSubsystem.getGryoAngle() - 90, 1));
		}*/

		double speed = Robot.oi.getSpeed();
		double turn  = Robot.oi.getTurn();

		// Reduce the scale of the speed and turn at low 
		// values of the joystick
		double scaledSpeed = scaleValue(speed);
		double scaledTurn  = scaleValue(turn);

		double leftSpeed = 0;
		double rightSpeed = 0;

		//straight driving
		if (Math.abs(speed) > 0.1 && Math.abs(turn) < 0.1) {

			leftSpeed = scaledSpeed;
			rightSpeed = leftSpeed;
		}

		//straight turning
		if (Math.abs(turn) > 0.1 && Math.abs(speed) < 0.1) {
			leftSpeed = scaledTurn * .6;
			rightSpeed = -scaledTurn * .6;
		}

		// Blend speed and turn
		if ( speed > 0.1 && turn > 0.1) {
			leftSpeed = scaledSpeed;
			rightSpeed = scaledSpeed - (turn / 2);
		}

		if ( speed > 0.1 && turn < -0.1) {
			leftSpeed = scaledSpeed + (turn / 2);
			rightSpeed = scaledSpeed;
		}

		if ( speed < -0.1 && turn > 0.1) {
			leftSpeed = scaledSpeed+ (turn /2);
			rightSpeed = scaledSpeed ;
		}

		if ( speed < -0.1 && turn < -0.1) {
			leftSpeed = scaledSpeed ;
			rightSpeed = scaledSpeed - (turn / 2);
		}
		//System.out.println(speed);
		//System.out.println(turn);

		// automatic high gear
		if (Robot.oi.getTurboOn()) {

			//System.out.println(leftSpeed + " " + rightSpeed + " " + getEncoderSpeed()); 
			if (Math.abs(Robot.chassisSubsystem.getEncoderSpeed()) >= robot.RobotConst.MAX_LOW_GEAR_SPEED * 0.4 
				&& (   (leftSpeed >= 0 && rightSpeed >= 0) 
					|| (leftSpeed <= 0 && rightSpeed <= 0) ) ) { 
				Robot.chassisSubsystem.enableTurbo();
			}
			 

			if (Math.abs(Robot.chassisSubsystem.getEncoderSpeed()) <= robot.RobotConst.MAX_LOW_GEAR_SPEED * 0.2) {
				Robot.chassisSubsystem.disableTurbo(); 
				// System.out.println("disable turbo 1"); 
			} 
			if (! ((leftSpeed >= 0 && rightSpeed >= 0) 
				|| (leftSpeed <= 0 && rightSpeed <= 0))) { 
				Robot.chassisSubsystem.disableTurbo();
				//System.out.println("disable turbo 3"); 
			}

		}
		else {
			Robot.chassisSubsystem.disableTurbo(); 
		}


		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
	}


	// This routine scales a joystick value to make the 
	// acceleration and turning more smooth.  All values that are
	// less than 0.5 are cut in half, and values above 0.5 are
	// scaled to be from 0.25 to 1.0.
	private double scaleValue(double value) {

		double absValue = Math.abs(value);

		if (absValue < 0.1) { 
			return 0;
		}

		if (absValue < 0.5) {
			return value / 2;
		}

		// Follow a y=mx + b curve to scale inputs from
		// 0.5 to 1.0 to outputs of 0.25 to 1.0
		if (value > 0) {
			return 0.25 + (value-0.5) * 1.5;
		}

		return - 0.25 + (value+0.5) * 1.5; 
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
