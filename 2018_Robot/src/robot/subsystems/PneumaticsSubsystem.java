package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.commands.pneumatics.DefaultPneumaticsCommand;

/**
 *
 */
public class PneumaticsSubsystem extends TSubsystem {
	
	Compressor compressor = new Compressor();
	
	boolean compressorState = false;
	
	@Override
	public void init() {
		compressor.setClosedLoopControl(false);
	};

	public void disableCompressor() {
		compressor.setClosedLoopControl(false);
	}
	
	
	
	public void enableCompressor() {
		compressor.setClosedLoopControl(true);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultPneumaticsCommand());
	}
	
	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		
		SmartDashboard.putBoolean("Compressor", compressor.getClosedLoopControl());
	}

}
