package com.darktone.sampler;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonListener implements GpioPinListenerDigital {

	public void handleGpioPinDigitalStateChangeEvent(
			GpioPinDigitalStateChangeEvent event) {
		try {
			System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = "
	                + event.getState());
			if( event.getPin().toString().equals( "\"Pin 18\" <GPIO 1>") && event.getState() == PinState.LOW ) {
				System.out.println("Playing sample 0");
				Sampler.createInstance().playSample( 0 );
			}
			else if( event.getPin().toString().equals( "\"Pin 23\" <GPIO 4>" ) && event.getState() == PinState.LOW ) {
				System.out.println( "Playing sample 1");
				Sampler.createInstance().playSample( 1 );
			}
		}
		catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
	}

}
