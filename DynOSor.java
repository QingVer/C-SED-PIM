import java.io.File;
import java.util.Scanner;

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
		userInputScanner = new Scanner(System.in);
	}

	/**
	 * Checks to make sure that the directories the program needs
	 * to functons exist, and creates them if they do not.
	 */
	public void fileCheck(){
		File rootDir = new File(rootDirectory);
		File contactDir = new File(Contact.contactsDirectory);
		File noteDir = new File(Note.notesDirectory);

		if(!rootDir.exists()){
			rootDir.mkdir();
		}
		if(!contactDir.exists()){
			contactDir.mkdir();
		}
		if(!noteDir.exists()){
			noteDir.mkdir();
		}

	}

	/**
	 * Shows a set of menu options to the user and continually monitors
	 * their command line input. Once this method exits, the program closes.
	 */
	public void showMainMenu(){
		//Initialises other menu trees
		contactMenu = new ContactMenuCLI(userInputScanner);
		noteMenu = new NoteMenuCLI(userInputScanner);
		try {
			while (true) {
				System.out.println("##### DynOSor #####");
				System.out.println("\nWhich Menu Would You Like To Enter:" +
						"\n- Contacts" +
						"\n- Notes" +
						"\nEnter Selection: ");
				if(getInput().toUpperCase().equals("CONTACTS")){
					contactMenu.showMainContactMenu();
				} else if(getInput().toUpperCase().equals("NOTES")){
					noteMenu.showMainNoteMenu();
				} else {
					System.err.println("Not A Vaild Selection");
				}
			}
		} catch (QuitException e){
			return;
		}
	}

	public static void main(String[] args){
		DynOSor mainMenu = new DynOSor();
		mainMenu.showMainMenu();
	}

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
