package com.darktone.sampler.io;

import com.pi4j.io.gpio.Pin;

public class SharedRGBLCD4x20 extends SharedRGBLCD {

	public SharedRGBLCD4x20(Pin ePin, Pin redPin, Pin greenPin, Pin bluePin) throws Exception {
		super(ePin, redPin, greenPin, bluePin, 4, 20);
	}

}
