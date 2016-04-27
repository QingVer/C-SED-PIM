import javax.swing.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates a Appointment Gui that can be called at any time.
 * @author George Andrews
 * @version 1.0, Sprint 3
 * @see Appointment
 */
public class AppointmentGUI {
    ArrayList<Appointment> appointments = new ArrayList<>();
    @SuppressWarnings("serial")	ArrayList<String> stringFields =  new ArrayList<String>(){{
        add("Title:");
        add("Description:");
        add("Location:");
        add("Start Date (dd/MM/yyyy HH:mm):");
    }};
    @SuppressWarnings("serial")	ArrayList<String> intFields = new ArrayList<String>(){{
        add("\0Duration (Hrs): ");
    }};

    /**
     * Loads all appointments from the file!!
     */
    public AppointmentGUI(){
        try {
            File directory = new File(Appointment.fileDirectory);
            File appointments[] = directory.listFiles();
            for (int i = 0; i < appointments.length; i++) {
                this.appointments.add(new Appointment(appointments[i]));
            }
        } catch (NullPointerException e) {
            System.err.println("No Appointments To Load");
            //e.printStackTrace();
        }
    }

    /**
     * Shows the main menu
     */
    public void showGuiMenu(){
        try {
            while (true) {
                ArrayList<String> options = new ArrayList<>();
                options.add("Make New Appointment");
                options.add("View/Edit Appointments");
                options.add("Delete Appointment");
                GuiButtonInput input = new GuiButtonInput("Appointments Menu", options);
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
                        newAppointmentGUI();
                        break;
                    case 1:
                        listAppointmentGUI();
                        break;
                    case 2:
                        removeAppointmentGUI();
                        break;
                }
            }
        } catch (QuitException e){
            //
        }
    }

    /**
     * Lists all appointments using a button GUI.
     * @see GuiButtonInput
     */
    private void listAppointmentGUI(){
        try {
            while(true) {
                ArrayList<String> appointmentTitle = new ArrayList<>();
                ArrayList<String> appointmentDetails = new ArrayList<>();

                for (int i = 0; i < appointments.size(); i++) {
                    Appointment appointment = appointments.get(i);
                    appointmentTitle.add(appointment.getTitle());
                    String details = appointment.getDesc() + "\nLocation: " +
                            appointment.getLocation() + "\nStart Date: " +
                                    (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(appointment.getStartDate())
                            + "\nDuration (hrs): " +
                            appointment.getDurationHours();
                    appointmentDetails.add(details);
                }

                GuiButtonInput input = new GuiButtonInput("Appointments", appointmentTitle, appointmentDetails);
                int selection = appointmentTitle.indexOf(input.getInput());
                editAppointmentGUI(selection);
            }
        } catch (QuitException e) {
            //
        }
    }

    /**
     * Removes an appointment though a GUI
     * @see GuiButtonInput
     */
    private void removeAppointmentGUI(){
        try {
            while(true) {
                ArrayList<String> appointmentNames = new ArrayList<>();
                for (int i = 0; i < appointments.size(); i++) {
                    appointmentNames.add(appointments.get(i).getTitle());
                }
                GuiButtonInput input = new GuiButtonInput("Delete Appointment", appointmentNames);
                if (0 == JOptionPane.showConfirmDialog(input, "Delete " + input.getInput(), "Delete Appointment", JOptionPane.YES_NO_OPTION)) {
                    int id = appointmentNames.indexOf(input.getInput());
                    appointments.get(id).deleteAppointment();
                    appointments.remove(id);
                    break;
                }
            }
        } catch (QuitException e){
            //
        }
    }

    /**
     * Edits an appointment using a GuiDateInput
     * @see
     * GuiDateInput
     * @param id
     * the contact to edit
     */
    private void editAppointmentGUI(int id){
        try {
            while (true) {
                Appointment appointment = appointments.get(id);
                ArrayList<String> stringAnswers = new ArrayList<>();
                stringAnswers.add(appointment.getTitle());
                stringAnswers.add(appointment.getDesc());
                stringAnswers.add(appointment.getLocation());
                stringAnswers.add(
                        (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(appointment.getStartDate())
                );
                ArrayList<String> intAnswers = new ArrayList<>();
                intAnswers.add(appointment.getDurationHours() + "");
                GuiDateInput input =
                        new GuiDateInput("Edit appointment", stringFields, stringAnswers, intFields, intAnswers);
                input.getInput();
                try {
                    Date date = ((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(stringAnswers.get(3)));
                    String title = stringAnswers.get(0);
                    String desc = stringAnswers.get(1);
                    String location = stringAnswers.get(2);
                    float duration = Float.parseFloat(input.getIntAnswers().get(0));
                    appointments.remove(appointment);
                    appointment.deleteAppointment();
                    appointments.add(id,new Appointment(title,desc,location,date,duration));
                    return;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(input, "Date Is In The Wrong Format!");
                }
            }
        } catch (QuitException e){
            //
        }
    }

    /**
     * Using a GuiDateInput creates a new appointment
     * @see
     * GuiDateInput
     */
    public void newAppointmentGUI(){
        try {
            while (true) {
                GuiDateInput input = new GuiDateInput("New Appointment", stringFields, intFields);
                input.getInput();
                ArrayList<String> stringAnswers = input.getStringAnswers();
                try {
                    Date date = ((new SimpleDateFormat("dd/MM/yyyy HH:mm")).parse(stringAnswers.get(3)));
                    System.out.println(date.toString());
                    String title = stringAnswers.get(0);
                    String desc = stringAnswers.get(1);
                    String location = stringAnswers.get(2);
                    float duration = Float.parseFloat(input.getIntAnswers().get(0));
                    appointments.add(new Appointment(title, desc, location, date, duration));
                    return;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(input, "Date Is In The Wrong Format!");
                }
            }

        } catch (QuitException e){
            //
        }
    }
}
