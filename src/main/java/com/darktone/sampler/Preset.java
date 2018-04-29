package com.darktone.sampler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Preset {

	private List<String> filenames;
	private List<Float> gains;
	private List<Clip> clips;
	
	public Preset( List<String> filenames ) {
		this( filenames, null );
	}
	
	public Preset( List<String> filenames, List<Float> gains ) {
		this.setFilenames(filenames);
		if( gains == null ) {
			gains = new ArrayList<Float>();
			for( String filename : filenames ) {
				gains.add( 0.0f );
			}
		}
		this.gains = gains;
	}
	
	public void loadPreset() throws Exception {
		List<Clip> clips = new ArrayList<Clip>();
		for ( int i = 0; i < filenames.size(); i++ ) {
			clips.add( loadClip( filenames.get(i), gains.get(i) ) );
		}
		this.clips = clips;
	}
	
	private Clip loadClip( String filename, float gain ) throws Exception {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.setFramePosition(0);

        // values have min/max values, for now don't check for outOfBounds values
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gain);
        return clip;
	}
	
	public void startSample( int i ) {
		Clip sample = clips.get(i);
		if( !sample.isRunning() ) {
			sample.start();
		}
	}
	
	public void stopSample( int i ) {
		Clip sample = clips.get(i);
		if( sample.isRunning() ) {
			sample.stop();
			sample.setFramePosition(0);
		}
	}
	
	public void pauseSample( int i ) {
		Clip sample = clips.get(i);
		if( sample.isRunning() ) {
			sample.stop();
		}
	}
	
	public void shutdown() {
		if( clips != null ) {
			for( Clip clip : clips ) {
				if( clip != null ) {
					if( clip.isRunning() ) {
						clip.stop();
						clip.setFramePosition(0);
					}
				}
			}
		}
	}

	/**
	 * @return the filenames
	 */
	public List<String> getFilenames() {
		return filenames;
	}

	/**
	 * @param filenames the filenames to set
	 */
	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}

	/**
	 * @return the gains
	 */
	public List<Float> getGains() {
		return gains;
	}

	/**
	 * @param gains the gains to set
	 */
	public void setGains(List<Float> gains) {
		this.gains = gains;
	}
}
