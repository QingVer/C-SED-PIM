import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class TimerGUI {
	// timerclock class
	private TimerClock clock;
	// timer label
	private JLabel timerLabel;
	// is stopwatch running
	private boolean running;

	/*
	 * Constructor. sets global variables
	 */
	public TimerGUI() {
		running = false;
		clock = new TimerClock();
	}

	/*
	 * creates and show GUI
	 */
	public void createAndShowGUI() {
		JFrame frame = new JFrame("Stopwatch");
		frame.setPreferredSize(new Dimension(400, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		// label that shows timer
		timerLabel = new JLabel("00:00:00");
		timerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		timerLabel.setFont(new Font("Consolas", Font.CENTER_BASELINE, 24));

		// panel to hold buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		// button for starting or restarting stopwatch
		JButton startOrRestart = new JButton("Start/Restart Stopwatch");
		startOrRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clock.startOrRestartWatch();
				running = true;
				// updates timer
				update();
			}
		});
		JButton pauseButton = new JButton("Pause Stopwatch");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if stopwatch is not running
				if (running == false)
					JOptionPane.showMessageDialog(frame, "Stopwatch is not running");
				else
					running = clock.pauseWatch();
			}
		});

		// button used to resume stopwatch
		JButton resumeButton = new JButton("Resume Stopwatch");
		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if timer is not paused
				if (running == true) {
					JOptionPane.showMessageDialog(frame, "Stopwatch is not paused");
				} else
					running = clock.resumeWatch();
			}
		});
		// adds buttons to panel
		buttonPanel.add(startOrRestart);
		buttonPanel.add(pauseButton);
		buttonPanel.add(resumeButton);

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
	private void update() {
		java.util.Timer updateTimer = new java.util.Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			// new thread
			@Override
			public void run() {
				// only start when running = true (when start button is pressed)
				if (running)
					timerLabel.setText(clock.timeRunning());
			}
		}, 0, 100);
	}
}
