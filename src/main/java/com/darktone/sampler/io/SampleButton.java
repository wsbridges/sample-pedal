package com.darktone.sampler.io;

import com.darktone.sampler.Sampler;
import com.pi4j.component.lcd.LCD;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class SampleButton {
	
	/**
	 * TODO: logging, javadoc info
	 * @param pin
	 * @param sampleNumber
	 */
	public static void provisionSampleButton(Pin pin, final int sampleNumber, LCD lcd) {
		final GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalInput button = gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP);
		button.addListener( new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(
					GpioPinDigitalStateChangeEvent event) {
				if( event.getState() == PinState.LOW ) {
					try {
						Sampler sampler = Sampler.createInstance();
						if( sampler.isSamplePlaying(sampleNumber)) {
							System.out.println("Stopping sample " + sampleNumber);
							sampler.stopSample(sampleNumber);
						}
						else {
							System.out.println("Playing sample " + sampleNumber);
							sampler.playSample(sampleNumber);
						}
					}
					catch(Exception e) {
						throw new RuntimeException(e);
					}
						
				}
			}
		});
	}

}
