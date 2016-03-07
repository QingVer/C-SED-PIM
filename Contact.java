import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class representing a contact, which is a file containing a number of
 * 'fields' which are themselves made up of field names and field contents.
 * This class contains methods to create, modify and delete contacts and
 * their fields.
 * 
 * @version Sprint 1, V1.0
 */
public class Contact{
	
	private File contactFile;
	private ArrayList<String> fieldNames;
	private ArrayList<String> fieldContents;
	public static final String contactsDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor" + System.getProperty("file.separator") + "Contacts";
	
	/**
	* Constructor which takes a text file and reads each line as a field of the
	* form fieldName:fieldContents.
	* Each field name and its contents are added to their appropriate ArrayLists.
	*/
	public Contact(File contactFile){
		this.contactFile = contactFile;
		
		Scanner contactReader = null;
		
		try {
			contactReader = new Scanner(this.contactFile);
			
			while(contactReader.hasNext() == true){
				String field = contactReader.nextLine();
				if (field.contains(":") && field.length() >= 3){
					String[] fieldParts = field.split(":", 2);
					fieldNames.add(fieldParts[0]);
					fieldContents.add(fieldParts[1]);
				}
			}
			
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not read from the specified note file because the file could not be found.");
		}
		finally {
			contactReader.close();
		}
	}
	
	/**
	 * Constructor which takes an ArrayList of field strings which it then decomposes into its
	 * component parts (fieldName and fieldContents). A file is then created in which the new
	 * contact's fields are stored.
	 * As it creates a new contact, it creates a new contact file.
	 *  
	 * @param intialFields An ArrayList containing field strings of the form fieldName:fieldContent
	 */
	public Contact(ArrayList<String> initialFields){
		//Gets all of the supplied initial fields.
		String fieldName;
		String fieldContent;
		for (int i = 0; i < initialFields.size(); i++){
			String[] field = initialFields.get(i).split(":", 2);
			fieldName = field[0];
			fieldContent = field[1];
			
			fieldNames.add(fieldName);
			fieldContents.add(fieldContent);
		}
		
		//Creates the new contact's file.
		File newContactFile;
		int j = 0;
		do{
			j++;
			newContactFile = new File(contactsDirectory + System.getProperty("file.separator") + "Contact" + j + ".txt");
		}while(newContactFile.exists() == true);
		
		contactFile = newContactFile;
		updateContactFile();
	}
	
	/**
	 * Returns an ArrayList of strings detailing the contact's fields.
	 * For each valid field, i.e. a field name for which there exists
	 * field content, a string will be added to the ArrayList.
	 * 
	 * @return an ArrayList of strings of all of the contact's fields.
	 */
	public ArrayList<String> viewContact(){
		ArrayList<String> fields = new ArrayList<String>();
		
		for (int fieldNumber = 0; fieldNumber < fieldNames.size(); fieldNumber++){
			if (fieldNumber < fieldContents.size()){
				fields.add(fieldNames.get(fieldNumber) + " : " + fieldContents.get(fieldNumber));
			}
			else{
				break;
			}
		}
		
		return fields;
	}
	
	/**
	 * Deletes the contact file.
	 * 
	 * @return Whether or not the deletion operation was successful.
	 */
	public boolean deleteContact(){
		boolean wasSuccessful = false;
		
		wasSuccessful = contactFile.delete();
		
		return wasSuccessful;
	}
	
	/**
	 * Given the name and contents of a field, adds them to the
	 * ArrayLists, then updates the contact file. This effectively
	 * 'adds' the field to the contact.
	 * 
	 * @param fieldName The field's name.
	 * @param fieldContents The contents of the field.
	 * @return whether or not the field addition operation was successful.
	 */
	public boolean addField(String fieldName, String fieldContent){
		boolean wasSuccessful = false;
		
		fieldNames.add(fieldName);
		fieldContents.add(fieldContent);
		
		wasSuccessful = updateContactFile();
		
		return wasSuccessful;
	}
	
	/**
	 * Given the name of the field to delete, this method removes that field's
	 * name and contents from the appropriate ArrayLists, then updates the
	 * contact file. 
	 * 
	 * @param fieldName The name of the field to be deleted.
	 * @return whether or not the operation was successful.
	 */
	public boolean removeField(String nameOfFieldToDelete){
		boolean wasSuccessful = false;
		
		if(getFieldIndexByName(nameOfFieldToDelete) != -1 || getFieldIndexByName(nameOfFieldToDelete) >= fieldContents.size()){
			fieldContents.remove(getFieldIndexByName(nameOfFieldToDelete));
			fieldNames.remove(getFieldIndexByName(nameOfFieldToDelete));
			wasSuccessful = updateContactFile();
		}
		else{
			wasSuccessful = false;
		}
		
		return wasSuccessful;
	}
	
	/**
	 * Given the name of a field and a value to change that field's contents to,
	 * this method modifies that field's contents to the given contents, then
	 * updates the contact file.
	 * 
	 * @param nameOfFieldToModify Name of the fields whose contents needs modification.
	 * @param newFieldContents  
	 * @return Whether or not the operation was successful.
	 */
	public boolean modifyField(String nameOfFieldToModify, String newFieldContents){
		boolean wasSuccessful = false;
		
		if(getFieldIndexByName(nameOfFieldToModify) != -1 || getFieldIndexByName(nameOfFieldToModify) >= fieldContents.size()){
			fieldContents.set(getFieldIndexByName(nameOfFieldToModify), newFieldContents);
			wasSuccessful = updateContactFile();
		}
		else{
			wasSuccessful = false;
		}
				
		return wasSuccessful;
	}
	
	/**
	 * Given the name of a field, this method returns that field's index in
	 * the ArrayList fieldNames. If a field with that name does not exist,
	 * -1 is returned.
	 * 
	 * @param fieldName the name of the fields whose index is to be searched for.
	 * @return the index of the field if it exists, and -1 if it does not.
	 */
	public int getFieldIndexByName(String fieldName){
		int fieldIndex = -1;
		
		for (int i = 0; i < fieldNames.size(); i++){
			if (fieldName == fieldNames.get(i)){
				fieldIndex = i;
				break;
			}
			else {
			}
		}
		
		return fieldIndex;
	}
	
	/**
	 * Re-writes the contact's file with all of its fields in their current
	 * state. Fields are written in the form fieldName:fieldContents,
	 * with each field taking up its own line.
	 * 
	 * @return whether or not the file writing operation was successful.
	 */
	public boolean updateContactFile(){
		boolean wasSuccessful = false;
		PrintWriter contactWriter = null;
		
		try {
			//Initialises the contact file writer
			contactWriter = new PrintWriter(contactFile);
			
			//Writes each field to the file
			for (int fieldNumber = 0; fieldNumber < fieldNames.size(); fieldNumber++){
				contactWriter.println(fieldNames.get(fieldNumber) + ":" + fieldContents.get(fieldNumber));
			}
			
			//If the method reaches this point, it has successfully completed the writing operation.
			wasSuccessful = true;
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not write to specified note file because the file could not be found.");
		}
		finally {
			//Closes the file writer
			contactWriter.close();
		}
		
		return wasSuccessful;
	}
}