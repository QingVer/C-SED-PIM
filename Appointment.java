import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.text.ParseException;
import java.util.Date;

/**
* Class representing an appointment in a personal
* organiser. Contains methods for initialising, 
* accessing and manipulating appointment objects.
*
* @author Joshua Evans
* @version Sprint 2, V1.0
* @kill yourself
*/
public class Appointment {

	private String title;
	private String desc;
	private String location;
	private Date startDate;
	private float durationHours;
	private File appointmentFile;
	public static final String fileDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor" + System.getProperty("file.separator") + "Appointments";
	
	/**
	* Given a file containing an appointment's data, this
	* constructor initialises the appointment object using
	* data read from the file.
	*
	* @param appointmentFile The file containing the appointment data.
	*/
	public Appointment(File appointmentFile){
		this.appointmentFile = appointmentFile;
		
		Scanner appointmentReader = null;
		
		try {
			appointmentReader = new Scanner(this.appointmentFile);
			
			//Reads all of the appointment data from given the file.
			title = appointmentReader.nextLine();
			desc = appointmentReader.nextLine();
			location = appointmentReader.nextLine();
			DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			startDate = new Date();
			startDate = dateFormatter.parse(appointmentReader.nextLine());
			durationHours = Float.parseFloat(appointmentReader.nextLine());
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not read from the specified note file because the file could not be found.");
		}
		catch (ParseException ex){
			System.err.println("Invalid Appointment File Data: Incorrectly Formatted Start Date");
		}
		catch (NoSuchElementException ex){
			System.err.println("Invalid Appointment File");
		}
		finally {
			assert appointmentReader != null;
			appointmentReader.close();
		}	
	}
	
	/**
	* Given all of the data pertaining to an instance of an appointment in a personal
	* organiser, this constructor creates a new Appointment object and creates a new
	* file to holds its data.
	*
	* @param title The appointment's title.
	* @param desc A short description of the appointment.
	* @param location The location where the appointment is taking place.
	* @param startDate The start time and date of the appointment.
	* @param durationHours How long the appointment will last, in hours. Does not need to be an integer number.
	*/
	public Appointment(String title, String desc, String location, Date startDate, float durationHours){
		this.title = title;
		this.desc = desc;
		this.location = location;
		this.startDate = startDate;
		this.durationHours = durationHours;
		
		//Finds an unused filename, then creates the new file.
		File newAppointmentFile;
		int i = 0;
		do{
			i++;
			newAppointmentFile = new File(fileDirectory + System.getProperty("file.separator") + "Appointment" + i + ".txt");
		} while(newAppointmentFile.exists());
		
		appointmentFile = newAppointmentFile;
		updateAppointmentFile();
	}
	
	/**
	* Sets the appointment's title.
	*
	* @param title The appointment's title.
	*/
	public boolean setTitle(String title){
		boolean wasSuccessful = false;
		this.title = title;
		wasSuccessful = updateAppointmentFile();
		return wasSuccessful;
	}
	
	/**
	* Sets the appointment's description.
	*
	* @param desc The appointment's description.
	*/
	public boolean setDesc(String desc){
		boolean wasSuccessful = false;
		this.desc = desc;
		wasSuccessful = updateAppointmentFile();
		return wasSuccessful;
	}
	
	/**
	* Sets the appointment's location.
	*
	* @param location The appointment's location.
	*/
	public boolean setLocation(String location){
		boolean wasSuccessful = false;
		this.location = location;
		wasSuccessful = updateAppointmentFile();
		return wasSuccessful;
	}
	
	/**
	* Sets the appointment's start date and time.
	*
	* @param startDate The appointment's start date and time.
	*/
	public boolean setStartDate(Date startDate){
		boolean wasSuccessful = false;
		this.startDate = startDate;
		wasSuccessful = updateAppointmentFile();
		return wasSuccessful;
	}
	
	/**
	* Sets the appointment's duration in hours. Doesn't need to be an integer.
	*
	* @param durationHours The appointment's duration in hours.
	*/
	public boolean setDuration(float durationHours){
		boolean wasSuccessful = false;
		this.durationHours = durationHours;
		wasSuccessful = updateAppointmentFile();
		return wasSuccessful;
	}
	
	/**
	* Returns the appointment's title.
	*
	* @return the appointment's title.
	*/
	public String getTitle(){
		return title;
	}
	
	/**
	* Returns the appointment's description.
	*
	* @return the appointment's description.
	*/
	public String getDesc(){
		return desc;
	}
	
	/**
	* Returns the appointment's location.
	*
	* @return the appointment's location.
	*/
	public String getLocation(){
		return location;
	}
	
	/**
	* Returns the appointment's start date and time.
	*
	* @return the appointment's start date and time.
	*/
	public Date getStartDate(){
	 return startDate;
	}
	
	/**
	* Returns the appointment's duration in hours.
	*
	* @return the appointment's duration in hours.
	*/
	public float getDurationHours(){
		return durationHours;
	}
	
	/**
	 * Deletes the appointment's file.
	 * 
	 * @return whether or not the file deletion operation was successful.
	 */
	public boolean deleteAppointment(){
		boolean wasSuccessful = false;
		wasSuccessful = appointmentFile.delete();		
		return wasSuccessful;
	}
	
	/**
	 * Re-writes the appointment's file, with its title on the first line
	 * and its body on the following line(s).
	 * 
	 * @return whether or not the file writing operation was successful.
	 */
	public boolean updateAppointmentFile(){
		boolean wasSuccessful = false;
		PrintWriter appointmentWriter = null;
		
		try {
			//Writes the appointment's data to a file.
			DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			appointmentWriter = new PrintWriter(appointmentFile);
			
			appointmentWriter.println(title);
			appointmentWriter.println(desc);
			appointmentWriter.println(location);
			appointmentWriter.println(dateFormatter.format(startDate));
			appointmentWriter.println("" + durationHours);
			
			//If the method reaches this point, it has successfully completed the writing operation.
			wasSuccessful = true;
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not write to specified appointment file because the file could not be found.");
		}
		finally {
			//Closes the file writer
			assert appointmentWriter != null;
			appointmentWriter.close();
		}
		
		return wasSuccessful;
	}
	
	/**
	* Returns the note's file.
	*
	* @return The note's file.
	*/
	public File getFile(){
		return appointmentFile;
	}
}
