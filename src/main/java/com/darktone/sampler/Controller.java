package com.darktone.sampler;

import java.io.Console;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.pi4j.gpio.extension.mcp.MCP23008GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23008Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Lcd;

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
		//RS=1
		//E=2
		//DB4=3
		//DB5=4
		//DB6=5
		//DB7=6
		//Light=7

        
		I2CLcdDisplay lcd= new I2CLcdDisplay();
		lcd.test( gpio );
		
//		I2CLcdDisplay lcd = new I2CLcdDisplay(2, 16, I2CBus.BUS_1, 0x20, MCP23008Pin.GPIO_07.getAddress(), MCP23008Pin.GPIO_01.getAddress(), MCP23008Pin.GPIO_00.getAddress(), MCP23008Pin.GPIO_02.getAddress(), MCP23008Pin.GPIO_06.getAddress(), MCP23008Pin.GPIO_05.getAddress(), MCP23008Pin.GPIO_04.getAddress(), MCP23008Pin.GPIO_03.getAddress());
//		lcd.clear();
//		lcd.setCursorHome();
//		lcd.write("Hello");
//		lcd.setCursorPosition(1, 0);
//		lcd.write("Hello 2");
//		lcd.setBacklight(true);
//		Thread.sleep(500);
//		lcd.setBacklight(false);
//		Thread.sleep(500);
//		lcd.setBacklight(true);
//		lcd.setBacklight(false);
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
