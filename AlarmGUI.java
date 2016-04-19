import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class providing a GUI for creating and managing Alarms.
 * 
 * @author Joshua Evans
 * @version Sprint 3, V1.1
 * @release 17/04/2016
 * @see Alarm, GuiFieldInput, GuiButtonInput
 */
public class AlarmGUI {
	private ArrayList<Alarm> alarmList;
	private ArrayList<String> alarmStringFields;
	private ArrayList<String> alarmIntFields;
	
	/**
	 * Initialises the AlarmGUI.
	 */
	public AlarmGUI(){
		//Loads all alarms into an ArrayList.
		try {
			alarmList = new ArrayList<Alarm>();
			File alarmDirectory = new File(Alarm.fileDirectory);
			File alarms[] = alarmDirectory.listFiles();
			for (int i = 0; i < alarms.length; i++) {
				alarmList.add(new Alarm(alarms[i]));
			}
		} catch (NullPointerException e) {
			System.err.println("No Alarms to Load.");
		}
		
		
		//Adds GUI fields
		alarmStringFields = new ArrayList<String>();
		alarmIntFields = new ArrayList<String>();
		alarmStringFields.add("Enable Alarm (true/false):");
		alarmIntFields.add("Alarm Hour:");
		alarmIntFields.add("Alarm Minute:");
	}
	
	/**
	 * Shows the Alarm Management GUI.
	 */
	public void showGUI(){
		try{
			while (true){
				ArrayList<String> options = new ArrayList<>();
				options.add("Create New Alarm");
				options.add("View/Edit Alarms");
				options.add("Delete Alarms");
				GuiButtonInput input = new GuiButtonInput("Alarms Menu", options);
				
				String selection = input.getInput();
				int id = -1;
				for(int i = 0; i < options.size(); i ++){
					if(selection.equals(options.get(i))){
						id = i;
						break;
					}
				}
				switch (id) {
					case 0:
						newAlarmGUI();
						break;
					case 1:
						listAlarmsGUI();
						break;
					case 2:
						removeAlarmGUI();
						break;
				}
			}
		}
		catch(QuitException ex){
		}
	}

	/**
	 * Shows a GUI for creating a new GUI.
	 */
	private void newAlarmGUI() {
		try {
			GuiFieldInput input = new GuiFieldInput("New Alarm", alarmStringFields, alarmIntFields);
			input.getInput();
			
			int hours = Integer.parseInt(input.getIntAnswers().get(0));
			int minutes = Integer.parseInt(input.getIntAnswers().get(1));
			
			boolean isEnabled;
			if (input.getStringAnswers().get(0).toLowerCase().equals("true")){
				isEnabled = true;
			}
			else {
				isEnabled = false;
			}
		
			alarmList.add(new Alarm(hours, minutes, isEnabled));
		} catch (QuitException ex){
		}
		catch (NumberFormatException ex){
			JOptionPane.showMessageDialog(new JFrame(), "Invalid Hour/Minute Value Enered - Must Be Integers.", "Could Not Create Alarm", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * Shows all alarms in a GUI.
	 */
	private void listAlarmsGUI() {
		try {
			while(true) {
				ArrayList<String> alarmTimes = new ArrayList<>();
				ArrayList<String> alarmEnabledDetails = new ArrayList<>();
	
				for (int i = 0; i < alarmList.size(); i++) {
					Alarm alarm = alarmList.get(i);
					alarmTimes.add(alarmList.get(i).getAlarmTimeString());
					alarmEnabledDetails.add(alarmList.get(i).getIsEnabledString());
				}
	
				GuiButtonInput input = new GuiButtonInput("Alarm List:", alarmTimes, alarmEnabledDetails);
				int selection = alarmTimes.indexOf(input.getInput());
				editAlarmGUI(selection);
			}
		} 
		catch (QuitException e) {
			
		}
		
	}
	
	/**
	 * Shows a GUI for editing the Alarm with the specified index.
	 * @param id The index of the Alarm to edit.
	 */
	private void editAlarmGUI(int id) {
		try {
			ArrayList<String> stringAnswers = new ArrayList<String>();
			ArrayList<String> intAnswers = new ArrayList<String>();
			
			stringAnswers.add(new Boolean(alarmList.get(id).getIsEnabled()).toString());
			intAnswers.add("" + alarmList.get(id).getAlarmHour());
			intAnswers.add("" + alarmList.get(id).getAlarmMinute());
			
			GuiFieldInput input = new GuiFieldInput("Edit Contact", alarmStringFields, stringAnswers, alarmIntFields, intAnswers);
			input.getInput();

			alarmList.get(id).setIsEnabled(input.getStringAnswers().get(0));
			alarmList.get(id).setAlarmTime(Integer.parseInt(input.getIntAnswers().get(0)), Integer.parseInt(input.getIntAnswers().get(1)));
		} catch (QuitException e){
		}
	}

	private void removeAlarmGUI() {
		try {
			while(true) {
				ArrayList<String> alarmTimes = new ArrayList<>();
				for (int i = 0; i < alarmList.size(); i++) {
					alarmTimes.add(alarmList.get(i).getAlarmTimeString());
				}
				GuiButtonInput input = new GuiButtonInput("Delete Contact", alarmTimes);
				if (0 == JOptionPane.showConfirmDialog(input, "Delete " + input.getInput(), "Delete Contact", JOptionPane.YES_NO_OPTION)) {
					removeAlarm(alarmTimes.indexOf(input.getInput()));
					break;
				}
			}
		} catch (QuitException e){
			//
		}		
	}
	
	/**
	 * Deletes the Alarm with the given index.
	 * @param id The Alarm to remove.
	 * @return Whether or not the Alarm was deleted successfully.
     */
	public boolean removeAlarm(int id) {
		try {
			if (alarmList.get(id).deleteAlarm()) {
				alarmList.remove(id);
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Alarm Does Not Exist!");
			return false;
		}
	}

}
