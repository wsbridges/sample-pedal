package com.darktone.sampler;

import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;

public class Configuration {
	
	//MCP23017 Config
	int MCP23017_BUS = I2CBus.BUS_1;
	int MCP23017_ADDRESS = 0x20;
	
	//LCD Config
	public final static Pin LCD1_E = RaspiPin.GPIO_02;
	public final static Pin LCD1_RED = MCP23017Pin.GPIO_A7;
	public final static Pin LCD1_GREEN = MCP23017Pin.GPIO_A6;
	public final static Pin LCD1_BLUE = MCP23017Pin.GPIO_A5;
	
	public final static Pin LCD_RS = RaspiPin.GPIO_00;  //LCD RS pin
	public final static Pin LCD_D4 = RaspiPin.GPIO_03;  //LCD data bit D4
	public final static Pin LCD_D5 = RaspiPin.GPIO_05;  //LCD data bit D5
	public final static Pin LCD_D6 = RaspiPin.GPIO_06;  //LCD data bit D6
	public final static Pin LCD_D7 = RaspiPin.GPIO_07;  //LCD data bit D7
}
