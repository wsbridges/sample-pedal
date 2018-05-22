package com.darktone.sampler.io;

import com.pi4j.io.gpio.Pin;

public class SharedRGBLCD2x16 extends SharedRGBLCD {

	public SharedRGBLCD2x16(Pin ePin, Pin redPin, Pin bluePin, Pin greenPin) {
		super(ePin, redPin, bluePin, greenPin, 2, 16);
	}

}
