import java.io.File;
import java.util.Scanner;

public class NoteMenuCLI {
	private Scanner userInputReader;
	@SuppressWarnings("unused") private Note noteFile;
	
	public NoteMenuCLI(Scanner userInputReader){
		this.userInputReader = userInputReader;
	}
	/*
	 * Displays a main menu in which the user can go back to the top level
	 * menu, create a new note or view his/her notes 
	 */
	public void showMainNoteMenu(){
		//boolean used to check if user wants to quit
		boolean selectionExit = false;
		while(!selectionExit){
			System.out.println("(1) Create a note\n(2) View Notes\n(3) Go back");
			String userInputString = userInputReader.nextLine();
			
			//checks if user has input is valid
			if(userInputString.length() == 1){
				switch(userInputString.charAt(0)){
				case '1':
					getNewNoteInfo();
					break;
				case '2':
					listNotes();
					break;
				case '3':
					//TODO go back to main menu if seletionExit is true
					selectionExit = true;
					break;
				default:
					System.err.println("Please enter a valid option...");
					break;
				}
			}else{
				System.err.println("Please enter a valid option...");
			}
		}
	}
	
	/*
	 * Displays all notes listed in the directory in which all notes are store.
	 * If notes are empty, it will return to the main menu and if not,
	 * user can select a note numerically and get an option menu (selectedNoteCommands())
	 * */
	public void listNotes(){
		while(true){
		
		if(new File(Note.fileDirectory).listFiles().length == 0){
			System.err.println("Directory is empty, please create a note first...");	
		}
			else {
				File files[] = new File(Note.fileDirectory).listFiles();
	
				System.out.println("Select a note via number selection");
				int amountOfNotes = files.length;
			
				for(int i = 0; i < files.length; i++){
					System.out.println("(" + (i) + ") " + files[i].getName());
				}
				
				String userInputString = userInputReader.nextLine();
		
				//if the input of the user is solely numerical and the part of the list of notes
				if(userInputString.matches("[0-9]+") && Integer.parseInt(userInputString) < amountOfNotes){
					selectedNoteCommands(new Note(files[Integer.parseInt(userInputString)]));		
				}
				else if (userInputString.toUpperCase().trim().equals("QUIT")){
					break;
				}
				else{
					System.err.println("Please enter a valid option...");
				}
			}
		}
	}
	
	/*
	 * Displays a menu that allows the user to edit a note,
	 * delete a note, update title, update body or both
	 * */
	public void selectedNoteCommands(Note note){
		while(true){
			System.out.println("(1) Update title\n(2) Update Body\n(3) Delete Note");
		

			String userInputString = userInputReader.nextLine();
		
			if(Character.isDigit(userInputString.charAt(0))){
				switch(Integer.parseInt(userInputString)){
					case 1:
						System.out.println("Please enter a title: ");
						note.setTitle(userInputReader.nextLine());
						break;
					case 2:
						System.out.println("\nPlease enter the body text for your note - use \\n to indicate new lines");
						note.setTitle(userInputReader.nextLine());
						break;
					case 3:
						note.deleteNote();
						break;
					default:
						System.err.println("Please enter a valid option...");
						break;
				}
			}
			else if (userInputString.toUpperCase().trim().equals("QUIT")){
				break;
			}
			else{
				System.err.println("Please enter a valid option...");
			}
		}
	}
	
	public void getNewNoteInfo(){
		String titleText = null;
		String bodyText = null;
		
		System.out.println("Please enter a title for your note:");
		titleText = userInputReader.nextLine();
		System.out.println("\nPlease enter the body text for your note - use \\n to indicate new lines");
		bodyText = userInputReader.nextLine();
		
		noteFile = new Note(titleText, bodyText);
	}
	
}
