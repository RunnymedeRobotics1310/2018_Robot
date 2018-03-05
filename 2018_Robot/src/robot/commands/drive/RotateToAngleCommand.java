package robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;

public class RotateToAngleCommand extends TSafeCommand {
	public double speed = 0.0;
	public double endDirection = 0;
	public double startDirection = 0;
	public double turnangle = 0;

	public RotateToAngleCommand(double endDirection, double speed, double timeout) {
		super(timeout);
		this.endDirection = endDirection;
		if (this.endDirection < 0) {
			this.endDirection += 360;
		}
		this.speed = speed;
		requires(Robot.chassisSubsystem);
	}
	
	public RotateToAngleCommand(double endDirection, double speed) {
		this(endDirection, speed, 15);
	}

	public void initialize() {
		this.startDirection = Robot.chassisSubsystem.getGryoAngle();
		this.turnangle = (this.endDirection - this.startDirection);

		if (this.turnangle > 180) {
			this.turnangle -= 360;
		} else if (this.turnangle < -180) {
			this.turnangle += 360;
		}

		SmartDashboard.putNumber("turn angle", turnangle);

		if (isFinished()) { return; }  // If already at the end angle
		
		if (this.turnangle < 0) {
			Robot.chassisSubsystem.setSpeed(-speed, speed);
		} else {
			Robot.chassisSubsystem.setSpeed(speed, -speed);
		}
	}

	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}

		double error = this.endDirection - Robot.chassisSubsystem.getGryoAngle();

		/*if (this.turnangle < 0) {
			error += 2;
		} else if (this.turnangle > 0) {
			error -= 2;
		}/**/

		if (error > 180) {
			error -= 360;
		} else if (error < -180) {
			error += 360;
		}

		if (Math.abs(error) <= 5) {
			return true;
		}
		
		System.out.println(error);
		return false;
	}

}
