/**
 * Represents a library of all sounds that might need to be
 * played in the DynOSor personal organiser application.
 * 
 * ALL SOUNDS MUST BE 16-BIT .WAV FILES, ELSE THEY WILL NOT
 * LOAD PROPERLY USING THIS CLASS.
 * 
 * 
 * @author Joshua Evans
 * @version V1.0
 * @release 24/03/2016
 */
public class Sounds {

	private SoundEffect alarmSound;
	
	/**
	 * Constructor which initialises the SoundEffect Objects.
	 */
	public Sounds(){
		alarmSound = new SoundEffect("Alarm Sound", "alarm.wav");
	}
	
	public SoundEffect getAlarmSound(){
		return alarmSound;
	}

}
