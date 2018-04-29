package com.darktone.sampler;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {

	public static void main( String ... args ) throws Exception {
		Sampler sampler = new Sampler();
		sampler.initialize();
		
		final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_20);
        myButton.addListener( new ButtonListener() );
        
        System.out.println(" --> GPIO PIN STATE: " + myButton.getPin() + " = "
                + myButton.getState());

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
            System.out.println(" --> GPIO PIN STATE: " + myButton.getPin() + " = "
                    + myButton.getState());
        }
	}
}
