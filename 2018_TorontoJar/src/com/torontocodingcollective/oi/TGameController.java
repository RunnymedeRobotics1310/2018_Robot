package com.torontocodingcollective.oi;

import edu.wpi.first.wpilibj.Joystick;

public abstract class TGameController extends Joystick {

	public TGameController(int port) {
		super(port);
	}

	public abstract double getAxis(TStick stick, TAxis axis);

	public abstract boolean getButton(TButton button);
	public abstract boolean getButton(TStick stick);
	
	public boolean getButton(TTrigger trigger) {
		return getTrigger(trigger) > 0.3;
	};

	public void setRumble(double volume) {
		super.setRumble(RumbleType.kLeftRumble, volume);
		super.setRumble(RumbleType.kRightRumble, volume);
	}
	
	public abstract double getTrigger(TTrigger trigger);
}
