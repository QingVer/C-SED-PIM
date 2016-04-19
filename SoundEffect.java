import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Represents a sound clip containing music for use in the DynOSor
 * personal organiser application.
 * Encapsulates javax.sound.sampled.Clip in order to allow audio
 * playback.
 * 
 * To play a SoundEffect, use soundEffect.getSoundClip().start().
 * To have a SoundEffect looping, use .loop(Clip.LOOP_CONTINUOUSLY) instead of .start().
 * 
 * @author Joshua Evans
 * @version V1.0
 * @release 24/03/2016
 */
public class SoundEffect {
	
	private String soundEffectName;
	private String fileName;
	private Clip soundEffectClip;
	
	/**
	 * Constructor given the name and fileName of the SoundEffect as strings.
	 * Initialises private variables and then attempts to initialise the
	 * Clip by calling initialiseClip.
	 * 
	 * @param soundEffectName The Name of the Sound Effect.
	 * @param fileName The File containing the Sound Effect.
	 */
	public SoundEffect(String soundEffectName, String fileName){
		//Initialises private Strings
		this.soundEffectName = soundEffectName;
		this.fileName = fileName;
		
		try {
			initialiseClip(this.soundEffectName, new File(this.fileName).toURI().toURL());
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error Loading " + soundEffectName + " Sound - Malformed Sound File Path.", "Could Not Load " + soundEffectName + " Sound", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialises the Sound Effect Clip, given its name and the URL
	 * at which it can be found.
	 * 
	 * @param soundEffectName The Name of the Sound Effect.
	 * @param fileURL The Uniform Resource Location of the Sound Effect's File.
	 */
	private void initialiseClip(String soundEffectName, URL fileURL){	
		//Tries to load the given music file
		try {
	         //Sets up an audio stream piped from the audio file.
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileURL);
	         //Gets the clip resource.
	         soundEffectClip = AudioSystem.getClip();
	         //Opens the clip and reads sampled audio from the stream.
	         soundEffectClip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException ex) {
	    	  JOptionPane.showMessageDialog(new JFrame(), "Error Loading " + soundEffectName + " Sound - Incorrect File Type.", "Could Not Load " + soundEffectName + " Sound", JOptionPane.WARNING_MESSAGE);
	    	  ex.printStackTrace();
	      } catch (IOException ex) {
	    	  JOptionPane.showMessageDialog(new JFrame(), "Error Loading " + soundEffectName + " Sound - IOException Occurred.", "Could Not Load " + soundEffectName + " Sound", JOptionPane.WARNING_MESSAGE);
	    	  ex.printStackTrace();
	      } catch (LineUnavailableException ex) {
	    	  JOptionPane.showMessageDialog(new JFrame(), "Error Loading " + soundEffectName + " Sound - Couldn't Open Audio Line.", "Could Not Load " + soundEffectName + " Sound", JOptionPane.WARNING_MESSAGE);
	    	  ex.printStackTrace();
	      }
	}
	
	/**
	 * Returns the sound effect's name.
	 * 
	 * @return Returns the name of the Sound Effect.
	 */
	public String getSoundEffectName(){
		return soundEffectName;
	}
	
	/**
	 * Returns the loaded clip for this sound effect.
	 * 
	 * @return Returns the loaded clip for this sound effect.
	 */
	public Clip getSoundClip(){
		return soundEffectClip;
	}
}
