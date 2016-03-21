import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Represents a To-Do list item, essentially a note with an associated true/false state.
 * 
 * @author Joshua Evans
 */
public class ToDoItem extends Note{

	private boolean isComplete;
	public static final String notesDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor" + System.getProperty("file.separator") + "ToDo";
	
	public ToDoItem(File toDoItemFile) {
		this.noteFile = toDoItemFile;
		
		Scanner toDoReader = null;
		
		try {
			toDoReader = new Scanner(this.noteFile);
			String isCompleteString = toDoReader.nextLine();
			
			if (isCompleteString.equals("true")){
				isComplete = true;
			}
			else {
				isComplete = false;
			}
			
			titleText = toDoReader.nextLine();
			
			while (toDoReader.hasNextLine()){
				bodyText = bodyText + "\n" + toDoReader.nextLine();
			}
			
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not read from the specified to-do item file because the file could not be found.");
		}
		finally {
			toDoReader.close();
		}
	}
	
	public ToDoItem(String title, String body, boolean isComplete) {
		super(title, body);
		this.isComplete = isComplete;
	}
	
	public void setIsComplete(boolean isComplete){
		this.isComplete = isComplete;
	}
	
	public boolean getIsComplete(){
		return isComplete;
	}
	
	@Override
	public boolean updateNoteFile(){
		boolean wasSuccessful = false;
		PrintWriter toDoWriter = null;
		
		try {
			//Writes the title text to the note file, followed by the body text.
			toDoWriter = new PrintWriter(noteFile);
			toDoWriter.println((new Boolean(isComplete)).toString());
			toDoWriter.println(titleText);
			toDoWriter.println(bodyText);
			
			//If the method reaches this point, it has successfully completed the writing operation.
			wasSuccessful = true;
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not write to specified note file because the file could not be found.");
		}
		finally {
			//Closes the file writer
			toDoWriter.close();
		}
		
		return wasSuccessful;
	}
}
