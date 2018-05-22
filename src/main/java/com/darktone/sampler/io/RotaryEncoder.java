package com.darktone.sampler.io;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class RotaryEncoder {

	private int value = 0;
	private SharedLCD lcd;

	public RotaryEncoder(Pin aPin, Pin bPin, SharedLCD lcd) {
		this.lcd = lcd;
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalInput inputA = gpio.provisionDigitalInputPin(aPin, PinPullResistance.PULL_UP);
		final GpioPinDigitalInput inputB = gpio.provisionDigitalInputPin(bPin, PinPullResistance.PULL_UP);

		inputA.addListener(new GpioPinListenerDigital() {
			int lastA;

			@Override
			public synchronized void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				int a = inputA.getState().getValue();
				int b = inputB.getState().getValue();
				if (lastA != a) {
					rotate(b == a ? -1 : 1);
					lastA = a;
				}
			}
		});
	}
	
	public void rotate(int val) {
		value += val;
		lcd.clearAndWrite(0, "" + value, LCDTextAlignment.ALIGN_CENTER);
	}

}
