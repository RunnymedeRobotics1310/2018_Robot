package robot.commands.drive;

import com.torontocodingcollective.sensors.ultrasonic.TUltrasonicSensor;

import robot.Robot;
import robot.RobotConst.Direction;

/**
 *
 */
public class DriveToUltrasonicCommand extends DriveDirectionCommand {

	private static final double STOPPING_DISTANCE = 1.0; // Stopping distance in inches

	private final double stoppingDistance;
	private final TUltrasonicSensor ultrasonicSensor;

	private Direction direction;

	public DriveToUltrasonicCommand(double angle, double speed, double stoppingDistance, TUltrasonicSensor ultrasonicSensor) {
		super(angle, speed, 15);
		this.ultrasonicSensor = ultrasonicSensor;
		this.stoppingDistance = stoppingDistance;
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

		super.initialize();

		// Where am I now?
		double currentDistance = ultrasonicSensor.getDistance();

		direction = currentDistance >= stoppingDistance ? Direction.FORWARD : Direction.BACKWARD;
//		super.setDir(direction);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double currentDistance = ultrasonicSensor.getDistance();

		
		switch (direction) {

		case FORWARD:

			if (currentDistance <= stoppingDistance + STOPPING_DISTANCE) {
				return true;
			}
			break;

		case BACKWARD:

			if (currentDistance >= stoppingDistance - STOPPING_DISTANCE) {
				return true;
			}
			break;

		default:
			break;
		}

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
