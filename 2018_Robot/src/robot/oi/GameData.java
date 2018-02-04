package robot.oi;

import edu.wpi.first.wpilibj.DriverStation;

public class GameData {

	public static final char LEFT = 'L';
	public static final char RIGHT = 'R';
	
	private static char closeSwitch = LEFT;
	private static char scale       = LEFT;
	private static char farSwitch   = LEFT;
	
	/** 
	 * Initialize the Game Data based on information from the driver station.
	 * This method should be called during AutoInit.
	 * <p>
	 * Data is retrieved from the DriverStation console (under the gear/settings
	 * section).  
	 * <p>
	 * The expected input is a string of 3 characters that L or R.
	 * If a non-expected character is encountered, it is ignored.
	 * <p>
	 * The default switch setup for all switches is L = Left if there is no
	 * corresponding valid data in the DriverStation for that switch.
	 */
	public static void init() {
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.length() >= 1) {
			char c = gameData.charAt(0);
			if (c == 'L' || c == 'l') {
				closeSwitch = LEFT;
			}
			if (c == 'R' || c == 'r') {
				closeSwitch = RIGHT;
			}
		}
		
		if (gameData.length() >= 2) {
			char c = gameData.charAt(1);
			if (c == 'L' || c == 'l') {
				scale = LEFT;
			}
			if (c == 'R' || c == 'r') {
				scale = RIGHT;
			}
		}
		
		if (gameData.length() >= 3) {
			char c = gameData.charAt(2);
			if (c == 'L' || c == 'l') {
				farSwitch = LEFT;
			}
			if (c == 'R' || c == 'r') {
				farSwitch = RIGHT;
			}
		}
	}
	
	public static char getCloseSwitch() {
		return closeSwitch;
	}

	public static char getFarSwitch() {
		return farSwitch;
	}

	public static char getScale() {
		return scale;
	}

}
