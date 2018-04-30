package com.darktone.sampler;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonListener implements GpioPinListenerDigital {

	public void handleGpioPinDigitalStateChangeEvent(
			GpioPinDigitalStateChangeEvent event) {
		try {
			System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = "
	                + event.getState());
			if( event.getPin() == RaspiPin.GPIO_01 ) {
				Sampler.createInstance().playSample( 0 );
			}
			else if( event.getPin() == RaspiPin.GPIO_04 ) {
				Sampler.createInstance().playSample( 1 );
			}
		}
		catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
	}

}
