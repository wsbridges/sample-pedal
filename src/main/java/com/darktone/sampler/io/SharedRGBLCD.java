package com.darktone.sampler.io;

import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.SoftPwm;

/**
 * For LCDs with RGB backlights
 * 
 * @author Bill
 */
public class SharedRGBLCD extends SharedLCD implements BacklitLCD {
	
	private Pin redPin;
	private Pin greenPin;
	private Pin bluePin;
	
	private int redPercent=100;
	private int greenPercent=100;
	private int bluePercent=100;
	
	public SharedRGBLCD(Pin ePin, Pin redPin, Pin bluePin, Pin greenPin, int rows, int cols) {
		super(ePin, rows, cols);
		this.redPin = redPin;
		this.greenPin = greenPin;
		this.bluePin = bluePin;
		
		SoftPwm.softPwmCreate(redPin.getAddress(), 0, 100);
		SoftPwm.softPwmCreate(greenPin.getAddress(), 0, 100);
		SoftPwm.softPwmCreate(bluePin.getAddress(), 0, 100);
		
		turnOnBacklight();
	}
	
	/**
	 * Sets RGB color of backlight. Values for each color can be 0-100.
	 * 
	 * Examples: 
	 * 	- setColor(100, 0, 0) would be red
	 * 	- setColor(0, 100, 0) would be green
	 * 	- setColor(100, 0, 100) would be purple
	 * 	- setColor(50, 0, 50) would be a dimmer purple
	 * 
	 * @param redPercent
	 * @param greenPercent
	 * @param bluePercent
	 */
	public void setColor(int redPercent, int greenPercent, int bluePercent) {
		this.redPercent = LCDUtils.validatePercent(redPercent);
		this.greenPercent = LCDUtils.validatePercent(greenPercent);
		this.bluePercent = LCDUtils.validatePercent(bluePercent);
		
		turnOnBacklight();
	}

	@Override
	public void turnOffBacklight() {
		SoftPwm.softPwmWrite(redPin.getAddress(), 0);
		SoftPwm.softPwmWrite(greenPin.getAddress(), 0);
		SoftPwm.softPwmWrite(bluePin.getAddress(), 0);
	}

	@Override
	public void turnOnBacklight() {
		//Set the backlight to where it was when it was last on
		//The default is white
		SoftPwm.softPwmWrite(redPin.getAddress(), redPercent);
		SoftPwm.softPwmWrite(greenPin.getAddress(), greenPercent);
		SoftPwm.softPwmWrite(bluePin.getAddress(), bluePercent);
	}
	
}
