package com.darktone.sampler.io;

import com.darktone.sampler.Configuration;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.wiringpi.SoftPwm;

public class SharedMultiColorLCD extends GpioLcdDisplay {

	private int red=27;
	private int green=28;
	private int blue=29;
	
	public SharedMultiColorLCD(Pin ePin, int rows, int cols) throws Exception {
		super(rows, cols, Configuration.LCD_RS, ePin, Configuration.LCD_D4, Configuration.LCD_D5, Configuration.LCD_D6, Configuration.LCD_D7);

		SoftPwm.softPwmCreate(red, 0, 100);
		SoftPwm.softPwmCreate(green, 0, 100);
		SoftPwm.softPwmCreate(blue, 0, 100);
	}
    
    public void clearAndWrite(int row, String data, LCDTextAlignment alignment) {
    	this.clear();
    	this.setCursorHome();
    	this.write(0, data, alignment);
    }
    
    public void setColor(int r, int g, int b) {
    	SoftPwm.softPwmWrite(red, r);
    	SoftPwm.softPwmWrite(green, g);
    	SoftPwm.softPwmWrite(blue, b);
    }
    
    public void cycleColors() throws InterruptedException {
    	for(int r=100; r>=0; r--) {
    		for( int g=0; g<=100; g++){
    			setColor(r, g, 0);
    			Thread.sleep(200);
    		}
    	}

    	for(int g=100; g>=0; g--) {
    		for( int b=0; b<=100; b++){
    			setColor(0, g, b);
    			Thread.sleep(200);
    		}
    	}

    	for(int b=100; b>=0; b--) {
    		for( int r=0; r<=100; r++){
    			setColor(r, 0, b);
    			Thread.sleep(200);
    		}
    	}
    }

}
