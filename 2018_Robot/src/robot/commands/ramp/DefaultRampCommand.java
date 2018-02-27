package robot.commands.ramp;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;



public class DefaultRampCommand extends Command{

	public DefaultRampCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.rampSubsystem);
	}

	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.oi.getRampRelease()) {
			System.out.println("Release Ramp");
			double time;
			time = DriverStation.getInstance().getMatchTime();
//			if (time <= 30) {
				Robot.rampSubsystem.setRampRelease(true);
//			}
//			else {
//				System.out.println("Cannot release ramps at time: " + time);
//			}
		}
		else {
			Robot.rampSubsystem.setRampRelease(false);
		}

		if (Robot.oi.getRampUp(RobotConst.LEFT)) {
			Robot.rampSubsystem.setLeftRampSpeed(0.9);
		}
		else if (Robot.oi.getRampDown(RobotConst.LEFT)) {
			Robot.rampSubsystem.setLeftRampSpeed(-0.3);
		}
		else {
			Robot.rampSubsystem.setLeftRampSpeed(0);
		}
		
		
		if (Robot.oi.getRampUp(RobotConst.RIGHT)) {
			Robot.rampSubsystem.setRightRampSpeed(0.9);
		}
		else if (Robot.oi.getRampDown(RobotConst.RIGHT)) {
			Robot.rampSubsystem.setRightRampSpeed(-0.3);
		}
		else {
			Robot.rampSubsystem.setRightRampSpeed(0);
		}
		
		if (Math.abs(Robot.oi.getLeftRampRearAdjust()) > 0.05) {
			Robot.rampSubsystem.adjustLeftRearRamp(Robot.oi.getLeftRampRearAdjust());
		}

		if (Math.abs(Robot.oi.getRightRampRearAdjust()) > 0.05) {
			Robot.rampSubsystem.adjustRightRearRamp(Robot.oi.getRightRampRearAdjust());
		}
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
