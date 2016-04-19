import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Class providing functionality for importing a the contents of a .csv file into
 * the DynOSor personal organiser application as appointments.
 * The provided file should be a .csv file, but need not be as long as it follows
 * the following structure:
 * 
 * "Activity";"Unit";"Start week";"Start day";"Start date";"Start time";"End day";"End date";"End time";"Duration";"Staff member";"Room";"Department";"Size";"Week pattern"
 * 
 * @author Joshua Evans
 * @version Sprint 3, V1.0
 * @release 17/04/2016
 */
public class AppointmentImporter {
	
	/**
	 * Given a file, tries to import its contents as appointments.
	 * The file should be downloaded from myTimeTable and/or should be on the form:
	 * "Activity";"Unit";"Start week";"Start day";"Start date";"Start time";"End day";"End date";"End time";"Duration";"Staff member";"Room";"Department";"Size";"Week pattern"
	 * 
	 * @param csvFile A file containing a series of appointment records.
	 */
	public static void importFile(File csvFile){
		try {
			//Initialises Scanner
			Scanner fileReader = new Scanner(csvFile);
		
			//Skips first two lines
			fileReader.nextLine();
			fileReader.nextLine();
			
			//Parses each line in the file and adds them to the personal organiser as appointments.
			while (fileReader.hasNextLine() == true){
				try{
					String title;
					String desc;
					String location;
					Date startDate;
					float durationHours;
					
					String appointmentRecord = fileReader.nextLine();
					
					String[] appointmentData = appointmentRecord.split(";");
					
					title = appointmentData[0];
					desc = appointmentData[1] + " with " + appointmentData[10];
					location = appointmentData[11];
					startDate = makeValidDate(appointmentData[4].substring(1, appointmentData[4].length() - 1), appointmentData[5].substring(1, appointmentData[5].length() - 1));
					durationHours = makeDurationFloat(appointmentData[9].substring(1, appointmentData[9].length() - 1));
					
					new Appointment(title, desc, location, startDate, durationHours);
				}
				catch (ArrayIndexOutOfBoundsException | ParseException | NumberFormatException ex){
					System.err.println("Malformed line in .csv file, skipping...");
					ex.printStackTrace();					
					continue;
				}
				
			}
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Given a date string and time string, converts them to a date.
	 * @param startDateString A date string in the form yyyy-MM-dd.
	 * @param startTimeString A time string in the form HH:mm.
	 * @return A date.
	 * @throws ParseException
	 */
	private static Date makeValidDate(String startDateString, String startTimeString) throws ParseException{
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date startDate = new Date();
		startDate = dateFormatter.parse(startDateString + " " + startTimeString);
		
		return startDate;
	}
	
	/**
	 * Converts a string in the form HH:mm into a fractional number of hours.
	 * @param durationString A time string in the form HH:mm
	 * @return A fractional number of hours.
	 * @throws NumberFormatException
	 */
	private static float makeDurationFloat(String durationString) throws NumberFormatException {
		String[] durationStringParts = durationString.split(":");
		float hour = Float.parseFloat(durationStringParts[0]);
		float fractionOfHour =  Float.parseFloat(durationStringParts[1]) / 60;
		
		return hour + fractionOfHour;
	}
	
	/**
	 * Returns whether of not a given file can be processed for appointments.
	 * @param csvFile The file to be processed. 
	 * @return Whether of not a given file can be processed for appointments.
	 */
	public static boolean canReadFile(File csvFile){
		if(csvFile.exists() == false || csvFile.isFile() == false || csvFile.canRead() == false){
			return false;
		}
		else {
			return true;
		}
	}

}
