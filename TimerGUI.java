import java.util.ArrayList;
import java.util.TimerTask;

/**
 * This class displays a timer that ticks up to the
 * user, and allows them to pause/start and reset the timer
 * @author George Andrews
 * @version 1.0
 */
public class TimerGUI {
    //Variables
    private TimerClock timerClock;
    private GuiButtonInput input;
    private java.util.Timer updateTimer;

    /**
     * Sets up the timerClock variable.
     */
    public TimerGUI() {
        timerClock = new TimerClock();
    }

    /**
     * Displays a Gui where the user can
     * pause, start and reset the timer.
     */
    public void showGuiMenu() {
        ArrayList<String> buttons = new ArrayList<>();
        buttons.add("Start Timer");
        buttons.add("Pause Timer");
        buttons.add("Reset Timer");
        input = new GuiButtonInput(timerClock.getTime(), buttons);
        input.setDispose(false);
        update();
            try {
                while (true) {
                    input.resetButtonPressed();
                    int selection = buttons.indexOf(input.getInput());
                    switch (selection) {
                        case 0:
                            timerClock.resumeWatch();
                            break;
                        case 1:
                            timerClock.pauseWatch();
                            break;
                        case 2:
                            timerClock.restartWatch();
                            break;
                    }
                }
            } catch (QuitException e) {
                if(updateTimer != null) {
                    updateTimer.cancel();
                }
                input.dispose();
            }
        }

    /**
     * Updates the title label text to the
     * time on the timer.
     */
    private void update() {
        updateTimer = new java.util.Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            // new thread
            @Override
            public void run() {
                // only start when running = true (when start button is pressed)
                    input.setTitle(timerClock.getTime());
            }
        }, 0, 100);
    }
}

