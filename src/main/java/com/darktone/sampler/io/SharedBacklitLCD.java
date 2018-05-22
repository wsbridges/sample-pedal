package com.darktone.sampler.io;

import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

public class SharedBacklitLCD extends SharedLCD implements BacklitLCD {
	
	private Pin backlightPin;
	private int brightness = 100;

	public SharedBacklitLCD(Pin ePin, Pin backlightPin, int rows, int cols) {
		super(ePin, rows, cols);
		this.backlightPin = backlightPin;
		
		Gpio.wiringPiSetup(); //This is required for PWM
		
		SoftPwm.softPwmCreate(backlightPin.getAddress(), 0, 100);
	}
	
	public void setBrightness(int brightness) {
		this.brightness = LCDUtils.validatePercent(brightness);
		turnOnBacklight();
	}

	@Override
	public void turnOffBacklight() {
		SoftPwm.softPwmWrite(backlightPin.getAddress(), 0);
	}

	@Override
	public void turnOnBacklight() {
		SoftPwm.softPwmWrite(backlightPin.getAddress(), brightness);
	}

}
