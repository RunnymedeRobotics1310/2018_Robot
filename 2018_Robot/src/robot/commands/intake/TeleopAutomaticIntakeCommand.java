package robot.commands.intake;

import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.drive.TSafeCommand;
import robot.commands.elevator.SetElevatorHeightCommand;

public class TeleopAutomaticIntakeCommand extends TSafeCommand {	
	
	private enum State { FORWARD, REVERSE, ELEVATE, FINISH };
	
	State state = State.FORWARD;
	double reverseStartTime = 0;

	public TeleopAutomaticIntakeCommand() {
		requires(Robot.intakeSubsystem);
	}
	
	protected void initialize() {
		Robot.intakeSubsystem.intakeCube();
		Robot.intakeSubsystem.intakeClawOpen();
		Robot.oi.driverRumble.rumbleOn();
	}
	
	protected void execute() {

		switch (state) {
		case FORWARD:
			// If the motors go overcurrent then reverse the motors for 1 second
			// and try again on the intake
			if (Robot.powerSubsystem.getIntakeWheelMotorCurrent() > 2000) {
				reverseStartTime = timeSinceInitialized();
				state = State.REVERSE;
				Robot.intakeSubsystem.outtakeCube();
			}
			
			if (Robot.intakeSubsystem.isCubeDetected()) {
				Robot.oi.driverRumble.rumbleOff();
				Robot.intakeSubsystem.intakeStop();
				Robot.intakeSubsystem.intakeClawClose();
				state = State.ELEVATE;
			}
			break;

		case REVERSE:
			if (timeSinceInitialized() > reverseStartTime + 2.0) {
				state = State.FORWARD;
				Robot.intakeSubsystem.intakeCube();
			}
			break;
			
		case ELEVATE:
			if (Robot.elevatorSubsystem.getLevel() <= 2) {
				Scheduler.getInstance().add(new SetElevatorHeightCommand(2));
			}
			state = State.FINISH;
			break;
			
		default:
			break;
		}
	}
	
	protected void end(){
		Robot.oi.driverRumble.rumbleOff();
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