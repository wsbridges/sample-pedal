package com.darktone.sampler;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {

	public static void main( String ... args ) throws Exception {
		//initialize sampler
		System.out.println("Initializing Sampler");
		Sampler sampler = Sampler.createInstance();
		System.out.println("Sampler initialized");
		
		final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Pin 18", PinPullResistance.PULL_UP);
        final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Pin 23", PinPullResistance.PULL_UP);
        myButton.addListener( new ButtonListener() );
        myButton2.addListener( new ButtonListener() );
        System.out.println(" --> GPIO PIN STATE: " + myButton.getPin() + " = "
                + myButton.getState());
        System.out.println(" --> GPIO PIN STATE: " + myButton2.getPin() + " = "
                + myButton2.getState());

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(200);
            
        }
	}
}
