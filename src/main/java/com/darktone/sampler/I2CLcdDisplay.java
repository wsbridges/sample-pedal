package com.darktone.sampler;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.gpio.extension.mcp.MCP23008GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23008Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;

import java.util.BitSet;

public class I2CLcdDisplay {
    boolean             rsFlag                = false; 
    boolean             eFlag                 = false;
    private static I2CDevice   dev                   = null;
    private final int[] LCD_LINE_ADDRESS      = { 0x80, 0xC0};  //Address for LCD Lines 0 and 1

    private final boolean LCD_CHR = true; //To decide sent data is data or command
    private final static boolean LCD_CMD = false;

    int         RS_PIN=1; //Pin of MCP23017 PORTB/A connected LCD RS pin
    int         EN_PIN=2; //Pin of MCP23017 PORTB/A connected LCD E pin
    int         D7_PIN=6;//Pin of MCP23017 PORTB/A connected LCD D7 pin
    int         D6_PIN=5;//Pin of MCP23017  PORTB/A connected LCD D6 pin
    int         D5_PIN=4;//Pin of MCP23017  PORTB/A connected LCD D5 pin
    int         D4_PIN=3;//Pin of MCP23017 PORTB/A connected LCD D4 pin

	public void test( GpioController gpio ) throws Exception{
		System.out.println("Strting up the MCP23017 based 16x2 LCD Example");
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1); //
        MCP23008GpioProvider prov = new MCP23008GpioProvider(I2CBus.BUS_1, 0x20);
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_01);//RS
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_02);//E
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_03);
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_04);
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_05);
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_06);
        gpio.provisionDigitalOutputPin(prov, MCP23008Pin.GPIO_07);
        com.pi4j.component.lcd.impl.I2CLcdDisplay display = new com.pi4j.component.lcd.impl.I2CLcdDisplay(2, 16, I2CBus.BUS_1, 0x20, MCP23008Pin.GPIO_07.getAddress(), MCP23008Pin.GPIO_01.getAddress(), MCP23008Pin.GPIO_00.getAddress(), MCP23008Pin.GPIO_02.getAddress(), MCP23008Pin.GPIO_06.getAddress(), MCP23008Pin.GPIO_05.getAddress(), MCP23008Pin.GPIO_04.getAddress(), MCP23008Pin.GPIO_03.getAddress());
//        GpioLcdDisplay display = new GpioLcdDisplay(2, 16, MCP23008Pin.GPIO_01, MCP23008Pin.GPIO_02, MCP23008Pin.GPIO_03, MCP23008Pin.GPIO_04, MCP23008Pin.GPIO_05, MCP23008Pin.GPIO_06);

        display.clear();
        display.setCursorHome();
        display.write("Hello");
		
//		lcd.init(); //LCD Initialization Routine
//		
//		lcd.lcd_byte(0x01, LCD_CMD); //LCD Clear Command
//		lcd.lcd_byte(0x02, LCD_CMD); //LCD Home Command
//		lcd.write("WeArGenius");
//		lcd.setCursorPosition(1, 0);
//		lcd.write("weargenius.in");
//		
//		Thread.sleep(2000);
//		while(true){
//			lcd.lcd_byte(0x01, LCD_CMD); //LCD Clear
//			lcd.setCursorPosition(1, 0);
//			lcd.write("Embedded");
//			Thread.sleep(3000);
//			
//			lcd.lcd_byte(0x01, LCD_CMD); //LCD Clear
//			lcd.setCursorPosition(1, 0);
//			lcd.write("Home Automation");
//			Thread.sleep(3000);
//			
//			lcd.lcd_byte(0x01, LCD_CMD); //LCD Clear
//			lcd.setCursorPosition(1, 0);
//			lcd.write("IOT");
//			Thread.sleep(3000);
//			
//			lcd.lcd_byte(0x01, LCD_CMD); //LCD Clear
//			lcd.setCursorPosition(1, 0);
//			lcd.write("Programming");
//			Thread.sleep(3000);
//		}
	}
	
    public void write(byte data) { //Writes 1 Byte data to LCD
        try {
            lcd_byte(data, LCD_CHR);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    public void write(String data) {//Writes a string to LCD
        for (int i = 0; i < data.length(); i++) {
            try {
                lcd_byte(data.charAt(i), LCD_CHR);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void lcd_byte(int val, boolean type) throws Exception { //Sets RS flag and send value to ports depending on DATA or COMMAND

    	rsFlag=type;

        write(val >> 4);
        pulse_en(type, val >> 4);    // cmd or display data

        write(val & 0x0f);
        pulse_en(type, val & 0x0f);
    }

    public static BitSet fromByte(byte b) { //Convert a byte into Bitset
        BitSet bits = new BitSet(8);

        for (int i = 0; i < 8; i++) {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }

        return bits;
    }

    private void init() throws Exception { //Initialization routine of LCD
        lcd_byte(0x33, LCD_CMD);    // 4 bit
        lcd_byte(0x32, LCD_CMD);    // 4 bit
        lcd_byte(0x28, LCD_CMD);    // 4bit - 2 line
        lcd_byte(0x08, LCD_CMD);    // don't shift, hide cursor
        lcd_byte(0x01, LCD_CMD);    // clear and home display
        lcd_byte(0x06, LCD_CMD);    // move cursor right
        lcd_byte(0x0c, LCD_CMD);    // turn on
    }

    private void pulse_en(boolean type, int val) throws Exception {// Make the enable pin high and low to provide a pulse.
        eFlag = true;
        write(val);
        eFlag =false;
        write(val);

        if (type == LCD_CMD) {
            Thread.sleep(1);
        }
    }
    private void write(int incomingData) throws Exception { // Arrange the respective bit of value to be send depending upon the pins the LCD is connected to.
        int    tmpData = incomingData;
        BitSet bits    = fromByte((byte) tmpData);
        byte   out     = (byte) ((bits.get(3)
                                  ? 1 << D7_PIN
                                  : 0 << D7_PIN) | (bits.get(2)
                ? 1 << D6_PIN
                : 0 << D6_PIN) | (bits.get(1)
                                 ? 1 << D5_PIN
                                 : 0 << D5_PIN) | (bits.get(0)
                ? 1 << D4_PIN
                : 0 << D4_PIN) | (rsFlag
            					 ? 1 << RS_PIN
                : 0 << RS_PIN) | (eFlag
                                 ? 1 << EN_PIN
                                 : 0 << EN_PIN));

        dev.write(0x13,out); //Set the value to PORT B register.
    }

    public void setCursorPosition(int row, int column) {

        try {
            lcd_byte(LCD_LINE_ADDRESS[row] + column, LCD_CMD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
