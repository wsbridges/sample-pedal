package com.darktone.sampler;

//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;

import com.darktone.sampler.io.Color;
import com.darktone.sampler.io.SampleButton;
import com.darktone.sampler.io.SharedRGBLCD2x16;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

public class Controller {

	//configuration
	private static final Pin BUTTON_1_PIN = RaspiPin.GPIO_01;
	private static final Pin BUTTON_2_PIN = RaspiPin.GPIO_04;

	public static void main( String ... args ) throws Exception {
		//initialize sampler
//		System.out.println("Initializing Sampler");
//		Sampler sampler = Sampler.createInstance();
//		System.out.println("Sampler initialized");
		
		raspberryPiSetup();
		
	}
	
	public static void raspberryPiSetup() throws Exception {
		Gpio.wiringPiSetup();
		
		final SharedRGBLCD2x16 lcd = new SharedRGBLCD2x16(Configuration.LCD1_E, Configuration.LCD1_RED, Configuration.LCD1_GREEN, Configuration.LCD1_BLUE);
		lcd.turnOnBacklight();
		lcd.clearAndWrite(0, "Initializing...", LCDTextAlignment.ALIGN_CENTER);
		
		//Loads sampler
		Sampler.createInstance();
		
		SampleButton.provisionSampleButton(BUTTON_1_PIN, 0, lcd);
		SampleButton.provisionSampleButton(BUTTON_2_PIN, 1, lcd);
		
//		RotaryEncoder enc = new RotaryEncoder(RaspiPin.GPIO_27, RaspiPin.GPIO_28, lcd);

		//cycle through colors
		lcd.setColor(Color.WHITE);
		lcd.clearAndWrite(0, "White", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		lcd.setColor(Color.RED);
		lcd.clearAndWrite(0, "Red", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		lcd.setColor(Color.YELLOW);
		lcd.clearAndWrite(0, "Yellow", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		lcd.setColor(Color.GREEN);
		lcd.clearAndWrite(0, "Green", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		lcd.setColor(Color.BLUE_GREEN);
		lcd.clearAndWrite(0, "Blue Green", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		lcd.setColor(Color.BLUE);
		lcd.clearAndWrite(0, "Blue", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		lcd.setColor(Color.PURPLE);
		lcd.clearAndWrite(0, "Purple", LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(1000);
		
		//White
		lcd.setColor(Color.WHITE);
		lcd.clearAndWrite(0, "Ready", LCDTextAlignment.ALIGN_CENTER);
        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(200);
            
        }
	}
	
	//For debugging. TODO: remove this
//	public static void keyPressSetup( Sampler sampler) throws Exception {
//		Display.setDisplayMode(new DisplayMode(0, 0));
//		Display.create();
//		Keyboard.create();
//		while( !Display.isCloseRequested()) {
//			while(Keyboard.next()) {
//				if( Keyboard.getEventKey() == Keyboard.KEY_A ) {
//					if(Keyboard.getEventKeyState()) {
//						System.out.println("A was pressed");
//						if( sampler.isSamplePlaying(0)) {
//							System.out.println("Stopping sample 0");
//							sampler.stopSample(0);
//						}
//						else {
//							System.out.println("Playing sample 0");
//							sampler.playSample( 0 );
//						}
//					}
//					else {
//						System.out.println( "A was released");
//					}
//				}
//				else if( Keyboard.getEventKey() == Keyboard.KEY_S) {
//	
//					if(Keyboard.getEventKeyState()) {
//						System.out.println("S was pressed");
//						if( sampler.isSamplePlaying(1)) {
//							System.out.println("Stopping sample 1");
//							sampler.stopSample(1);
//						}
//						else {
//							System.out.println("Playing sample 1");
//							sampler.playSample( 1 );
//						}
//					}
//					else {
//						System.out.println( "S was released");
//					}
//				}
//			}
//			Display.update();
//		}
//		Display.destroy();
//	}
}
