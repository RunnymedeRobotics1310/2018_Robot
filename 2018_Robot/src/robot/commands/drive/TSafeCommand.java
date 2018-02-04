package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class TSafeCommand extends Command {

	private final double maxTimeSec;
	
	public TSafeCommand(double maxTimeSec) {
		this.maxTimeSec = maxTimeSec;
	}
	
    protected boolean isFinished() {
    	if (isCancelled() || isTimedOut()) {
    		return true;
    	}
        return false;
    }

    public boolean isCancelled() {
    	if (Robot.oi.getCancelCommand()) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isTimedOut() {
    	if (timeSinceInitialized() >= maxTimeSec) {
    		return true;
    	}
    	return false;
    }
}
