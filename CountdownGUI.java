import javax.swing.*;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Creates a GUI for users to interact with a countdown
 * timer.
 * @author George Andrews, Joao Duarte
 * @version 1.1, Sprint 3
 * @see Countdown
 */
public class CountdownGUI {
    //Variables
    private Countdown countdown;
    private GuiButtonInput input;
    private java.util.Timer updateTimer;

    /**
     * Sets up the countdown variable
     * @param dynOSor
     * The parent to be pasted to countdown
     * @see Countdown
     */
    public CountdownGUI(DynOSor dynOSor){
        countdown = new Countdown(dynOSor);
    }

    /**
     * Displays the GUI for the user
     * to interact with, allows them to add/take
     * time from the timer, start and stop the clock.
     * The countdown continues even when the menu is not
     * visible.
     */
    public void showGuiMenu(){
        ArrayList<String> buttons = new ArrayList<>();
        buttons.add("Start Countdown");
        buttons.add("Pause Countdown");
        buttons.add("Stop Countdown");
        buttons.add("Add 1 Second");
        buttons.add("Add 1 Minute");
        buttons.add("Add 1 Hour");
        buttons.add("Take 1 Second");
        buttons.add("Take 1 Minute");
        buttons.add("Take 1 Hour");
        input = new GuiButtonInput(countdown.getTime(), buttons);
        input.setDispose(false);
        update();
        try {
            while (true) {
                input.resetButtonPressed();
                int selection = buttons.indexOf(input.getInput());
                switch (selection) {
                    case 0:
                        countdown.resumeCountdown();
                        break;
                    case 1:
                        countdown.pauseCountdown();
                        break;
                    case 2:
                        countdown.stopCountdown();
                        break;
                    case 3:
                        countdown.addTime(1);
                        input.setTitle(countdown.getTime());
                        break;
                    case 4:
                        countdown.addTime(60);
                        input.setTitle(countdown.getTime());
                        break;
                    case 5:
                        countdown.addTime(3600);
                        input.setTitle(countdown.getTime());
                        break;
                    case 6:
                        countdown.addTime(-1);
                        input.setTitle(countdown.getTime());
                        break;
                    case 7:
                        countdown.addTime(-60);
                        input.setTitle(countdown.getTime());
                        break;
                    case 8:
                        countdown.addTime(-3600);
                        input.setTitle(countdown.getTime());
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
     * Every second updates the label on the GUI to
     * the current time of the countdown.
     * @see Countdown
     */
    private void update() {
        updateTimer = new java.util.Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            // new thread
            @Override
            public void run() {
                // only start when running = true (when start button is pressed)
                input.setTitle(countdown.getTime());
            }
        }, 0, 100);
    }
}
