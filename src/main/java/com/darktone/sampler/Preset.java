package com.darktone.sampler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class Preset {

	private List<String> filenames;
	private List<Float> gains;
	private List<Clip> clips;
	private String presetNum;
	private List<String> sourceNames;
	
	private static SoundSystem soundSystem;
	
	static {
		try {
//			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
//			SoundSystemConfig.setNumberNormalChannels(0);
//			SoundSystemConfig.setNumberStreamingChannels(32);
		} catch (SoundSystemException e) {
			throw new RuntimeException( e );
		}
	    soundSystem = new SoundSystem();
	}
	
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
//		List<Clip> clips = new ArrayList<Clip>();
//		for ( int i = 0; i < filenames.size(); i++ ) {
//			clips.add( loadClip( filenames.get(i), gains.get(i) ) );
//		}
//		this.clips = clips;
		
		for ( int i = 0; i < filenames.size(); i++ ) {
			loadClip( i, filenames.get(i), gains.get(i) );
		}

	    
	}
	
	private void loadClip( int index, String filename, float gain ) throws Exception {
//		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//        clip.setFramePosition(0);
//
//        // values have min/max values, for now don't check for outOfBounds values
//        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(gain);
//        return clip;
		soundSystem.newStreamingSource(false, presetNum + "-" + index, new File(filename).toURI().toURL(), new File(filename).getName(), false, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, SoundSystemConfig.getDefaultRolloff());
	}
	
	public void startSample( int i ) {
		
//		Clip sample = clips.get(i);
//		if( !sample.isRunning() ) {
//			sample.start();
//		}
		
		try {
			soundSystem.play(presetNum + "-" + i);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isRunning( int i ) {
//		Clip sample = clips.get(i);
//		return sample.isRunning();
		return soundSystem.playing(presetNum + "-" + i);
	}
	
	public void stopSample( int i ) {
//		Clip sample = clips.get(i);
//		sample.stop();
//		sample.setFramePosition(0);
		System.out.println( "In stopSample" );
		if( isRunning( i ) ) {
			System.out.println( "Stopping sample " + i );
			soundSystem.stop(presetNum + "-" + i);
			System.out.println( "Done Stopping sampel " + i);
		}
	}
	
	public void pauseSample( int i ) {
//		Clip sample = clips.get(i);
//		if( sample.isRunning() ) {
//			sample.stop();
//		}
		soundSystem.pause(presetNum + "-" + i);
	}
	
	public void shutdown() {
		for( int i = 0; i < filenames.size(); i++) {
			stopSample(i);
		}
//		if( clips != null ) {
//			for( Clip clip : clips ) {
//				if( clip != null ) {
//					if( clip.isRunning() ) {
//						clip.stop();
//						clip.setFramePosition(0);
//					}
//				}
//			}
//		}
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

	/**
	 * @return the presetNum
	 */
	public String getPresetNum() {
		return presetNum;
	}

	/**
	 * @param presetNum the presetNum to set
	 */
	public void setPresetNum(String presetNum) {
		this.presetNum = presetNum;
	}
}
