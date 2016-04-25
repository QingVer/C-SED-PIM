import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CountdownGUI {
	// time counting down
	private long time;
	// is timer counting down
	private boolean running;
	// timer obj
	private TimerClock clock;
	private JLabel timerLabel;
	// if user has dismissed the timer
	private boolean dismissed;
	// number of threads
	private int threadsRunning;
	// check if a mintue has passed
	private int minutePassed;

	/*
	 * Constructor. sets global variables
	 */
	public CountdownGUI() {
		minutePassed = 0;
		threadsRunning = -1;
		time = 0;
		running = false;
		clock = new TimerClock();
		dismissed = false;
	}

	/*
	 * Allows user to choose a time to countdown from in seconds
	 */
	public void chooseTimeGUI() {
		JFrame initialFrame = new JFrame("Enter a time...");
		initialFrame.setPreferredSize(new Dimension(450, 100));
		initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialFrame.setResizable(false);
		initialFrame.setLayout(new FlowLayout());

		JLabel instructions = new JLabel("Please enter a time in seconds...");
		JTextField timeBox = new JTextField();
		timeBox.setPreferredSize(new Dimension(100, timeBox.getPreferredSize().height));
		JButton okButton = new JButton("Continue");

		initialFrame.getContentPane().add(instructions);
		initialFrame.getContentPane().add(timeBox);
		initialFrame.getContentPane().add(okButton);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// get jtextbox text
					time = Integer.parseInt(timeBox.getText());
					// if time is negative, make it positive
					if (time < 0) {
						time *= -1;
					}
					createAndShowGUI();
					initialFrame.setVisible(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(initialFrame, "Invalid input...");
				}
			}
		});
		initialFrame.pack();
		initialFrame.setVisible(true);
	}

	/*
	 * creates and show GUI
	 */
	private void createAndShowGUI() {
		JFrame frame = new JFrame("Timer");
		frame.setPreferredSize(new Dimension(400, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		// label that shows timer
		timerLabel = new JLabel(clock.secondsToString(time * 1000));
		timerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		timerLabel.setFont(new Font("Consolas", Font.CENTER_BASELINE, 24));

		// panel to hold buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		// resets the time by opening the previous gui
		JButton resetButton = new JButton("Reset time");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseTimeGUI();
				frame.dispose();
			}
		});

		// allowsuser to dismiss time
		JButton dismissButton = new JButton("Dismiss timer");
		dismissButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dismissed = true;
			}
		});

		// button for starting or restarting stopwatch
		JButton startOrRestart = new JButton("Start/Resume Timer");
		startOrRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (running == false) {
					running = true;
					// updates timer
					update(frame);
				}
			}
		});
		JButton pauseButton = new JButton("Pause Timer");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if stopwatch is not running
				if (running == false)
					JOptionPane.showMessageDialog(frame, "Timer is not running");
				else
					running = false;
			}
		});

		// adds buttons to panel
		buttonPanel.add(startOrRestart);
		buttonPanel.add(pauseButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(dismissButton);

		// adds components and panels to frame
		frame.getContentPane().add(timerLabel);
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));
		frame.getContentPane().add(buttonPanel);

		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * based off http://stackoverflow.com/questions/18926839/timer-stopwatch-gui
	 * Updates label via threading
	 */
	private void update(JFrame frame) {
		threadsRunning++;
		if (threadsRunning > 0) {
			threadsRunning--;
			return;
		}
		java.util.Timer updateTimer = new java.util.Timer();
		// update every second
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			// new thread
			@Override
			public void run() {
				// only start when running = true (when start button is pressed)
				if (running) {
					// if time has ended
					if (time <= 0 || dismissed == true) {
						// play sound
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(frame, "Time has ended!");
						System.exit(0);
					}
					if (minutePassed == 60) {
						// play sound
						minutePassed = 0;
						Toolkit.getDefaultToolkit().beep();
					}
					// time supposed to be in ms hence time*1000
					timerLabel.setText(clock.secondsToString(time * 1000));
					// decrease time by 1
					time--;
					minutePassed++;
				}
			}
		}, 0, 1000);
	}
}
