package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PowerSubsystem extends TSubsystem {
	
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	
	public double getIntakeWheelMotorCurrent() {
		return pdp.getCurrent(4);
	};

	
	@Override
	public void init() {
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		SmartDashboard.putNumber("Intake Wheel Current", getIntakeWheelMotorCurrent());
	}


	@Override
	protected void initDefaultCommand() {
	}

}
