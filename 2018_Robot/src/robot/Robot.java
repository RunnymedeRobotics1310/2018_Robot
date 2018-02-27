
package robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.commands.AutonomousCommand;
import robot.oi.GameData;
import robot.oi.OI;
import robot.subsystems.ChassisSubsystem;
import robot.subsystems.ElevatorSubsystem;
import robot.subsystems.IntakeSubsystem;
import robot.subsystems.PneumaticsSubsystem;
import robot.subsystems.RampSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
	public static final PneumaticsSubsystem pneumaticsSubsystem = new PneumaticsSubsystem();
	public static final RampSubsystem rampSubsystem = new RampSubsystem();
	public static final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
	public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
	public static OI oi;
	
	
	private Command autoCommand;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		oi.init();

		chassisSubsystem.init();
		pneumaticsSubsystem.init();
		rampSubsystem.init();
		elevatorSubsystem.init();
		intakeSubsystem.init();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updatePeriodic();
	}	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		// Initialize the game data
		GameData.init();

		// Turn on the drive pids
		Robot.oi.setSpeedPidToggle(true);
		chassisSubsystem.enableSpeedPids();
		
		// Reset the gyro and the encoders
		Robot.chassisSubsystem.setGyroAngle(0);
		Robot.chassisSubsystem.resetEncoders();
				
		// Initialize the robot command after initializing the game data
		// because the game data will be used in the auto command.
		autoCommand = new AutonomousCommand();
		autoCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		// Update the OI before running the commands
		oi.updatePeriodic();
		
		Scheduler.getInstance().run();

		// Update all subsystems after running commands
		updatePeriodic();
	}

	@Override
	public void teleopInit() {
		
		if (autoCommand != null) {
			autoCommand.cancel();
		}

		// Turn off the drive PIDs
		// Save the battery in teleop by using the 
		// SpeedController built in braking.
		Robot.oi.setSpeedPidToggle(false);
		chassisSubsystem.disableSpeedPids();

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		// Update the OI before running the commands
		oi.updatePeriodic();
		
		Scheduler.getInstance().run();
		
		// Update all subsystems after running commands
		updatePeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	
	private void updatePeriodic() {
		chassisSubsystem.updatePeriodic();
		pneumaticsSubsystem.updatePeriodic();
		rampSubsystem.updatePeriodic();
		elevatorSubsystem.updatePeriodic();
//		intakeSubsystem.updatePeriodic();
	}
}
