import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Entry class for the Dynamic Organiser and Sorter program.
 * Represents the root main menu for the application. Contains
 * a constructor which initialises the application and methods
 * to navigate the application's other menu trees.
 *
 * This is for the initial GUI version of DynOSor.
 * 
 * @version Sprint 1, V1.0
 */
public class DynOSor{
	private static Image image = null;
	public static String rootDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor";

	/**
	 * Constructor with no parameters which simply checks
	 * to ensure that the applications directories exist,
	 * creating them if they don't, as well as initialising
	 * the application's menus.
	 */
	public DynOSor(){
		fileCheck();
		URL url = null;
		try {
			url = new URL("https://raw.githubusercontent.com/QLVermeire/C-SED-PIM/master/DynOSor.jpg");
		} catch (MalformedURLException e){
			//
		}
		image = Toolkit.getDefaultToolkit().createImage(url);
	}

	/**
	 * Checks to make sure that the directories the program needs
	 * to function exist, and creates them if they do not.
	 */
	public void fileCheck(){
		File rootDir = new File(rootDirectory);
		File contactDir = new File(Contact.fileDirectory);
		File noteDir = new File(Note.fileDirectory);
		File toDoDir = new File(ToDoItem.fileDirectory);
		File appointmentDir = new File(Appointment.fileDirectory);
		
		if(!rootDir.exists()){
			rootDir.mkdir();
		}
		if(!contactDir.exists()){
			contactDir.mkdir();
		}
		if(!noteDir.exists()){
			noteDir.mkdir();
		}
		if(!toDoDir.exists()){
			toDoDir.mkdir();
		}
		if(!appointmentDir.exists()){
			appointmentDir.mkdir();
		}

	}

	/**
	 * Shows a set of menu options to the user and continually monitors
	 * their command line input. Once this method exits, the program closes.
	 */
	public void showGuiMenu(){
		ContactMenuGUI contactMenu = new ContactMenuGUI();
		NoteMenuGUI noteMenu = new NoteMenuGUI();
		ToDoGUI toDoMenu = new ToDoGUI();
		AppointmentGUI appointmentMenu = new AppointmentGUI();
		AppointmentImporterGUI appointmentImporterMenu = new AppointmentImporterGUI();
		try {
			while (true) {
				ArrayList<String> options = new ArrayList<>();
				options.add("Contacts");
				options.add("Notes");
				options.add("TODOs");
				options.add("Appointments");
				options.add("Import Appointment File");

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
					case 4:
						appointmentImporterMenu.showGUIMenu();	
				}
			}
		} catch (QuitException e){
			System.exit(0);
		}
	}

	/**
	 * Entry method for DynOSor. Instantiates a new DynOSor object
	 * and calls the showMainMenu method.
	 */
	public static void main(String[] args) {
		DynOSor mainMenu = new DynOSor();
		mainMenu.startProgram();
	}

	/**
	 * Returns the logo
	 * @return
     */
	public static Image getLogo(){
		return image;
	}

	public void startProgram(){
		PINGui.showGui();
		showGuiMenu();
	}
}
