package robot.oi;

import com.torontocodingcollective.oi.TAxis;
import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TButtonPressDetector;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TPOVPressDetector;
import com.torontocodingcollective.oi.TRumbleManager;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


/**Driver Controller
 * 	Sticks:
 * 		Right Stick X-axis 	= Drive Motor Turn 
 * 		Left Stick Y-axis  	= Drive Motor Speed
 * 		Right Stick Press  	= Toggle PIDs
 * 		Left Stick Press 	= Toggle Compressor
 * 	Buttons:
 * 		Start Button 		= Reset Encoders and Gyro 
 * 		Back Button 		= Cancel any Command
 * 		X Button			= Automatic intake
 * 		B Button			= Automatic intake cancel
 * 		A Button			= Climb arm up
 * 		Y Button			= Climb arm down
 * 
 * 	Bumpers/Triggers:
 *      Right Bumper        = Intake Cube
 *      Right Trigger       = Outtake Cube
 * 		Left Bumper			= High Gear
 *      Left Trigger        = Intake Open
 *  
 *  POV:
 *  	Any Angle			= Rotate to the Pressed Angle
 * 
 * Operator Controller
 * 	Sticks:
 * 		Left Stick Y-axis  	= Elevator Motor Speed (Manual Control)
 * 		Right Stick Y-Axis  = Manual Intake Tilt Control
 * 
 * 	Buttons:
 * 		Y Button 			= Move Elevator to Switch Level
 * 		A Button 			= Set Intake Tilt Angle to 0
 * 		X Button			= Set Intake Tilt Angle to 45
 * 		B Button			= Set Intake Tilt Angle to 90
 * 		start button		= Winch up
 * 		back button			= Winch down
 * 
 * 
 * 	Bumpers/Triggers:
 * 		Right Bumper		= Open Intake
 * 		Right Trigger		= Eject Cube
 * 		Left Bumper 		= Intake Cube
 *	POV
 *		0					= Move Elevator Up One Level
 *		180					= Move Elevator Down One Level
 *
 *
 */
public class OI {

	public AutoSelector autoSelector = new AutoSelector();

	private TGameController driverController = new TGameController_Logitech(0);
	private TGameController operatorController = new TGameController_Logitech(1);

	public TRumbleManager driverRumble = new TRumbleManager("Driver", driverController);


	private TToggle pneumaticsToggle = new TToggle(driverController, TStick.LEFT);
	private TToggle pidToggle = new TToggle(driverController, TStick.RIGHT);
	private TToggle rampRelease = new TToggle(operatorController, TButton.X);

	private TPOVPressDetector elevatorButtonPress = 
			new TPOVPressDetector(operatorController);

	public void init() {
		pneumaticsToggle.set(true);
	}

	//Driver Controller
	public double getSpeed() {
		return - driverController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public double getTurn() {
		return driverController.getAxis(TStick.RIGHT, TAxis.X);
	}

	public boolean getAutomaticIntake() {
		return driverController.getButton(TButton.X);
	}
	public boolean getAutomaticIntakeCancel() {
		return driverController.getButton(TButton.B);
	}
	public boolean getClimbArmUp() {
		return driverController.getButton(TButton.A);
	}
	public boolean getClimbArmDown() {
		return driverController.getButton(TButton.Y);
	}

	public boolean getCancelCommand() {
		return driverController.getButton(TButton.BACK);
	}

	public boolean reset() {
		return driverController.getButton(TButton.START);
	}

	public int getPov() {
		return driverController.getPOV();
	}

	public boolean getTurboOn() {
		return driverController.getButton(TButton.LEFT_BUMPER);
	}

	public boolean getCompressorEnabled() {
		return pneumaticsToggle.get();
	}

	public boolean getSpeedPidEnabled() {
		return pidToggle.get();
	}

	public void setSpeedPidToggle(boolean state) {
		pidToggle.set(state);
	}

	public boolean getIntakeCube() {
		return driverController.getButton(TButton.RIGHT_BUMPER) || operatorController.getButton(TButton.LEFT_BUMPER);
	}

	public double getOuttakeCube() {
		return Math.max(driverController.getTrigger(TTrigger.RIGHT), operatorController.getTrigger(TTrigger.RIGHT));
	}

	public double getOuttakeCubeOP() {
		return operatorController.getTrigger(TTrigger.LEFT);
	}
	public boolean getClawOpen() {
		return operatorController.getButton(TButton.RIGHT_BUMPER) || driverController.getButton(TTrigger.LEFT); 
	}

	//Operator Controller

	public double getElevatorSpeed() {
		return - operatorController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public int getElevatorMove() {
		return elevatorButtonPress.get();
	}

	public boolean getElevatorSwitch() {
		return operatorController.getButton(TButton.Y);
	}

	public double getIntakeTiltSpeed() {
		return - operatorController.getAxis(TStick.RIGHT, TAxis.Y);
	}

	public boolean getTiltArmUp() {
		return operatorController.getButton(TButton.B);
	}

	public boolean getTiltArm45() {
		return operatorController.getButton(TButton.X);
	}

	public boolean getTiltArmDown() {
		return operatorController.getButton(TButton.A);
	}
	
	public boolean getWinchUp() {
		return operatorController.getButton(TButton.START);
	}
	
	public boolean getWinchDown() {
		return operatorController.getButton(TButton.BACK);
	}

	public void updatePeriodic() {
		pneumaticsToggle.updatePeriodic();
		pidToggle.updatePeriodic();
		rampRelease.updatePeriodic();
		driverRumble.updatePeriodic();
	}


}
