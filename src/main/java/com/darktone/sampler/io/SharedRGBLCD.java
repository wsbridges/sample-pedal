package com.darktone.sampler.io;

import com.darktone.sampler.Configuration;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.i2c.I2CBus;

/**
 * For LCDs with RGB backlights. LCDs controlled with MCP23017
 * expander chip.
 * 
 * @author Bill
 */
public class SharedRGBLCD extends GpioLcdDisplay {
	
	GpioPinDigitalOutput red;
	GpioPinDigitalOutput green;
	GpioPinDigitalOutput blue;
	
	public SharedRGBLCD(Pin ePin, Pin redPin, Pin greenPin, Pin bluePin, int rows, int cols) throws Exception {
		super(rows, cols, Configuration.LCD_RS, ePin, Configuration.LCD_D4, Configuration.LCD_D5, Configuration.LCD_D6, Configuration.LCD_D7);
		
		final GpioController gpio = GpioFactory.getInstance();
		MCP23017GpioProvider provider = new MCP23017GpioProvider(I2CBus.BUS_1, 0x20);
		red = gpio.provisionDigitalOutputPin(provider, redPin, PinState.HIGH);
		green = gpio.provisionDigitalOutputPin(provider, greenPin, PinState.HIGH);
		blue = gpio.provisionDigitalOutputPin(provider, bluePin, PinState.HIGH);
		
		turnOnBacklight();
	}
    
    public void clearAndWrite(int row, String data, LCDTextAlignment alignment) {
    	this.clear();
    	this.setCursorHome();
    	this.write(0, data, alignment);
    }
	
	public void setColor(Color color) {
		red.setState(!color.isRedOn());
		green.setState(!color.isGreenOn());
		blue.setState(!color.isBlueOn());
	}

	public void turnOffBacklight() {
		setColor(Color.OFF);
	}

	public void turnOnBacklight() {
		setColor(Color.WHITE);
	}
	
}
