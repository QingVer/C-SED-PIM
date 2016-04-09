import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Represents a To-Do list item, essentially a note with an associated true/false state
 * indicating whether or not it has been completed.
 * 
 * @author Joshua Evans
 * @version Sprint 1, V1.0
 */
public class ToDoItem extends Note{

	private boolean isComplete;	
	@Override public static String fileDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor" + System.getProperty("file.separator") + "ToDo";
	
	
	/**
	* Constructor given a file containing a todo list item's data.
	* Reads the data from the file and initialises the object.
	*
	* @param toDoItemFile The file containing the to-do item's data.
	*/
	public ToDoItem(File toDoItemFile) {
		this.noteFile = toDoItemFile;
		
		Scanner toDoReader = null;
		
		//Reads the isComplete value.
		try {
			toDoReader = new Scanner(this.noteFile);
			String isCompleteString = toDoReader.nextLine();
			
			if (isCompleteString.equals("true")){
				isComplete = true;
			}
			else {
				isComplete = false;
			}
			
			//Reads title string.
			titleText = toDoReader.nextLine();
			
			//Reads body text.
			while (toDoReader.hasNextLine()){
				bodyText = bodyText + "\n" + toDoReader.nextLine();
			}
			
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not read from the specified to-do item file because the file could not be found.");
		}
		catch (NoSuchElementException ex){
			System.err.println("Invalid To-Do Item File");
		}
		finally {
			toDoReader.close();
		}
	}
	
	/**
	* Given a title string, body text string and isComplete boolean value,
	* initialises the object and creates a new todo item file.
	* 
	* @param title The title text for the to-do item.
	* @param body The body text for the to-do item.
	* @param isComplete The completion state for this to-do item.
	*/
	public ToDoItem(String title, String body, boolean isComplete) {
		super(title, body);
		this.isComplete = isComplete;
		updateNoteFile();
	}
	
	/**
	* Sets the to-do item's completion value.
	* 
	* @param isComplete The to-do item's completion value.
	*/
	public void setIsComplete(boolean isComplete){
		this.isComplete = isComplete;
		updateNoteFile();
	}
	
	/**
	* Returns the to-do item's completion state.
	* 
	* @return The to-do item's completion state.
	*/
	public boolean getIsComplete(){
		return isComplete;
	}
	
	/**
	* Updates the contents of the to-do list's file.
	*/
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
