import java.io.File;
import java.util.Scanner;

public class NoteMenuCLI {
	private Scanner userInputReader;
	private Note noteFile;
	
	public NoteMenuCLI(){

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
			userInputReader = new Scanner(System.in);
			String userInputString = userInputReader.nextLine();
			
			//checks if user has input is valid
			if(userInputString.length() == 1){
				switch(userInputString.charAt(0)){
				case 1:
					getNewNoteInfo();
					break;
				case 2:
					listNotes();
					break;
				case 3:
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
		if(new File(noteFile.getNotesDirectory()).listFiles().length == 0){
			System.err.println("Directory is empty, please create a note first...");
			showMainNoteMenu();
			
		}else{
			System.out.println("Select a note via number selection");
			int amountOfNotes = (Integer)(new File(noteFile.getNotesDirectory()).listFiles().length);
		
			for(int i = 0; i < amountOfNotes; i++){
				System.out.println("(" + (i+1) + ") Note"+(i));
			}
		
			while(true){
				userInputReader = new Scanner(System.in);
				String userInputString = userInputReader.nextLine();
		
				//if the input of the user is solely numerical and the part of the list of notes
				if(userInputString.length() == (amountOfNotes + "").length() && userInputString.matches("[0-9]+") && Integer.parseInt(userInputString) <= amountOfNotes && Integer.parseInt(userInputString) != 0){
					File file[] = (new File(noteFile.getNotesDirectory()).listFiles());
				
					for(File f : file){
						//TODO check if this works even if the filename ends with an .extension
						if(f.getName().endsWith(userInputString)){
							selectedNoteCommands(new Note(f));
							
							System.out.println();
							showMainNoteMenu();
							break;
						}	
					}
				}else{
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
		
			userInputReader = new Scanner(System.in);
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
			}else{
				System.err.println("Please enter a valid option...");
			}
		}
	}
	
	public void getNewNoteInfo(){
		String titleText = null;
		String bodyText = null;
		Scanner noteInfoReader = new Scanner(System.in);
		
		System.out.println("Please enter a title for your note:");
		titleText = noteInfoReader.nextLine();
		System.out.println("\nPlease enter the body text for your note - use \\n to indicate new lines");
		bodyText = noteInfoReader.nextLine();
		
		noteInfoReader.close();
		
		noteFile = new Note(titleText, bodyText);
	}
	
}
