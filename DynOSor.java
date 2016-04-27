import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Entry class for the Dynamic Organiser and Sorter program. Represents the root
 * main menu for the application. Contains a constructor which initialises the
 * application and methods to navigate the application's other menu trees.
 *
 * This is for the initial GUI version of DynOSor.
 * 
 * @version Sprint 1, V1.0
 * @author George Andrews, Joshua Evans
 */
public class DynOSor {
	private static Image image = null;
	private Sounds soundLib;
	private AlarmThread alarmThread;
	private ContactMenuGUI contactMenu;
	private NoteMenuGUI noteMenu;
	private ToDoGUI toDoMenu;
	private AppointmentGUI appointmentMenu;
	private AlarmGUI alarmMenu;
	private AppointmentImporterGUI appointmentImporterMenu;
	private CountdownGUI countdownGUI;
	private TimerGUI timerGUI;
	public static String rootDirectory = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "DynOSor";

	/**
	 * Constructor with no parameters which simply checks to ensure that the
	 * applications directories exist, creating them if they don't, as well as
	 * initialising the application's menus.
	 */
	public DynOSor() {
		fileCheck();
		URL url = null;
		try {
			url = new URL("https://raw.githubusercontent.com/QLVermeire/C-SED-PIM/master/DynOSor.jpg");
		} catch (MalformedURLException e) {
		}
		image = Toolkit.getDefaultToolkit().createImage(url);
		soundLib = new Sounds();
		alarmThread = new AlarmThread(this);
		contactMenu = new ContactMenuGUI();
		noteMenu = new NoteMenuGUI();
		toDoMenu = new ToDoGUI();
		appointmentMenu = new AppointmentGUI();
		alarmMenu = new AlarmGUI();
		appointmentImporterMenu = new AppointmentImporterGUI();
		countdownGUI = new CountdownGUI(this);
		timerGUI = new TimerGUI();
	}

	/**
	 * Checks to make sure that the directories the program needs to function
	 * exist, and creates them if they do not.
	 */
	public void fileCheck() {
		File rootDir = new File(rootDirectory);
		File contactDir = new File(Contact.fileDirectory);
		File noteDir = new File(Note.fileDirectory);
		File toDoDir = new File(ToDoItem.fileDirectory);
		File appointmentDir = new File(Appointment.fileDirectory);
		File alarmDir = new File(Alarm.fileDirectory);

		if (!rootDir.exists()) {
			rootDir.mkdir();
		}
		if (!contactDir.exists()) {
			contactDir.mkdir();
		}
		if (!noteDir.exists()) {
			noteDir.mkdir();
		}
		if (!toDoDir.exists()) {
			toDoDir.mkdir();
		}
		if (!appointmentDir.exists()) {
			appointmentDir.mkdir();
		}
		if (!alarmDir.exists()) {
			alarmDir.mkdir();
		}

	}

	/**
	 * Shows a set of menu options and opens the selected menu
	 * using a GUI
	 * @see GuiButtonInput
	 */
	public void showGuiMenu() {
		try {
			while (true) {
				ArrayList<String> options = new ArrayList<>();
				options.add("Contacts");
				options.add("Notes");
				options.add("TODOs");
				options.add("Appointments");
				options.add("Import Appointment File");
				options.add("Alarms");
				options.add("Countdown Timer");
				options.add("Stopwatch");
				options.add("Reload DynOSor");
				GuiButtonInput input = new GuiButtonInput("Main Menu", options);
				int selection = options.indexOf(input.getInput());

				switch (selection) {
				case 0:
					contactMenu.showGuiMenu();
					break;
				case 1:
					noteMenu.showGuiMenu();
					break;
				case 2:
					toDoMenu.showGuiMenu();
					break;
				case 3:
					appointmentMenu.showGuiMenu();
					break;
				case 5:
					alarmMenu.showGUI();
					break;
				case 4:
					appointmentImporterMenu.showGUIMenu();
					refresh();
					break;
				case 6:
					countdownGUI.showGuiMenu();
					break;
				case 7:
					timerGUI.showGuiMenu();
					break;
					case 8:
						refresh();
						break;
				}
			}
		} catch (QuitException e) {
			System.exit(0);
		}
	}

	/**
	 * Entry method for DynOSor. Instantiates a new DynOSor object and calls the
	 * showMainMenu method.
	 */
	public static void main(String[] args) {
		DynOSor mainMenu = new DynOSor();
		mainMenu.startProgram();
	}

	/**
	 * Returns the logo
	 * 
	 * @return
	 */
	public static Image getLogo() {
		return image;
	}

	/**
	 * Validates the user should be able to start
	 * the program by asking for a pincode
	 * @see PINGui
	 */
	public void startProgram() {
		PINGui.showGui();
		showGuiMenu();
	}

	/**
	 * Reloads all the menus so any new
	 * files are imported into the program
	 */
	public void refresh(){
		contactMenu = new ContactMenuGUI();
		noteMenu = new NoteMenuGUI();
		toDoMenu = new ToDoGUI();
		appointmentMenu = new AppointmentGUI();
		alarmMenu = new AlarmGUI();
	}

	/**
	 * @return
	 * soundLib
     */
	public Sounds getSoundLib() {
		return soundLib;
	}
}