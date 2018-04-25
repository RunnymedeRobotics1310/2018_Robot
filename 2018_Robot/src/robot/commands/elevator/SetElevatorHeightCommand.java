package robot.commands.elevator;

import robot.Robot;
import robot.RobotConst;
import robot.commands.drive.TSafeCommand;
import robot.subsystems.ElevatorSubsystem;

public class SetElevatorHeightCommand extends TSafeCommand {
	
	private double setLevel;
	
	public SetElevatorHeightCommand(double setLevel) {
		super(0);
		requires (Robot.elevatorSubsystem);
		this.setLevel = setLevel;
	}
	
	public void initialize() {
	}
	
	protected void execute() {
	
		// In Teleop you can adjust the setpoint within this command
		
		int elevatorMove = Robot.oi.getElevatorMove();
		
		if (elevatorMove == 0) {
			setLevel++;
			if (setLevel > ElevatorSubsystem.MAX_LEVEL) {
				setLevel = ElevatorSubsystem.MAX_LEVEL;
			}
		}

		if (elevatorMove == 180) {
			setLevel--;
			if (setLevel < ElevatorSubsystem.MIN_LEVEL) {
				setLevel = ElevatorSubsystem.MIN_LEVEL;
			}
		}
		

		if (Robot.elevatorSubsystem.getLevel() > setLevel) {
			Robot.elevatorSubsystem.setSpeed(-0.8);
		}
		if (Robot.elevatorSubsystem.getLevel() < setLevel) {
			Robot.elevatorSubsystem.setSpeed(1.0);
		}
	}
	
	protected boolean isFinished() {
		
		if (super.isFinished()) {
			return true;
		}
		
		// If the operator is trying to move the elevator manually, then 
		// this command is done.
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.1) {
			return true;
		}
		
		if (Robot.elevatorSubsystem.getLevel() == setLevel) {
			return true;
		}
		return false;
	}
	
	protected void end() {
		
		// Lock intake
		if (Robot.elevatorSubsystem.getLevel() > 0) {
			if (Robot.intakeSubsystem.isCubeDetected()) {
				Robot.elevatorSubsystem.setSpeed(RobotConst.ELEVATOR_LOCK_SPEED_WITH_CUBE);
			}
			else {
				Robot.elevatorSubsystem.setSpeed(RobotConst.ELEVATOR_LOCK_SPEED);
			}
		}
			
		Robot.elevatorSubsystem.setSpeed(0);
	}
}
