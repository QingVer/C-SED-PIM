import javax.sound.sampled.Clip;
import java.time.Duration;

/**
 * Creates a countdown timer that can be paused and stopped.
 * Plays a sound once the counter reaches 0
 * @author George Andrews, Joao Duarte
 * @version 1.1, Sprint 3
 */
public class Countdown implements Runnable{
    //Variables
    private Thread thread;
    private int time;
    private boolean running;
    private DynOSor parent;

    /**
     * Constructor.
     * Sets up all the variables and starts the thread
     * that controls the 'ticking' of the clock.
     * @param parent
     * The parent interface the sound should be played on.
     */
    public Countdown(DynOSor parent){
        this.parent = parent;
        time = 0;
        running = false;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Resumes the ticking of the clock.
     */
    public void resumeCountdown(){
        running = true;
    }

    /**
     * Stops the clock at the current time until
     * resumed.
     */
    public void pauseCountdown(){
        running = false;
    }

    /**
     * Stops the countdown and sets the time to 0
     */
    public void stopCountdown(){
        running = false;
        time = 0;
    }

    /**
     * Adds the given time to the countdown
     * if the result is above 0.
     * @param time
     * The time to be added (can be negative)
     */
    public void addTime(int time){
        int newTime = this.time + time;
        if (newTime >= 0){
            this.time = newTime;
        }
    }

    /**
     * Returns the time left on the clock in a
     * 'digital clock' format
     * @return
     * The time left as a string.
     */
    public String getTime(){
        return secondsToString(time);
    }

    /**
     * Converts the given time in seconds to
     * a digital clock format
     * @param timeSec
     * The time to convert
     * @return
     * The time in a 'Digital clock format'
     */
    private String secondsToString(int timeSec){
        Duration time = Duration.ofSeconds(timeSec);
        String output = (int) time.toHours() + ":";
        if(output.length() == 2){
            output = "0" + output;
        }
        time = time.minusHours((int) time.toHours());
        String addition = (int) time.toMinutes() + ":";
        if(addition.length() == 2){
            addition = "0" + addition;
        }
        output += addition;
        time = time.minusMinutes((int) time.toMinutes());
        addition = (int) time.getSeconds() + "";
        if(addition.length() == 1){
            addition = "0" + addition;
        }
        output += addition;
        return output;
    }

    /**
     * Every second if the clock is running
     * decrements the clock. Makes a sound when
     * the timer reaches 0.
     */
    @Override
    public void run() {
        try {
            while (true){
                if(running && time > 0){
                    time--;
                }
                if (running && time == 0){
                    parent.getSoundLib().getAlarmSound().getSoundClip().setFramePosition(0);
                    parent.getSoundLib().getAlarmSound().getSoundClip().loop(Clip.LOOP_CONTINUOUSLY);
                    running = false;
                    Thread.sleep(2000);
                }
                Thread.sleep(1000);
            }

        } catch (InterruptedException e){
            //
        }
    }
}
