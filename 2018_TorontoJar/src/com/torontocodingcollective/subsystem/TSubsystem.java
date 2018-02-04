package com.torontocodingcollective.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TSubsystem extends Subsystem {

	protected abstract void init();
	protected abstract void updatePeriodic();
	
}
