package com.darktone.sampler.io;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Class for LCDs that share RS and D4-D7 pins. This saves GPIO pins on the Raspberry Pi.
 * 
 * The E (strobe) pin which tells the LCD to write the data is different if multiple LCDs are used.
 * 
 * @author Bill
 */
public class SharedLCD extends GpioLcdDisplay {
	private final static Pin rsPin = RaspiPin.GPIO_00;  //LCD RS pin
	private final static Pin d4Pin = RaspiPin.GPIO_03;  //LCD data bit D4
	private final static Pin d5Pin = RaspiPin.GPIO_05;  //LCD data bit D5
	private final static Pin d6Pin = RaspiPin.GPIO_06;  //LCD data bit D6
    private final static Pin d7Pin = RaspiPin.GPIO_07;  //LCD data bit D7
    
    public SharedLCD(Pin ePin, int rows, int cols) {
    	super(rows, cols, rsPin, ePin, d4Pin, d5Pin, d6Pin, d7Pin);
    }
    
    public void clearAndWrite(int row, String data, LCDTextAlignment alignment) {
    	this.clear();
    	this.setCursorHome();
    	this.write(0, data, alignment);
    }
    
}
