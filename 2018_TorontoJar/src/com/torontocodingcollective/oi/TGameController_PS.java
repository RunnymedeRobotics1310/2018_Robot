package com.torontocodingcollective.oi;

public class TGameController_PS extends TGameController {

	public TGameController_PS(int port) {
		super(port);
	}

	@Override
	public double getAxis (TStick stick, TAxis axis) {

		switch (stick) {
		
		case LEFT:
			switch(axis) {
			case X:
				return super.getRawAxis(0);
			case Y:
				return super.getRawAxis(1);
			}

		case RIGHT:
			switch (axis) {
			case X:
				return super.getRawAxis(2);
			case Y:
				return super.getRawAxis(5);
			}

		default: return 0.0;
		}
	}

	@Override
	public boolean getButton(TButton button) {

		switch (button) {

		case A:
		case X_SYMBOL:
			return getRawButton(2);
		case B:
		case CIRCLE:
			return getRawButton(3);
		case X:
		case SQUARE:
			return getRawButton(1);
		case Y:
		case TRIANGLE:
			return getRawButton(4);

		case LEFT_BUMPER:
			return getRawButton(5);
		case RIGHT_BUMPER:
			return getRawButton(6);	

		case BACK:
		case SELECT:
		case SHARE:
			return getRawButton(9);
		case START:
		case OPTIONS:
			return getRawButton(10);

		case PS:
			return getRawButton(13);

		case TOUCHPAD:
			return getRawButton(14);

		default: // Unknown button 
			return false;
		}
	}

	@Override
	public boolean getButton(TStick stick) {
		
		switch (stick) {
		
		case LEFT:
			return getRawButton(11);
		case RIGHT:
			return getRawButton(12);

		default: return false;
		}
	}

	@Override
	public double getTrigger (TTrigger trigger) {
		
		switch (trigger) {
		
		case LEFT:
			return getRawAxis(3);
		case RIGHT:
			return getRawAxis(4);

		default: return 0.0;
		}
	}
}



