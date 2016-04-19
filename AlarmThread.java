import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import javax.sound.sampled.Clip;

/**
 * Class representing a thread in which all alarm handling functionality
 * is run in. If it is the minute for an alarm to go off - the alarm sound
 * is played. Reloads the alarm files once every five seconds.
 * @author Joshua Evans
 * @version Sprint 3, V1.3
 * @release 17/04/2016
 * @see Sounds, SoundEffect, Alarm
 */
public class AlarmThread implements Runnable{
	
	private ArrayList<Alarm> alarmList;
	private boolean threadRunning;
	private boolean shouldBePlayingAlarm;
	private DynOSor parent;
	private Thread alarmThread;
	
	/**
	 * Given a parent, from which it can access a sound library, this
	 * constructor initialises the AlarmThread and starts it.
	 * @param parent
	 */
	public AlarmThread(DynOSor parent){
		threadRunning = true;
		this.parent = parent;
		reloadAlarms();
		
		alarmThread = new Thread(this);
		alarmThread.start();
	}
	
	/**
	 * Main loop for the alarm thread. Checks whether an alarm should be sounding,
	 * and plays the alarm sound if one should be. 
	 */
	public void run() {
		while (threadRunning == true){
			Calendar now = Calendar.getInstance();
			for (int i = 0; i < alarmList.size(); i++){
				if (now.get(Calendar.HOUR_OF_DAY) == alarmList.get(i).getAlarmHour() && now.get(Calendar.MINUTE) == alarmList.get(i).getAlarmMinute() && alarmList.get(i).getIsEnabled() == true){
					shouldBePlayingAlarm = true;
					break;
				}
				else {
					shouldBePlayingAlarm = false;
				}
			}
			playAlarmSound();
			reloadAlarms();
			try{Thread.sleep(5000);} catch (InterruptedException ex){}
		}
	}

	/**
	 * If an alarm should be playing, this method plays the alarm sound.
	 */
	private void playAlarmSound(){
		if (shouldBePlayingAlarm == true){
			if (parent.getSoundLib().getAlarmSound().getSoundClip().isRunning() == false){
				parent.getSoundLib().getAlarmSound().getSoundClip().setFramePosition(0);
				parent.getSoundLib().getAlarmSound().getSoundClip().loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		else {
			parent.getSoundLib().getAlarmSound().getSoundClip().stop();
		}
	}
	
	/**
	 * Reloads alarm data from the alarm files.
	 */
	public synchronized void reloadAlarms(){
		try {
			alarmList = new ArrayList<Alarm>();
			File alarmDirectory = new File(Alarm.fileDirectory);
			File alarms[] = alarmDirectory.listFiles();
			for (int i = 0; i < alarms.length; i++) {
				alarmList.add(new Alarm(alarms[i]));
			}
		} catch (NullPointerException e) {
		}
	}
}
