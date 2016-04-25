
public class TimerClock {
	// start time
	private long startTime;
	// holds paused time
	private long timeBuffer;

	/*
	 * Constructor. sets global variables
	 */
	public TimerClock() {
		// timer is -1 (
		timeBuffer = -1;
	}

	/*
	 * starts or restarts watch
	 */
	public void startOrRestartWatch() {
		startTime = System.currentTimeMillis();
		timeBuffer = -1;
	}

	/*
	 * @returns watch is not running (pauses watch)
	 */
	public boolean pauseWatch() {
		timeBuffer = System.currentTimeMillis() - startTime;
		return false;
	}

	/*
	 * @returns watch is running (resumes watch)
	 */
	public boolean resumeWatch() {
		startTime = System.currentTimeMillis();
		return true;
	}

	/*
	 * method was adapted from
	 * http://introcs.cs.princeton.edu/java/stdlib/Stopwatch.java.html
	 * 
	 * @returns String, the time since the timer was started in sexagesimal
	 */
	public String timeRunning() {
		long currentTime = System.currentTimeMillis();

		// if timer was paused
		if (timeBuffer != -1) {
			currentTime += timeBuffer;
		}

		return secondsToString((currentTime - startTime));

	}

	/*
	 * @returns String of converted seconds into a nice format of (eg) 00:01:23
	 */
	public String secondsToString(long time) {
		// convert ms to h
		long hours = (time) / 1000 / 3600;
		String hoursString = formatTime(hours);

		// converts ms to min, taking h into account
		long minutes = ((((time) / 1000) - hours * 3600)) / 60;
		String minutesString = formatTime(minutes);

		// converts ms to s, taking min and h into account
		long seconds = ((((time - startTime) / 1000) - hours * 3600) - minutes * 60);
		String secondsString = formatTime(seconds);

		return hoursString + ":" + minutesString + ":" + secondsString;

	}

	/*
	 * @return String of either 0x or x (x being a long)
	 */
	public String formatTime(long l) {
		// if l is one digit
		if ((l + "").length() == 1) {
			return "0" + l;
		} else {
			return l + "";
		}
	}
}
