package com.darktone.sampler.io;

public class LCDUtils {
	
	/**
	 * Checks to see if value is between 0 and 100.
	 * If the value is over 100, returns 100.
	 * If the value is less than 0, returns 0.
	 * Otherwise, the value is valid.
	 * @param percent
	 * @return
	 */
	public static int validatePercent(int percent) {
		if(percent > 100) {
			return 100;
		}
		else if(percent < 0) {
			return 0;
		}
		else {
			return percent;
		}
	}
	
}