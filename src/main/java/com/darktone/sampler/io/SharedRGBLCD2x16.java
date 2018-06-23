package com.darktone.sampler.io;

import com.pi4j.io.gpio.Pin;

public class SharedRGBLCD2x16 extends SharedRGBLCD {

	public SharedRGBLCD2x16(Pin ePin, Pin redPin, Pin greenPin, Pin bluePin) {
		super(ePin, redPin, greenPin, bluePin, 2, 16);
	}

}
