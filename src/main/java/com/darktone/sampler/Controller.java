package com.darktone.sampler;

import java.io.Console;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Controller {

	public static void main( String ... args ) throws Exception {
		//initialize sampler
		System.out.println("Initializing Sampler");
		Sampler sampler = Sampler.createInstance();
		System.out.println("Sampler initialized");
		
		raspberryPiSetup();
		
	}
	
	public static void keyPressSetup( Sampler sampler) throws Exception {
		Display.setDisplayMode(new DisplayMode(0, 0));
		Display.create();
		Keyboard.create();
		while( !Display.isCloseRequested()) {
			while(Keyboard.next()) {
				if( Keyboard.getEventKey() == Keyboard.KEY_A ) {
					if(Keyboard.getEventKeyState()) {
						System.out.println("A was pressed");
						if( sampler.isSamplePlaying(0)) {
							System.out.println("Stopping sample 0");
							sampler.stopSample(0);
						}
						else {
							System.out.println("Playing sample 0");
							sampler.playSample( 0 );
						}
					}
					else {
						System.out.println( "A was released");
					}
				}
				else if( Keyboard.getEventKey() == Keyboard.KEY_S) {
	
					if(Keyboard.getEventKeyState()) {
						System.out.println("S was pressed");
						if( sampler.isSamplePlaying(1)) {
							System.out.println("Stopping sample 1");
							sampler.stopSample(1);
						}
						else {
							System.out.println("Playing sample 1");
							sampler.playSample( 1 );
						}
					}
					else {
						System.out.println( "S was released");
					}
				}
			}
			Display.update();
		}
		Display.destroy();
	}
	
	public static void raspberryPiSetup() throws Exception {
		final GpioController gpio = GpioFactory.getInstance();
		setupSampleButton(gpio, RaspiPin.GPIO_01, 0);
		setupSampleButton(gpio, RaspiPin.GPIO_04, 1);

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(200);
            
        }
	}
	
	private static void setupSampleButton( final GpioController gpio, Pin pin, final int sampleNum ) {
		GpioPinDigitalInput button = gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP);
		button.addListener( new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(
					GpioPinDigitalStateChangeEvent event) {
				if( event.getState() == PinState.LOW ) {
					try {
						Sampler sampler = Sampler.createInstance();
						if( sampler.isSamplePlaying(sampleNum)) {
							System.out.println("Stopping sample " + sampleNum);
							sampler.stopSample(sampleNum);
						}
						else {
							System.out.println("Playing sample " + sampleNum);
							sampler.playSample(sampleNum);
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
