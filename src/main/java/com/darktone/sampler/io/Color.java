package com.darktone.sampler.io;

public class Color {
	private boolean red;
	private boolean green;
	private boolean blue;
	
	private Color(boolean red, boolean green, boolean blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public static final Color WHITE = new Color(true, true, true);
	public static final Color RED = new Color(true, false, false);
	public static final Color YELLOW = new Color(true, true, false);
	public static final Color GREEN = new Color(false, true, false);
	public static final Color BLUE_GREEN = new Color(false, true, true);
	public static final Color BLUE = new Color(false, false, true);
	public static final Color PURPLE = new Color(true, false, true);
	public static final Color OFF = new Color(false, false, false);
	
	public boolean isRedOn() {
		return red;
	}
	
	public boolean isGreenOn() {
		return green;
	}
	
	public boolean isBlueOn() {
		return blue;
	}
}
