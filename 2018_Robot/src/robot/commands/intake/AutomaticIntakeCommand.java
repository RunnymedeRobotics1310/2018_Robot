package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class AutomaticIntakeCommand extends TSafeCommand {	
	
	private enum State { FORWARD, INTAKE_DELAY, REVERSE, ELEVATE, FINISH };
	
	State state = State.FORWARD;
	double reverseStartTime = 0;
	double intakeStopDelayStartTime = 0;

	public AutomaticIntakeCommand() {
		requires(Robot.intakeSubsystem);
	}
	
	protected void initialize() {
		Robot.intakeSubsystem.intakeCube();
		Robot.intakeSubsystem.intakeClawOpen();
	}
	
	protected void execute() {

		// Allow the operator to adjust the tilt speed when the automatic intake\
		// command is running
		double intakeTiltSpeed = - Robot.oi.getIntakeTiltSpeed();

		if (intakeTiltSpeed > 0.1 && intakeTiltSpeed < 0.93) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(0.4 * intakeTiltSpeed + 0.2);
		} else if (intakeTiltSpeed < -0.1 && intakeTiltSpeed > -0.93) {
				Robot.intakeSubsystem.setIntakeTiltSpeed(0.4 * intakeTiltSpeed - 0.2);
		} else if (Math.abs(intakeTiltSpeed) > 0.93) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(1.0 * Math.signum(intakeTiltSpeed));
		} else {
			Robot.intakeSubsystem.setIntakeTiltSpeed(0);
		}

		switch (state) {
		case FORWARD:
			// If the motors go overcurrent then reverse the motors for 1 second
			// and try again on the intake
			if (Robot.powerSubsystem.getIntakeWheelMotorCurrent() > 2000) {
				reverseStartTime = timeSinceInitialized();
				state = State.REVERSE;
				Robot.intakeSubsystem.outtakeCube();
			}
			
			if (Robot.intakeSubsystem.isCubeDetected() || timeSinceInitialized() > 3) {
				Robot.intakeSubsystem.intakeClawClose();
				intakeStopDelayStartTime = timeSinceInitialized(); 
				state = State.INTAKE_DELAY;
			}
			break;
			
		case INTAKE_DELAY:
			if (timeSinceInitialized() > intakeStopDelayStartTime + 1) {
				Robot.intakeSubsystem.intakeStop();
				state = State.FINISH;
			}
			break;
			
		case REVERSE:
			if (timeSinceInitialized() > reverseStartTime + 2.0) {
				state = State.FORWARD;
				Robot.intakeSubsystem.intakeCube();
			}
			break;
			
		case ELEVATE:
			if (Robot.elevatorSubsystem.getLevel() <= 1) {
//				Scheduler.getInstance().add(new SetElevatorHeightCommand(1));
			}
			state = State.FINISH;
			break;
			
		default:
			break;
		}
	}
	
	protected void end(){
		Robot.intakeSubsystem.intakeClawClose();
		Robot.intakeSubsystem.intakeStop();
	}
	
	protected boolean isFinished() {
		if (Robot.oi.getAutomaticIntakeCancel()){
			return true;
		}
		if (super.isFinished()) {
			return true;
		}
		if (state == State.FINISH) {
			return true;
		}
		return false;
	}

}