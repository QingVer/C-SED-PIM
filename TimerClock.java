import java.time.Duration;

/**
 * Creates a timer that whilst running
 * increments up once every second. Can be
 * pause and reset back to 0.
 * @author George Andrews, Joao Duarte
 * @version 1.1
 */
public class TimerClock implements Runnable {
	//Variables
	private Thread thread;
	private int time;
	private boolean running;

	/**
	 * Constructor. sets global variables and
	 * starts the thread that controls the 'ticks'
	 */
	public TimerClock() {
		running = false;
		time = 0;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Sets the time back to 0.
	 */
	public void restartWatch() {
		time = 0;
	}

	/**
	 * Stops the timer 'ticking'
	 */
	public void pauseWatch() {
		running = false;
	}

	/**
	 * If paused sets the clock 'ticking' again.
	 */
	public void resumeWatch() {
		running = true;
	}

	/**
	 * @return
	 * Returns the time of the timer in a
	 * digital clock format.
     */
	public String getTime(){
		return secondsToString(time);
	}

	/**
	 * Converts a time in seconds to a digital
	 * clock format (hh:mm:ss)
	 * @param timeSec
	 * The time to convert.
	 * @return
	 * The converted time in a string format
     */
	public String secondsToString(int timeSec){
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
	 * If running increments the timer
	 */
	@Override
	public void run() {
		try {
			while (true){
				if(running){
					time++;
				}
				Thread.sleep(1000);
			}

		} catch (InterruptedException e){
			//
		}
	}
}
