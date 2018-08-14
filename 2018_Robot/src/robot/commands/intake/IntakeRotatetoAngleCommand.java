package robot.commands.intake;

import robot.Robot;
import robot.RobotConst;
import robot.commands.drive.TSafeCommand;

public class IntakeRotatetoAngleCommand extends TSafeCommand {

	int targetAngle;
	double currentAngle;
	double turnAngle;

	public IntakeRotatetoAngleCommand(int targetAngle){
		super(5);
		this.targetAngle = targetAngle;
		requires(Robot.intakeSubsystem);
	}
	public void initialize() {
		currentAngle = Robot.intakeSubsystem.getTiltEncoderCount()/RobotConst.INTAKE_TILT_COUNTS_PER_DEGREE;
		turnAngle = targetAngle - currentAngle;
		if (turnAngle > 0) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(0.7);
		}
		if (turnAngle < 0) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(-0.4);
		}
	}

	protected boolean isFinished() {
		if (super.isFinished()) {
			return true;
		}
		
		currentAngle = Robot.intakeSubsystem.getTiltEncoderCount()/RobotConst.INTAKE_TILT_COUNTS_PER_DEGREE;

		// Allow for a 3 degree overshoot
		if (turnAngle > 0 && currentAngle >= targetAngle-3) {
			return true;
		}
		else if (turnAngle < 0 && currentAngle <= targetAngle+3) {
			return true;
		}
		
		return false;
	}
	
	protected void end() {
		Robot.intakeSubsystem.setIntakeTiltSpeed(0);
	}
	
	protected void interrupted() {
	}
}
