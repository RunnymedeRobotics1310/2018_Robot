package com.torontocodingcollective.oi;

public class TGameController_Xbox extends TGameController {
	
	public TGameController_Xbox(int port) {
		super(port);
	}

	@Override
	public double getAxis(TStick stick, TAxis axis) {
		
		switch (stick) {
		
		case LEFT:
			switch (axis) {
			case X:
				return super.getRawAxis(0);
			case Y:
				return super.getRawAxis(1);
			}
			
		case RIGHT:
			switch (axis) {
			case X:
				return super.getRawAxis(4);
			case Y:
				return super.getRawAxis(5);
			}
			
		default: return 0.0;
		}
	}
	
	@Override
	public double getTrigger(TTrigger trigger) {
		
		switch (trigger) {
		case LEFT:
			return getRawAxis(2);
		case RIGHT:
			return getRawAxis(3);
			
		default: return 0.0;
		}
	}
	
	@Override
	public boolean getButton(TButton button) {
		
		switch (button) {
		case A:
			return getRawButton(0);
		case B:
			return getRawButton(1);
		case X:
			return getRawButton(2);
		case Y:
			return getRawButton(3);
			
		case BACK:
			return getRawButton(6);
		case START:
			return getRawButton(7);

		case LEFT_BUMPER:
			return getRawButton(4);
		case RIGHT_BUMPER:
			return getRawButton(5);
	
		default: return false;
		}
	}
	
	@Override
	public boolean getButton(TTrigger trigger) {
		
		switch (trigger) {
		case LEFT:
			return getRawAxis(2) > 0.3;
		case RIGHT:
			return getRawAxis(3) > 0.3;
			
		default: return false;
		}
	}

	@Override
	public boolean getButton(TStick stick) {

		switch (stick) {
		case LEFT:
			return getRawButton(9);
		case RIGHT:
			return getRawButton(10);
			
		default: return false;
		}
	}

}
