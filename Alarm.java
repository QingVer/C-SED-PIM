import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class representing an alarm which is set to go off at a certain
 * time every day that it is enabled.
 * @author Joshua Evans
 * @version Sprint 3, V1.2
 * @release 19/04/2016
 */
public class Alarm {
	private int alarmHour;
	private int alarmMinute;
	private boolean isEnabled;
	private File alarmFile;
	
	public static String fileDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "DynOSor" + System.getProperty("file.separator") + "Alarms";
	
	/**
	 * Constructor given a file, from which it reads all of the data
	 * necessary for its initialisation.
	 * 
	 * @param alarmFile The file containing the alarm data.
	 */
	public Alarm(File alarmFile){
		this.alarmFile = alarmFile;

		Scanner alarmReader = null;

		try {
			alarmReader = new Scanner(this.alarmFile);
			
			//Reads alarm hour from file
			alarmHour = Integer.parseInt(alarmReader.nextLine());
			
			//Validates alarm hour
			if (alarmHour > 23){
				alarmHour = 23;
			}
			else if (alarmHour < 0){
				alarmHour = 0;
			}
			
			//Reads alarm minute from file
			alarmMinute = Integer.parseInt(alarmReader.nextLine());
			
			//Validates alarm minute
			if (alarmMinute > 59){
				alarmMinute = 59;
			}
			else if (alarmMinute < 0){
				alarmMinute = 0;
			}
			
			//Reads alarm isEnabled from file
			String isisEnabledString = alarmReader.nextLine();
			
			//Converts string to boolean
			if (isisEnabledString.equals("true")){
				isEnabled = true;
			}
			else {
				isEnabled = false;
			}
			
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not read from the specified note file because the file could not be found.");
		}
		catch (NumberFormatException ex){
			
		}
		catch (NoSuchElementException ex){
			System.err.println("Invalid Appointment File");
		}
		finally {
			assert alarmReader != null;
			alarmReader.close();
		}
	}
	
	/**
	 * Constructor given a an alarm hour, alarm minute and whether the 
	 * alarm should be enabled or not. Creates an file for storing
	 * alarm data too.
	 * @param alarmHour The hour at which the alarm should go off.
	 * @param alarmMinute The minute at which the alarm should go off.
	 * @param isEnabled Whether or not the alarm should be enabled.
	 */
	public Alarm(int alarmHour, int alarmMinute, boolean isEnabled){
		
		this.alarmHour = alarmHour;
		this.alarmMinute = alarmMinute;
		this.isEnabled = isEnabled;
		
		
		//Finds an unused filename, then creates the new file.
		File newAlarmFile;
		int i = 0;
		do{
			i++;
			newAlarmFile = new File(fileDirectory + System.getProperty("file.separator") + "Alarm" + i + ".txt");
		} while (newAlarmFile.exists() == true);
		
		alarmFile = newAlarmFile;
		
		updateAlarmFile();
	}
	
	/**
	 * Sets the alarm time.
	 * @param alarmHour The hour at which the alarm should go off.
	 * @param alarmMinute The minute at which the alarm should go off.
	 * @return Whether or not updating the time was successful.
	 */
	public boolean setAlarmTime(int alarmHour, int alarmMinute){
		this.alarmHour = alarmHour;
		this.alarmMinute = alarmMinute;
		
		boolean wasSuccessful = updateAlarmFile();
		
		return wasSuccessful;
	}
	
	/**
	 * Sets whether or not the alarm is enabled.
	 * @param isEnabled Boolean value of whether or not the alarm is enabled.
	 * @return Whether or not the isEnabled value has been successfully updated.
	 */
	public boolean setIsEnabled(boolean isEnabled){
		this.isEnabled = isEnabled;
		
		boolean wasSuccessful = updateAlarmFile();
		
		return wasSuccessful;
	}
	
	public int getAlarmHour(){
		return alarmHour;
	}
	
	public int getAlarmMinute(){
		return alarmMinute;
	}
	
	public String getAlarmTimeString(){
		return alarmHour + ":" + alarmMinute;
	}
	
	public boolean getIsEnabled(){
		return isEnabled;
	}
	
	public String getIsEnabledString(){
		if (isEnabled == true){
			return "Alarm Enabled";
		}
		else {
			return "Alarm Disabled";
		}
	}
	
	/**
	 * Sets whether or not the alarm is enabled.
	 * @param isEnabled String value of whether or not the alarm is enabled.
	 * @return Whether or not the isEnabled value has been successfully updated.
	 */
	public boolean setIsEnabled(String isEnabledString){
		boolean wasSuccessful = false;
		
		if (isEnabledString.toLowerCase().equals("true")){
			this.isEnabled = true;
		}
		else {
			this.isEnabled = false;
		}
		
		wasSuccessful = updateAlarmFile();
		
		return wasSuccessful;
	}
	
	/**
	 * Updates the alarm file with the current alarm data.
	 * @return whether or not the file was updated successfully.
	 */
	public synchronized boolean updateAlarmFile(){
		boolean wasSuccessful = false;
		PrintWriter alarmWriter = null;

		try {
			//Writes the alarm's data to a file.
			alarmWriter = new PrintWriter(alarmFile);

			alarmWriter.println(alarmHour);
			alarmWriter.println(alarmMinute);
			alarmWriter.println(new Boolean(isEnabled));

			//If the method reaches this point, it has successfully completed the writing operation.
			wasSuccessful = true;
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not write to specified appointment file because the file could not be found.");
		}
		finally {
			//Closes the file writer
			assert alarmWriter != null;
			alarmWriter.close();
		}

		return wasSuccessful;
	}
	
	public boolean deleteAlarm(){
		boolean wasSuccessful = false;
		wasSuccessful = alarmFile.delete();		
		return wasSuccessful;
	}
	
	/**
	 * Returns the alarm's file.
	 *
	 * @return The alarm's file.
	 */
	public File getFile(){
		return alarmFile;
	}
}
