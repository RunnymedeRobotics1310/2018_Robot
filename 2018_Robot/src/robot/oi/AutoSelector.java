package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSelector {

	public static SendableChooser<String> robotStartPosition;
	public static SendableChooser<String> robotFirstAction;
	public static SendableChooser<String> robotSecondAction;

	public static final String ROBOT_LEFT = "Robot Left";
	public static final String ROBOT_CENTER = "Robot Center";
	public static final String ROBOT_RIGHT = "Robot Right";
	public static final String SWITCH = "Switch";
	public static final String SCALE = "Scale";
	public static final String CROSS = "Cross";
	public static final String NONE = "None";

	static {
		
		

		// Robot Position Optionsh
		robotStartPosition = new SendableChooser<String>();
		robotStartPosition.addObject(ROBOT_LEFT, ROBOT_LEFT);
		robotStartPosition.addDefault(ROBOT_CENTER, ROBOT_CENTER);
		robotStartPosition.addObject(ROBOT_RIGHT, ROBOT_RIGHT);

		SmartDashboard.putData("Robot Start", robotStartPosition);

		// First Auto action
		robotFirstAction = new SendableChooser<String>();
		robotFirstAction.addDefault(SWITCH, SWITCH);
		robotFirstAction.addObject(SCALE, SCALE);
		robotFirstAction.addObject(CROSS, CROSS);

		SmartDashboard.putData("First Action", robotFirstAction);

		// Second Auto Action
		robotSecondAction = new SendableChooser<String>();
		robotSecondAction.addObject(SWITCH, SWITCH);
		robotSecondAction.addObject(SCALE, SCALE);
		robotSecondAction.addDefault(NONE, NONE);

		SmartDashboard.putData("Second Action", robotSecondAction);

	}

	/**
	 * Get the robot starting position on the field.
	 * 
	 * @return 'L' for left, 'R' for right or 'C' for center
	 */
	public static String getRobotStartPosition() {

		return robotStartPosition.getSelected();
	}

	public static String getRobotFirstAction() {

		return robotFirstAction.getSelected();
	}

	public static String getRobotSecondAction() {

		if (robotFirstAction.getSelected() == CROSS) {
			return NONE;
		}
		return robotSecondAction.getSelected();
	}

	public static void updatePeriodic() {

		SmartDashboard.putString("Selected Robot Position", String.valueOf(getRobotStartPosition()));
	}
}
