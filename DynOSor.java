import java.io.File;
import java.util.Scanner;

/**
 * Entry class for the Dynamic Organiser and Sorter program.
 * Represents the root main menu for the application. Contains
 * a constructor which initialises the application and methods
 * to navigate the application's other menu trees.
 *
 * This is for the initial CLI version of DynOSor.
 * 
 * @version Sprint 1, V1.0
 */
public class DynOSor{
	private Scanner userInputScanner;
	private NoteMenuCLI noteMenu;
	private ContactMenuCLI contactMenu;
	private ToDoCLI toDoMenu;
	private static String rootDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor";


	/**
	 * Constructor with no parameters which simply checks
	 * to ensure that the applications directories exist,
	 * creating them if they don't, as well as initialising
	 * the application's menus.
	 */
	public DynOSor(){
		fileCheck();
		userInputScanner = new Scanner(System.in);
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
		//File appointmentDir = new File(Appointment.fileDirectory);
		
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
		//if(!appointmentDir.exists()){
		//	appointmentDir.mkdir();
		//}

	}

	/**
	 * Shows a set of menu options to the user and continually monitors
	 * their command line input. Once this method exits, the program closes.
	 */
	public void showMainMenu(){
		//Initialises other menu trees
		contactMenu = new ContactMenuCLI(userInputScanner);
		noteMenu = new NoteMenuCLI();
		toDoMenu = new ToDoCLI(userInputScanner);
		try {
			while (true) {
				System.out.println("##### DynOSor #####");
				System.out.println("\nWhich Menu Would You Like To Enter:" +
						"\n- Contacts" +
						"\n- Notes" +
						"\n- ToDo" +
						"\nEnter Selection: ");
				String input = getInput();
				if(input.toUpperCase().equals("CONTACTS")){
					contactMenu.showMainContactMenu();
				} else if(input.toUpperCase().equals("NOTES")){
					noteMenu.showMainNoteMenu();
				} else if(input.toUpperCase().equals("TODO")) {
					toDoMenu.showMainToDoMenu();
				} else {
					System.err.println("Not A Valid Selection");
				}
			}
		} catch (QuitException e){
			return;
		}
	}
	
	/**
	 * Entry method for DynOSor. Instantiates a new DynOSor object
	 * and calls the showMainMenu method.
	 */
	public static void main(String[] args){
		DynOSor mainMenu = new DynOSor();
		mainMenu.showMainMenu();
	}
	
	/**
	 * Gets input from the user via the command line.
	 * 
	 * @return The String entered by the user via the command line.
	 */
	private String getInput() throws QuitException {
		while (true) {
			String input = userInputScanner.nextLine();
			if (input.equals("")) {
				System.out.println("Please Enter Something: ");
			} else if (input.toUpperCase().equals("QUIT")) {
				throw new QuitException();
			} else {
				return input;
			}
		}
	}
}
