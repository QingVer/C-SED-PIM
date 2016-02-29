import java.util.Scanner;

public class NoteMenuCLI {
	private Scanner userInputScanner;
	
	public NoteMenuCLI(Scanner userInputScanner){
		this.userInputScanner = userInputScanner;
	}
	
	public void showMainNoteMenu(){
		//TODO Implement root menu for notes
	}
	
	public void listNotes(){
		//TODO list all note titles.
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
		
		Note newNote = new Note(titleText, bodyText);
	}
	
}
