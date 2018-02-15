package com.torontocodingcollective.oi;

/**
 * The TButtonPressDetector class implements a Button Press Detector on the passed in button.  
 * <p>  
 * This routine will only return {@code true} on the first occurance of a 
 * button press after the button was released.  It will then return {@code false} for all subsequent
 * calls to the {@link #get()} routine even if the button is still pressed.
 * <p>
 * The button must be released before the next press is detected. 
 * <p>
 * Use the {@link #get()} method rather than the button's get() routine.
 */
public class TButtonPressDetector {

	private final TGameController gameController;
	private final TButton button;
	private final TStick  stick;
	private final TTrigger trigger;
	private boolean previousButtonState;
	
	/**
	 * Declare a button press detector over the specified button on the GameController
	 * <br>
	 * If using a Stick button, use the constructor {@link #TButtonPressDetector(TGameController, TStick)}
	 * <br>
	 * if using a Trigger as a button, use the constructor {@link #TButtonPressDetector(TGameController, TTrigger)}
	 * @param gameController object
	 * @param button to use for the button press detector
	 */
	public TButtonPressDetector(TGameController gameController, TButton button) {
		this.gameController = gameController;
		this.button = button;
		this.stick = null;
		this.trigger = null;
		this.previousButtonState = gameController.getButton(button);
	}
	
	/**
	 * Declare a button press detector over the specified button on the GameController Stick (push)
	 * <br>
	 * @param gameController object
	 * @param stick (push) to use for the button press detector
	 */
	public TButtonPressDetector(TGameController gameController, TStick stick) {
		this.gameController = gameController;
		this.button = null;
		this.stick = stick;
		this.trigger = null;
		this.previousButtonState = gameController.getButton(stick);
	}
	
	/**
	 * Declare a button press detector over the specified trigger on the GameController
	 * <br>
	 * The initial state will be set to {@code false}.
	 * <p>
	 * Triggers on modern game controllers are typically analog returning a {@code double} 
	 * value from 0.0 to 1.0.  This routine determines the trigger to be pressed .3 and 
	 * released below that value.  
	 * @param gameController object
	 * @param trigger to use for the button press detector
	 */
	public TButtonPressDetector(TGameController gameController, TTrigger trigger) {
		this.gameController = gameController;
		this.button = null;
		this.stick = null;
		this.trigger = trigger;
		this.previousButtonState = gameController.getButton(trigger);
	}
	
	/**
	 * Get the current state of the button press detector
	 * @return {@code true} or {@code false}
	 */
	public boolean get() {
		
		// If the button was previously pressed then 
		// just update the button state
		if (previousButtonState) {
			previousButtonState = getNewButtonState();
		}
		else {
			
			// If the button was not previously pressed, then
			// return true if there is a new button press.
			if (getNewButtonState()) {
				previousButtonState = true;
				return true;
			}
		}
		
		return false;
	}
	
	private boolean getNewButtonState() {
		
		if (button != null) {
			return gameController.getButton(button);
		}
		
		if (trigger != null) {
			return gameController.getButton(trigger);
		}
		
		if (stick != null) {
			return gameController.getButton(stick);
		}
		
		return true;
	}
	
}
