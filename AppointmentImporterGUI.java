import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class providing a GUI for importing files as appointments in the DynOSor
 * personal organiser application.
 * @author Joshua Evans
 * @version Sprint 3, Version 1.0
 * @release 19/04/2016
 * @see AppointmentImporter, GuiFieldInput
 *
 */
public class AppointmentImporterGUI {
	
	/**
	 * Shows the GUI for importing appointments from a file, and handles actually importing them.
	 */
	public void showGUIMenu() {
		try {
				ArrayList<String> options = new ArrayList<>();
				options.add("File Path");
				GuiFieldInput input = new GuiFieldInput("Import Appointments From File", options, new ArrayList<String>());
				input.getInput();
				if (AppointmentImporter.canReadFile(new File(input.getStringAnswers().get(0))) == true){
					AppointmentImporter.importFile(new File(input.getStringAnswers().get(0)));
					JOptionPane.showMessageDialog(new JFrame(), "Finished importing all valid data from the specified file.", "Finished Importing File", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "Could not import data from the specified file..", "Could Not Open File", JOptionPane.WARNING_MESSAGE);
				}
		} catch (QuitException e){

		}
		
	}

}
