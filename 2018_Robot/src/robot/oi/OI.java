package robot.oi;

import com.torontocodingcollective.oi.TAxis;
import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TButtonPressDetector;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

import robot.RobotConst;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public AutoSelector autoSelector = new AutoSelector();

	private TGameController gameController = new TGameController_Logitech(0);
	private TGameController operatorController = new TGameController_Logitech(1);

	private TToggle pneumaticsToggle = new TToggle(gameController, TStick.LEFT);
	private TToggle pidToggle = new TToggle(gameController, TStick.RIGHT);
	
	private TButtonPressDetector elevatorUpButtonPress = 
			new TButtonPressDetector(operatorController,TButton.RIGHT_BUMPER);

	private TButtonPressDetector elevatorDownButtonPress = 
			new TButtonPressDetector(operatorController,TButton.LEFT_BUMPER);

	//Driver Controller
	public double getSpeed() {
		return -gameController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public double getTurn() {
		return gameController.getAxis(TStick.RIGHT, TAxis.X);
	}

	public boolean getForwardThrust() {
		return gameController.getButton(TButton.A);
	}

	public boolean getStartDriveDirection() {
		return gameController.getButton(TButton.B);
	}

	public int getArcCommand() {
		return gameController.getPOV();
	}

	public boolean getCancelCommand() {
		return gameController.getButton(TButton.BACK);
	}

	public boolean reset() {
		return gameController.getButton(TButton.START);
	}

	public boolean getTurboOn() {
		return gameController.getButton(TButton.LEFT_BUMPER);
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
	
	//Operator Controller
	public boolean getRampUp(char side) {
		if (side == RobotConst.LEFT && operatorController.getPOV() == 315) {
			return true;
		}
		if (side == RobotConst.RIGHT && operatorController.getPOV() == 45) {
			return true;
		}
		return false;
	}

	public boolean getRampDown(char side) {
		if (side == RobotConst.LEFT && operatorController.getPOV() == 225) {
			return true;
		}
		if (side == RobotConst.RIGHT && operatorController.getPOV() == 135) {
			return true;
		}
		return false;

	}
	
	public double getElevatorSpeed() {
		return - operatorController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public boolean getElevatorUp() {
		return elevatorUpButtonPress.get();
	}
	
	public boolean getElevatorDown() {
		return elevatorDownButtonPress.get();
	}

	/*
	 * Intake Buttons
	 */
	public double getForeArmIntake() {
		return gameController.getTrigger(TTrigger.LEFT);
	}

	public double getForeArmOuttake() {
		return gameController.getTrigger(TTrigger.RIGHT);
	}

	public boolean getEjectForward() {
		return gameController.getButton(TButton.Y);
	}

	public boolean getEjectRear() {
		return gameController.getButton(TButton.A);
	}

	public void updatePeriodic() {
		pneumaticsToggle.updatePeriodic();
		pidToggle.updatePeriodic();
	}


}
