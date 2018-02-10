package com.torontocodingcollective.limitswitch;

import edu.wpi.first.wpilibj.DigitalInput;

public class TLimitSwitch {

	public enum DefaultState {
		TRUE, FALSE
	}

	public final boolean DEFAULT_STATE = false;
	public final DigitalInput limitSwitch;

	public TLimitSwitch(int port, DefaultState defaultState) {
		
		limitSwitch = new DigitalInput(port);
//		this.DEFAULT_STATE = defaultState
	}

	public boolean atLimit() {
		return limitSwitch.get() != DEFAULT_STATE;
	}

}
