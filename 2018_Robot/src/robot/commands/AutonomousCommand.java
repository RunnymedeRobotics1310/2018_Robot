package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.commands.drive.ArcCommand;
import robot.commands.drive.DriveDistanceCommand;
import robot.commands.drive.RotateToAngleCommand;
import robot.commands.elevator.SetElevatorHeightCommand;
import robot.commands.intake.AutoCubeReleaseCommand;
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
	public static final String NONE  			= "None";

	public AutonomousCommand() {
		//getting info
		String robotStartPosition 	= AutoSelector.getRobotStartPosition();
		String firstAction 			= AutoSelector.getRobotFirstAction();
		String secondAction 		= AutoSelector.getRobotSecondAction();
		char closeSwitch 			= GameData.getCloseSwitch();
		char scale 					= GameData.getScale();


		// Print out the user selection and Game config for debug later
		System.out.println("Auto Command Configuration");
		System.out.println("--------------------------");
		System.out.println("Robot Position : " + robotStartPosition);
		System.out.println("First Action   : " + firstAction);
		System.out.println("Second Action  : " + secondAction);
		System.out.println("Close Switch   : " + closeSwitch);
		System.out.println("Scale		   : " + scale);

		//overrides
		//System.out.println("Starting Overrides");

		if (robotStartPosition.equals(ROBOT_CENTER) && !firstAction.equals(SWITCH)) {
			firstAction = SWITCH;
			System.out.println("Center start must do switch as first action. Overriding first action to SWITCH");
		}
		if (robotStartPosition.equals(ROBOT_RIGHT) && firstAction.equals(SWITCH) && closeSwitch == LEFT) {
			firstAction = CROSS;
			System.out.println("Switch is not on our side.  Overriding first action to CROSS");
		}
		if (robotStartPosition.equals(ROBOT_LEFT) && firstAction.equals(SWITCH) && closeSwitch == RIGHT){
			firstAction = CROSS;
			System.out.println("Switch is not on our side. Overriding first action to CROSS");
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
		if (firstAction.equals(SWITCH)) {
			//switch action is selected
			leftSwitch1();
		}
		else if (firstAction.equals(SCALE)) {
			//scale action is selected
			if (scale == LEFT){
				//scale is on the left side
				leftScaleLeft1();
			}
			else{
				//System.out.println("scale is on the right side");
				leftScaleRight1();
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
				if (firstAction.equals(SCALE)) {
					if (scale == RIGHT) {
						scaleRightSwitchLeft2();
					}
					else {
						scaleLeftSwitchLeft2();
					}
				}
				else {
					leftSwitchLeft2();
				}				
			}
			else{
				if (firstAction.equals(SCALE)) {
					if (scale == RIGHT) {
						scaleRightSwitchRight2();
					}
					else {
						scaleLeftSwitchRight2();
					}
				}
				else {
					leftSwitchRight2();
				}
			}
		}

		else if (secondAction.equals(SCALE)) {
			if (scale == LEFT){
				if (firstAction.equals(SCALE)) {
					scaleLeftScaleLeft2();
				}
				else {
					leftScaleLeft2();
				}
			}
			else{
				if (firstAction.equals(SCALE)) {
					scaleRightScaleRight2();
				}
				else {
					leftScaleRight2();
				}
			}
		}
		else{
			System.out.println("No second action");
		}
	}

	private void rightAuto(char scale, char closeSwitch, String firstAction, String secondAction) {

		//first action
		if (firstAction.equals(SWITCH)) {
			//switch action is selected
			rightSwitch1();
		}
		else if (firstAction.equals(SCALE)) {
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
						scaleRightSwitchRight2();
					}
					else {
						scaleLeftSwitchRight2();
					}
				}
				else {
					rightSwitchRight2();
				}
			}
			else{
				if (firstAction.equals(SCALE)) {
					if (scale == RIGHT) {
						scaleRightSwitchRight2();
					}
					else {
						scaleLeftSwitchRight2();
					}
				}
				else {
					rightSwitchLeft2();
				}
			}
		}
		else if (secondAction.equals(SCALE)) {
			if (scale == RIGHT){
				if (firstAction.equals(SCALE)) {
					scaleRightScaleRight2();
				}
				else {
					rightScaleRight2();
				}
			}
			else{
				if (firstAction.equals(SCALE)) {
					scaleLeftScaleLeft2();
				}
				else {
					rightScaleLeft2();
				}
			}
		}
		else{
			//System.out.println("No second action");
		}
	}

	private void centerAuto(char scale, char closeSwitch, String firstAction, String secondAction) {
		//System.out.println("Starting first action");
		if (firstAction.equals(SWITCH)) {
			//System.out.println("Switch action selected");
			if (closeSwitch == LEFT){
				//System.out.println("Executing left switch command");
				centerSwitchLeft1();
			}
			else{
				//System.out.println("Executing right switch command");	
				centerSwitchRight1();
			}
		}

		//System.out.println("Starting second action");
		if (secondAction.equals(SWITCH)) {
			//System.out.println("Switch action selected");
			if (closeSwitch == LEFT){
				//System.out.println("Executing left switch command");
				if (firstAction.equals(SCALE)) {
					scaleLeftSwitchLeft2();
				}
				else {
					leftSwitchLeft2();
				}
			}
			else{
				//System.out.println("Executing right switch command");	
				if (firstAction.equals(SCALE)) {
					scaleLeftSwitchRight2();
				}
				else {
					leftSwitchRight2();
				}
			}
		}
		else if (secondAction.equals(SCALE)) {
			if (scale == LEFT){
				//System.out.println("Executing left scale command");
				if (closeSwitch == LEFT) {
					leftScaleLeft2();
				}
				else{
					leftScaleRight2();
				}
			}
			else{
				//System.out.println("Executing right scale command");	
				if (closeSwitch == LEFT) {
					leftScaleRight2();
				}
				else{
					rightScaleRight2();
				}
			}
		}
		else{
			//System.out.println("No second action");
		}
	}

	//first action methods 
	// pattern methods name = (Start Position*)(Destination*)(Side)(Action Number)
	// * = mandatory name parameter

	//left side start
	private void leftSwitch1(){
		addSequential(new DriveDistanceCommand(160, 0, 1.0, 3.0, false));
		addSequential(new RotateToAngleCommand(90, 0.5));
		addSequential(new AutoCubeReleaseCommand());
		addSequential(new RotateToAngleCommand(0, 0.5));
	}
	private void leftScaleLeft1(){
		addSequential(new DriveDistanceCommand(320, 0, 1.0, 3.0, false));
		addSequential(new AutoCubeReleaseCommand());
	}
	private void leftScaleRight1(){
		addSequential(new DriveDistanceCommand(160, 0, 1.0, 3.0, false));
		addSequential(new ArcCommand(100, 0, 80, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
		addSequential(new ArcCommand(100, 80, 10, 1.0, true));
		addSequential(new AutoCubeReleaseCommand());
	}

	//right side start
	private void rightSwitch1(){
		addSequential(new DriveDistanceCommand(160, 0, 1.0, 3.0, false));
		addSequential(new RotateToAngleCommand(270, 0.5));
		addSequential(new AutoCubeReleaseCommand());
		addSequential(new RotateToAngleCommand(0, 0.5));


	}
	private void rightScaleLeft1(){
		addSequential(new DriveDistanceCommand(200, 0, 1.0, 5.0, false));
		addSequential(new ArcCommand(100, 0, 280, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 280, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(100, 280, 350, 1.0, false));
		addSequential(new DriveDistanceCommand(60, 350, 0.7, 5.0, true));
		addSequential(new RotateToAngleCommand(90, 0.5));
		addSequential(new AutoCubeReleaseCommand());

	}
	private void rightScaleRight1(){
		addSequential(new DriveDistanceCommand(320, 0, 1.0, 3.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new RotateToAngleCommand(90, 0.5));
		addSequential(new AutoCubeReleaseCommand());
	}

	//center start
	private void centerSwitchLeft1(){
		addSequential(new ArcCommand(90, 0, 310, 0.7, false));
		addSequential(new ArcCommand(140, 310, 0, 0.7, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	private void centerSwitchRight1(){
		addSequential(new ArcCommand(80, 0, 45, 0.7, false));
		addSequential(new DriveDistanceCommand(3, 45, 0.7, 3.0, false));
		addSequential(new ArcCommand(120, 45, 0, 0.7, true));
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
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new ArcCommand(100, 0, 80, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
		addSequential(new ArcCommand(100, 0, 170, 1.0, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void leftSwitchLeft2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new ArcCommand(100, 0, 80, 1.0, false));
		addSequential(new ArcCommand(100, 80, 170, 1.0, true));		
		addSequential(new AutoCubeReleaseCommand());
	}
	public void leftScaleRight2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new ArcCommand(100, 0, 80, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(100, 80, 10, 1.0, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void leftScaleLeft2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new DriveDistanceCommand(180, 0, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new RotateToAngleCommand(90, 0.5));
		addSequential(new AutoCubeReleaseCommand());
	}

	//right side
	public void rightSwitchRight2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new ArcCommand(100, 0, 280, 1.0, false));
		addSequential(new ArcCommand(100, 280, 190, 1.0, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void rightSwitchLeft2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new ArcCommand(100, 0, 280, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 80, 1.0, 5.0, false));
		addSequential(new ArcCommand(100, 0, 190, 1.0, true));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void rightScaleRight2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new DriveDistanceCommand(180, 0, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new RotateToAngleCommand(270, 0.5));
		addSequential(new AutoCubeReleaseCommand());
	}
	public void rightScaleLeft2(){
		if (!Robot.intakeSubsystem.isCubeDetected()) {
			getSecondCube();
		}
		else {

		}
		addSequential(new ArcCommand(100, 0, 280, 1.0, false));
		addSequential(new DriveDistanceCommand(180, 280, 1.0, 5.0, false));
		addParallel(new SetElevatorHeightCommand(5));
		addSequential(new ArcCommand(100, 280, 350, 1.0, true));
	}

	//left Scale
	public void scaleRightSwitchRight2(){

	}
	public void scaleRightSwitchLeft2(){

	}
	public void scaleRightScaleRight2(){

	}

	//right Scale
	public void scaleLeftSwitchRight2(){

	}
	public void scaleLeftSwitchLeft2(){

	}
	public void scaleLeftScaleLeft2(){

	}

	//universal
	public void getSecondCube(){

	}
}
