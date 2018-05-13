package com.darktone.sampler;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

import com.google.gson.Gson;

public class Sampler {
	
	private static final String PRESET_FILE="/var/opt/samples/presets.json";
	private List<Preset> presets;
	private int currentPreset = 0;
	private int lastPreset = 0;
	
	private static Sampler sampler;
	
	private Sampler() {
		
	}
	
	public static Sampler createInstance() throws Exception {
		if( sampler == null ) {
			sampler = new Sampler();
			sampler.initialize();
		}
		
		return sampler;
	}

	public void initialize() throws Exception {
		Gson gson = new Gson();
		String json = IOUtils.toString( new FileInputStream( new File( PRESET_FILE ) ), Charset.defaultCharset() );
		Presets presets = gson.fromJson(json, Presets.class);
		this.presets = presets.getPresets();
		lastPreset = this.presets.size() - 1;
		
		for( Preset preset : this.presets ) {
			preset.loadPreset();
		}
		changePreset( 0 );
	}
	
	public void nextPreset() throws Exception {
		if( currentPreset == lastPreset ) {
			changePreset( 0 );
		}
		else {
			changePreset( currentPreset + 1 );
		}
	}
	
	public void prevPreset() throws Exception {
		if( currentPreset == 0 ) {
			changePreset( lastPreset );
		}
		else {
			changePreset( currentPreset - 1 );
		}
	}
	
	public boolean isSamplePlaying( int i ) {
		Preset preset = getCurrentPreset();
		return preset.isRunning(i);
	}
	
	public void playSample( int i ) {
		Preset preset = getCurrentPreset();
		preset.startSample( i );
	}
	
	public void stopSample( int i ) {
		Preset preset = getCurrentPreset();
		preset.stopSample( i );
	}
	
	protected Preset getCurrentPreset() {
		return presets.get(currentPreset);
	}
	
	private void changePreset( int num ) throws Exception {
		getCurrentPreset().shutdown();
		currentPreset = num;
		Preset preset = presets.get( num );
	}
	
	public static void main( String ... args ) throws Exception {
//		
//		SoundSystem soundSystem;
//		SoundSystemConfig.addLibrary(LibraryJavaSound.class);
//		SoundSystemConfig.setCodec("wav", CodecWav.class);
//		
//	    soundSystem = new SoundSystem();
//	    
//	    soundSystem.newStreamingSource(false, "1", new File("/var/opt/samples/We will see how the Lord favors you.wav").toURI().toURL(), "We will see how the Lord favors you.wav", false, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, SoundSystemConfig.getDefaultRolloff());
//	    soundSystem.play("1");
		Sampler sampler = new Sampler();
		sampler.initialize();
		
		sampler.playSample(0);
		Thread.sleep(1000);
		sampler.playSample(1);
		Thread.sleep(5000);
		
		sampler.prevPreset();
		Thread.sleep(1000);
		sampler.playSample(0);
		Thread.sleep(1000);
		sampler.playSample(1);
		Thread.sleep(5000);
		
		sampler.prevPreset();
		sampler.playSample(0);
		Thread.sleep(1000);
		sampler.playSample(1);
		Thread.sleep(5000);
		
//		sampler.createDebugJson();
	}
	
	//Debug purposes only
	public void createDebugJson() {
		Gson gson = new Gson();
		Preset preset1 = new Preset( Arrays.asList( "/Users/Bill/Desktop/Samples/You are going to Hell!.wav", "/Users/Bill/Desktop/Samples/You are Immoral, but through Christ I love you.wav"));
		Preset preset2 = new Preset( Arrays.asList( "/Users/Bill/Desktop/Samples/Satanic Cartoons.wav", "/Users/Bill/Desktop/Samples/We will see how the Lord favors you.wav"));
		
		Presets presets = new Presets();
		presets.setPresets(Arrays.asList( preset1, preset2 ));
		
		String json = gson.toJson( presets );
		System.out.println( json );
	}
}
