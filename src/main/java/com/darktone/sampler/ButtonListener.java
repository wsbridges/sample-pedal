package com.darktone.sampler;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonListener implements GpioPinListenerDigital {

	public void handleGpioPinDigitalStateChangeEvent(
			GpioPinDigitalStateChangeEvent event) {
		System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = "
                + event.getState());
	}

}
