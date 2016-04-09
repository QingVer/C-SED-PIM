import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class representing a note - a file containing a body
 * of text and that body's title.
 * This class contains constructors and methods for creating,
 * modifying and deleting these notes.
 * 
 * @author Joshua Evans
 * @version Sprint 2, V1.2
 */
public class Note{
	
	protected String titleText;
	protected String bodyText;
	protected File noteFile;
	public static String fileDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor" + System.getProperty("file.separator") + "Notes";
	
	protected Note(){
	}
	
	/**
	 * Constructor which takes a text file, with the first line representing the .
	 * Each field name and its contents are added to their appropriate ArrayLists.
	 * 
	 * @param noteFile the file from which to read the note information from.
	 */
	public Note(File noteFile){
		this.noteFile = noteFile;
		
		Scanner noteReader = null;
		
		try {
			noteReader = new Scanner(this.noteFile);
		
			titleText = noteReader.nextLine();
			
			while (noteReader.hasNextLine()){
				bodyText = bodyText + "\n" + noteReader.nextLine();
			}
			
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not read from the specified note file because the file could not be found.");
		}
		catch (NoSuchElementException ex){
			System.err.println("Invalid Note File");
		}
		finally {
			noteReader.close();
		}
		
	}
	
	/**
	 * Constructor which creates a new instance of Note with
	 * the given title and body text.
	 * As it creates a new note, it creates a new note file.
	 * 
	 * @param title the new note's title text.
	 * @param body the new note's body text.
	 */
	public Note(String title, String body){
		File newNoteFile;
		titleText = title;
		bodyText = body;
		
		//Finds an unused filename, then creates the new file.
		int i = 0;
		do{
			i++;
			newNoteFile = new File(fileDirectory + System.getProperty("file.separator") + "Note" + i + ".txt");
		}while(newNoteFile.exists() == true);
		
		noteFile = newNoteFile;
		updateNoteFile();
	}
	
	/**
	 * Modifies the note's body text.
	 * 
	 * @param body the new string to be used for the note's body text.
	 * @return whether or not the body text modification was successful.
	 */
	public boolean setBodyText(String body){
		boolean wasSuccessful = false;
		bodyText = body;
		wasSuccessful = updateNoteFile();
		return wasSuccessful;
	}
	
	/**
	 * Returns the note's body text.
	 * 
	 * @return the note's body text.
	 */
	public String getBodyText(){
		return bodyText;
	}
	
	/**
	 * Modifies the note's title text.
	 * 
	 * @param title the new string to be used for the note's title.
	 * @return whether or not the title modification was successful.
	 */
	public boolean setTitle(String title){
		boolean wasSuccessful = false;
		titleText = title;
		wasSuccessful = updateNoteFile();
		return wasSuccessful;
	}
	
	/**
	 * Returns the note's title text.
	 * 
	 * @return the note's title text.
	 */
	public String getTitleText(){
		return titleText;
	}
	
	/**
	 * Deletes the note's file.
	 * 
	 * @return whether or not the file deletion operation was successful.
	 */
	public boolean deleteNote(){
		boolean wasSuccessful = false;
		wasSuccessful = noteFile.delete();		
		return wasSuccessful;
	}
	
	/**
	 * Re-writes the note's file, with its title on the first line
	 * and its body on the following line(s).
	 * 
	 * @return whether or not the file writing operation was successful.
	 */
	public boolean updateNoteFile(){
		boolean wasSuccessful = false;
		PrintWriter noteWriter = null;
		
		try {
			//Writes the title text to the note file, followed by the body text.
			noteWriter = new PrintWriter(noteFile);
			noteWriter.println(titleText);
			noteWriter.println(bodyText);
			
			//If the method reaches this point, it has successfully completed the writing operation.
			wasSuccessful = true;
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not write to specified note file because the file could not be found.");
		}
		finally {
			//Closes the file writer
			noteWriter.close();
		}
		
		return wasSuccessful;
	}
	
	/**
	* Returns the note's file.
	*
	* @return The note's file.
	*/
	public File getFile(){
	 return contactFile;
	}
}
