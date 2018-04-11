package robot.commands.intake;

import robot.Robot;
import robot.RobotConst;
import robot.commands.drive.TSafeCommand;

public class IntakeRotatetoAngleCommand extends TSafeCommand {

	int targetAngle;
	int currentAngle;
	int turnAngle;

	public IntakeRotatetoAngleCommand(int targetAngle){
		super(1.25);
		this.targetAngle = targetAngle;
		requires(Robot.intakeSubsystem);
	}
	public void initialize() {
		currentAngle = (int) (Robot.intakeSubsystem.getTiltEncoderCount()/RobotConst.INTAKE_TILT_COUNTS_PER_DEGREE);
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
		currentAngle = (int) (Robot.intakeSubsystem.getTiltEncoderCount()/RobotConst.INTAKE_TILT_COUNTS_PER_DEGREE);
		if (turnAngle > 0 && currentAngle >= targetAngle) {
			return true;
		}
		else if (turnAngle < 0 && currentAngle <= targetAngle) {
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
