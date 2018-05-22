package com.darktone.sampler;

import java.util.List;

/**
 * Wrapper to make JSON parsing easier.
 * 
 * @author Bill
 */
public class Presets {
	private List<Preset> presets;

	/**
	 * @return the presets
	 */
	public List<Preset> getPresets() {
		return presets;
	}

	/**
	 * @param presets the presets to set
	 */
	public void setPresets(List<Preset> presets) {
		this.presets = presets;
	}
}
