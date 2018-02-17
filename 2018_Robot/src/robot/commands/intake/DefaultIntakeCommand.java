package robot.commands.intake;

import javax.net.ssl.ExtendedSSLSession;

import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TTrigger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;

/**
 *
 */
public class DefaultIntakeCommand extends Command {

	public DefaultIntakeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

		// Open the forearms and start intaking once the forearm is open
		// if (Robot.oi.getIntakeForeArm() > 0.1) {
		// Robot.intakeSubsystem.openForeArms();
		//// Robot.intakeSubsystem.intakeCube(0.5);
		//
		// // Close the forearms once you choose to close it
		// } else if (Robot.oi.getOuttakeForeArm() > 0.1) {
		//
		// Robot.intakeSubsystem.closeForeArms();
		// }

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		double speed = Robot.oi.getIntakeForeArm();
		

		SmartDashboard.putNumber("Intake speed", speed);
		
		if (speed > 0.1 || speed < 0.1) {
			Robot.intakeSubsystem.openForeArms(speed);
		} else {
			Robot.intakeSubsystem.stopClawMotors();
		}

		
//		if (Robot.oi.getIntakeEjectForward()) {
//			Robot.intakeSubsystem.intakeCube(1.0);
//		} else {
//			Robot.intakeSubsystem.stopIntake();
//		}
//		
//		
//		if (Robot.oi.getIntakeEjectBackward()) {
//			Robot.intakeSubsystem._intakeCube(1.0);
//		} else {
//			Robot.intakeSubsystem.stopIntake();
//		}
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
