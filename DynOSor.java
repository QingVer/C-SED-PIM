import java.io.File;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

/**
 * Entry class for the Dynamic Organiser and Sorter program.
 * Represents the root main menu for the application. Contains
 * a constructor which initialises the application and methods 
 * to navigate the application's other menu trees. 
 *
 * This is for the initial CLI version of DynOSor.
 */
public class DynOSor{
	private Scanner userInputScanner;
	private NoteMenuCLI noteMenu;
	private ContactMenuCLI contactMenu;
	private static String rootDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor";

	
	/**
	 * Constructor with no parameters which simply checks
	 * to ensure that the applications directories exist,
	 * creating them if they don't, as well as initialising
	 * the application's menus.
	 */
	public DynOSor(){
		fileCheck();

		Scanner userInputScanner = new Scanner(System.in);
		contactMenu = new ContactMenuCLI(userInputScanner);
		contactMenu.showMainContactMenu();
	}
	
	/**
	* Checks to make sure that the directories the program needs
	* to functons exist, and creates them if they do not.
	*/
	public fileCheck(){
		File rootDir = new File(rootDirectory);
		File contactDir = new File(contactsDirectory);
		File noteDir = new File(Note.notesDirectory);
		
		if(rootDir.exists() == false){
			rootDir.mkdir();
		}
		if(contactDir.exists() == false){
			contactDir.mkdir();
		}
		if(noteDir.exists() == false){
			noteDir.mkdir();
		}

	}

	/**
	 * Checks to make sure that the directories the program needs
	 * to functons exist, and creates them if they do not.
	 */
	public void fileCheck() {
		File rootDir = new File(rootDirectory);
		File contactDir = new File(Contact.contactsDirectory);
		File noteDir = new File(Note.notesDirectory);

		if (!rootDir.exists()) {
			rootDir.mkdir();
		}
		if (!contactDir.exists()) {
			contactDir.mkdir();
		}
		if (!noteDir.exists()) {
			noteDir.mkdir();
		}
	}

	/**
	 * Shows a set of menu options to the user and continually monitors
	 * their command line input. Once this method exits, the program closes.
	 */
	public void showMainMenu(){
		Scanner userInputScanner = new Scanner(System.in);

		//Initialises other menu trees
		contactMenu = new ContactMenuCLI(userInputScanner);
		noteMenu = new NoteMenuCLI(userInputScanner);

		while (userInputScanner.hasNext()) {
			//TODO Implement the main menu CLI.
		}
	}

	public static void main(String[] args){
		DynOSor mainMenu = new DynOSor();
		mainMenu.showMainMenu();
	}
}
