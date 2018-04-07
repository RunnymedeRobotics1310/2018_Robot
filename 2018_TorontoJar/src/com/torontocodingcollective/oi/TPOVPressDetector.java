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
public class TPOVPressDetector {

	private final TGameController gameController;
	private int previousPOVValue;
	
	/**
	 * Declare a press detector over the POV
	 * <br>
	 * @param gameController object
	 */
	public TPOVPressDetector(TGameController gameController) {
		this.gameController = gameController;
		this.previousPOVValue = gameController.getPOV();
	}
	
	/**
	 * Get the current state of the button press detector
	 * @return {@code true} or {@code false}
	 */
	public int get() {

		int newPOVValue = gameController.getPOV();

		// If the button was previously pressed then 
		// just update the button state
		if (previousPOVValue != -1) {
			previousPOVValue = newPOVValue;
		}
		else {
			
			// If the button was not previously pressed, then
			// return true if there is a new button press.
			if (newPOVValue != -1) {
				previousPOVValue = newPOVValue;
				return newPOVValue;
			}
		}
		
		return -1;
	}
	
}
