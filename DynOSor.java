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
	private NoteMenuCLI noteMenu;
	private ContactMenuCLI contactMenu;
	
	/**
	 * Constructor with no parameters which simply checks
	 * to ensure that the applications directories exist,
	 * creating them if they don't, as well as initialising
	 * the application's menus.
	 */
	public DynOSor(){
		/* TODO Check that the following directories exist:
		 * 		user.home/DynOSor
		 * 		user.home/DynOSor/Contacts
		 * 		user.home/DynOSor/Notes
		 */
	}
	
	public void showMainMenu(){
		Scanner userInputScanner = new Scanner(System.in);
		
		//Initialises other menu trees
		contactMenu = new ContactMenuCLI(userInputScanner);
		noteMenu = new NoteMenuCLI(userInputScanner);
		
		while(userInputScanner.hasNext() == true){
			//TODO Implement the main menu CLI.
		}
	}
	
	public static void main(String[] args){
		DynOSor mainMenu = new DynOSor();
		mainMenu.showMainMenu();
	}
}