package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.commands.drive.AccelerateDistanceCommand;
import robot.commands.drive.ArcCommand;
import robot.commands.drive.BackupCommand;
import robot.commands.drive.DriveDistanceCommand;
import robot.commands.drive.RotateToAngleCommand;
import robot.commands.elevator.SetElevatorHeightCommand;
import robot.commands.intake.AutoCubeReleaseCommand;
import robot.commands.intake.AutomaticIntakeCommand;
import robot.commands.intake.IntakeRotatetoAngleCommand;
import robot.oi.AutoSelector;
import robot.oi.GameData;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

	public static final char LEFT 				= 'L';
	public static final char RIGHT 				= 'R';
	public static final char CENTER 			= 'C';
	public static final String ROBOT_LEFT   	= "Robot Left";
	public static final String ROBOT_CENTER 	= "Robot Center";
	public static final String ROBOT_RIGHT  	= "Robot Right";
	public static final String SWITCH 			= "Switch";
	public static final String SCALE 			= "Scale";
	public static final String CROSS  			= "Cross";
	public static final String CUBE 			= "Cube";
	public static final String CLOSE_SCALE_ONLY = "Close Scale";
	public static final String NONE  			= "None";
	public static final String EXCHANGE = "Exchange";

	public AutonomousCommand() {
		//getting info
		String robotStartPosition 	= AutoSelector.getRobotStartPosition();
		String firstAction 			= AutoSelector.getRobotFirstAction();
		String secondAction 		= AutoSelector.getRobotSecondAction();
		char closeSwitch 			= GameData.getCloseSwitch();
		char scale 					= GameData.getScale();


		// Print out the user selection and Game config for debug later
//		System.out.println("Auto Command Configuration");
//		System.out.println("--------------------------");
//		System.out.println("Robot Position : " + robotStartPosition);
//		System.out.println("First Action   : " + firstAction);
//		System.out.println("Second Action  : " + secondAction);
//		System.out.println("Close Switch   : " + closeSwitch);
//		System.out.println("Scale		   : " + scale);

		//overrides
		//System.out.println("Starting Overrides");

		if (robotStartPosition.equals(ROBOT_CENTER) && !firstAction.equals(SWITCH)) {
			firstAction = SWITCH;
			System.out.println("Center start must do switch as first action. Overriding first action to SWITCH");
		}

		//run the auto

		switch (robotStartPosition) {
		case ROBOT_LEFT:
			leftAuto(scale, closeSwitch, firstAction, secondAction);
			break;
		case ROBOT_CENTER:
			centerAuto(scale, closeSwitch, firstAction, secondAction);
			break;
		case ROBOT_RIGHT:
			rightAuto(scale, closeSwitch, firstAction, secondAction);
			break;
		}

	}

	private void leftAuto(char scale, char closeSwitch, String firstAction, String secondAction) {

		//first action
		if (firstAction.equals(SCALE)) {
			//scale action is selected
			if (scale == LEFT) {
				//scale is on the left side
				leftScaleLeft1();
			}
			else{
				//System.out.println("scale is on the right side");
				leftScaleRight1();
			}
		}
		else if (firstAction.equals(CLOSE_SCALE_ONLY)) {
			if (scale == LEFT) {
				//scale is on the left side
				leftScaleLeft1();
			}
			else {
				crossLine();
			}
		}
		else{
			//cross action is selected
			crossLine();
		}
		//System.out.println("Starting second action");
		if (secondAction.equals(SWITCH)) {
			//System.out.println("Switch action selected");
			if (closeSwitch == LEFT){
				if (scale == LEFT) {
					leftSwitchLeft2();
				}
			}
		}

		else if (secondAction.equals(SCALE)) {
			if (scale == LEFT){
				if (firstAction.equals(SCALE) || firstAction.equals(CLOSE_SCALE_ONLY)) {
					leftScaleLeft2();
				}
			}
		}

		else{
			System.out.println("No second action");
		}
	}

	private void rightAuto(char scale, char closeSwitch, String firstAction, String secondAction) {

		//first action
		if (firstAction.equals(SCALE)) {
			//scale action is selected

			if (scale == RIGHT){
				//scale is on the right side
				rightScaleRight1();
			}
			else{
				//scale is on the left side
				rightScaleLeft1();
			}
		}
		else if (firstAction.equals(CLOSE_SCALE_ONLY)) {
			if (scale == RIGHT) {
				//scale is on the left side
				rightScaleRight1();
			}
			else {
				crossLine();
			}
		}
		else{
			//cross action is selected
			crossLine();
		}

		//System.out.println("Starting second action");
		if (secondAction.equals(SWITCH)) {
			//System.out.println("Switch action selected");
			if (closeSwitch == RIGHT) {
				if (firstAction.equals(SCALE)) {
					if (scale == RIGHT) {
						rightSwitchRight2();
					}
				}
			}
		}
		else if (secondAction.equals(SCALE) || firstAction.equals(CLOSE_SCALE_ONLY)) {
			if (scale == RIGHT){
				rightScaleRight2();
			}
		}
		else{
			//System.out.println("No second action");
		}
	}

	private void centerAuto(char scale, char closeSwitch, String firstAction, String secondAction) {
		//System.out.println("Starting first action");

		//System.out.println("Switch action selected");
		if (closeSwitch == LEFT){
			//System.out.println("Executing left switch command");
			centerSwitchLeft1();
		}
		else{
			//System.out.println("Executing right switch command");	
			centerSwitchRight1();
		}

		//System.out.println("Starting second action");
		if (secondAction.equals(CUBE)) {
			if (closeSwitch == LEFT) {
				leftGetPowerCube();

			}
			else {
				rightGetPowerCube();
			}
		} else if (secondAction.equals(EXCHANGE)) {
			
		}
	}

	//first action methods 
	// pattern methods name = (Start Position*)(Destination*)(Side)(Action Number)
	// * = mandatory name parameter

	//left side start

	private void leftScaleLeft1(){
		addParallel(new SetElevatorHeightCommand(3));		
		addParallel(new IntakeRotatetoAngleCommand(0));
		addSequential(new AccelerateDistanceCommand(200, 0, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(120, 0, 40, 0.4, true));
		addSequential(new AutoCubeReleaseCommand());
		addSequential(new BackupCommand(15));
		addParallel(new SetElevatorHeightCommand(0));
		addSequential(new RotateToAngleCommand(140, 0.5)); //150
		addParallel(new DriveDistanceCommand(50, 150, 0.5, 7.0, false)); //54 og dist
		addSequential(new AutomaticIntakeCommand());
		addSequential(new BackupCommand(5));

	}

	private void leftScaleRight1(){
		addParallel(new SetElevatorHeightCommand(2));
		addParallel(new IntakeRotatetoAngleCommand(0));
		addSequential(new AccelerateDistanceCommand(175, 0, 1.0, 3.0, false));
		addSequential(new ArcCommand(100, 0, 85, 0.5, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new DriveDistanceCommand(110, 90, 1.0, 5.0, false));
		addSequential(new ArcCommand(100, 90, 5, 0.5, true));
		addSequential(new DriveDistanceCommand(10, 0, 0.5, 5.0, false));
		addSequential(new AutoCubeReleaseCommand());
	}

	//right side start
	private void rightScaleLeft1(){
		addParallel(new SetElevatorHeightCommand(2));
		addParallel(new IntakeRotatetoAngleCommand(0));
		addSequential(new AccelerateDistanceCommand(175, 0, 1.0, 3.0, false));
		addSequential(new ArcCommand(100, 0, 275, 0.5, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new DriveDistanceCommand(110, 270, 1.0, 5.0, false));
		addSequential(new ArcCommand(100, 270, 5, 0.5, true));
		addSequential(new DriveDistanceCommand(10, 0, 0.5, 5.0, false));
		addSequential(new AutoCubeReleaseCommand());

	}

	private void rightScaleRight1(){
		addParallel(new SetElevatorHeightCommand(3));
		addParallel(new IntakeRotatetoAngleCommand(0));
		addSequential(new AccelerateDistanceCommand(200, 0, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(100, 0, 320, 0.4, true));
		addSequential(new AutoCubeReleaseCommand());
		addSequential(new BackupCommand(10));
		addParallel(new SetElevatorHeightCommand(0));
		addSequential(new RotateToAngleCommand(230, 0.5));
		addSequential(new DriveDistanceCommand(22, 230, 0.4, 7.0, false));
		addParallel(new ArcCommand(85, 230, 180, 0.4, true));
		addSequential(new AutomaticIntakeCommand());
		addSequential(new BackupCommand(5));
	}

	//center start
	private void centerSwitchLeft1(){
		addParallel(new SetElevatorHeightCommand(2));
		addParallel(new IntakeRotatetoAngleCommand(0));
		addSequential(new ArcCommand(85, 0, 310, 0.8, false));
		addSequential(new ArcCommand(85, 310, 0, 0.8, false));
		addSequential(new DriveDistanceCommand(20, 0, 0.8, 7.0, false));
		addSequential(new AutoCubeReleaseCommand());
	}
	private void centerSwitchRight1(){
		addParallel(new SetElevatorHeightCommand(2));
		addParallel(new IntakeRotatetoAngleCommand(0));
		addSequential(new ArcCommand(80, 0, 45,0.8, false));
		addSequential(new DriveDistanceCommand(3, 45, 0.8, 3.0, false));
		addSequential(new ArcCommand(110, 45, 0, 0.8, false));
		addSequential(new DriveDistanceCommand(20, 0, 0.8, 7.0, false));
		addSequential(new AutoCubeReleaseCommand());
	}

	//universal
	private void crossLine(){
		addSequential(new DriveDistanceCommand(200, 0, 1.0, 3.0, false));
	}

	//second action methods
	// pattern methods name = (Side going into second action*)(Destination*)(Side)(Action Number)
	// * = mandatory name parameter

	//left side
	public void leftSwitchRight2(){
		leftScaleLeft2();
//		addSequential(new ArcCommand(100, 0, 80, 1.0, false));
//		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
//		addSequential(new ArcCommand(100, 0, 170, 1.0, true));
//		addSequential(new AutoCubeReleaseCommand());
	}
	public void leftSwitchLeft2(){
		addSequential(new SetElevatorHeightCommand(2));
		addSequential(new DriveDistanceCommand(10, 150, 0.3, 7.0, false));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void leftScaleRight2(){
		addSequential(new ArcCommand(100, 0, 80, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(100, 80, 10, 1.0, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void leftScaleLeft2(){
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new RotateToAngleCommand(0, 0.5));
		addParallel(new ArcCommand(50, 0, 15, 0.5, true));
		addSequential(new AutoCubeReleaseCommand());
	}

	//right side
	public void rightSwitchRight2(){
		addSequential(new SetElevatorHeightCommand(2));
		addSequential(new RotateToAngleCommand(200, 0.5));
		addSequential(new DriveDistanceCommand(10, 200, 0.3, 7.0, false));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void rightSwitchLeft2(){
		rightScaleRight2();
//		addSequential(new ArcCommand(100, 0, 280, 1.0, false));
//		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
//		addSequential(new ArcCommand(100, 0, 190, 1.0, true));
//		addSequential(new AutoCubeReleaseCommand());
	}
	public void rightScaleRight2(){	
		addSequential(new BackupCommand(10));
		addSequential(new RotateToAngleCommand(45, 0.5));
		addSequential(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(150, 45, 350, 0.4, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void rightScaleLeft2(){

		addSequential(new ArcCommand(100, 0, 280, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 280, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(100, 280, 350, 1.0, true));
	}

	//Center
	public void leftGetPowerCube() {
		addSequential(new BackupCommand(10));
		addSequential(new SetElevatorHeightCommand(0));
		addSequential(new RotateToAngleCommand(80, 0.5));
		addParallel(new AutomaticIntakeCommand());
		addSequential(new ArcCommand(60, 90, 70, 0.5, false));
	}
	public void rightGetPowerCube() {
		addSequential(new BackupCommand(10));
		addSequential(new SetElevatorHeightCommand(0));
		addSequential(new RotateToAngleCommand(280, 0.5));
		addParallel(new AutomaticIntakeCommand());
		addSequential(new ArcCommand(60, 270, 290, 0.5, false));
	}
}
