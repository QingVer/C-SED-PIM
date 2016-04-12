import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class AppointmentCLI extends CLI {
    private ArrayList<Appointment> appointments;

    public AppointmentCLI(Scanner scanner) {
        super(scanner);
        appointments = new ArrayList<>();
        try {
            File directory = new File(Appointment.fileDirectory);
            File files[] = directory.listFiles();
            for (File file : files) {
                appointments.add(new Appointment(file));
            }
        } catch (NullPointerException e) {
            System.err.println("No Appointments To Load");
            //e.printStackTrace();
        }
    }

    /*
     * Displays a main menu in which the user can go back to the top level
     * menu, create a new note or view his/her notes
     */
    public void showMainAppointmentMenu() {
        try {
            //boolean used to check if user wants to quit
            boolean selectionExit = false;

            while (!selectionExit) {
                System.out.println("(1) Create an appointment\n(2) View Appointments\n(3) Go back");
                int input = getInt(3);

                //checks if user has input is valid
                switch (input) {
                    case 1:
                        getNewAppointmentInfo();
                        break;
                    case 2:
                        listAppointments();
                        break;
                    case 3:
                        selectionExit = true;
                        break;
                    default:
                        System.err.println("Please enter a valid option...");
                        break;
                }
            }
        } catch (QuitException e) {
            System.out.println("Returning To Main Menu...");
        }
    }

    /*
     * Displays all notes listed in the directory in which all notes are store.
     * If notes are empty, it will return to the main menu and if not,
     * user can select a note numerically and get an option menu (selectedNoteCommands())
     * */
    public void listAppointments() {
        try {
            if (appointments.size() == 0) {
                System.err.println("Directory is empty, please create an appointment first...");
            } else {
                System.out.println("Select an appointment via number selection");
                for (int i = 0; i < +appointments.size(); i++) {
                    System.out.println((i+1) + ") " + appointments.get(i).getTitle());
                }
                int viewId = getInt(appointments.size());
                Appointment appointment = appointments.get(viewId - 1);
                selectedNoteCommands(appointment);
                //if the input of the user is solely numerical and the part of the list of notes
            }
        } catch (QuitException e) {
            System.out.println("Cancelling");
        }
    }

    private void printAppointment(Appointment appointment){
        System.out.println("Title: " + appointment.getTitle() + "\nDescription: " +
                appointment.getDesc() + "\nLocation: " +
                appointment.getLocation() + "\nDate: " +
                appointment.getStartDate().toString() + "\nDuration: " +
                appointment.getDurationHours());
    }

    /*
     * Displays a menu that allows the user to edit a note,
     * delete a note, update title, update body or both
     * */
    public void selectedNoteCommands(Appointment app) {
        try{
            while (true) {
                printAppointment(app);
                System.out.println("(1) Update Start Time\n(2) Update Title\n(3) Update Location\n(4) Update Description\n(5) Update Duration\n(6) Delete Note");
                int selection = getInt(6);
                switch (selection) {
                    case 1:
                        try {
                            System.out.println("Enter The New Start Time: ");
                            app.setStartDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(getInput()));
                        } catch (Exception e) {
                            System.err.println("Error");
                        }
                        break;
                    case 2:
                        System.out.println("Enter The New Title: ");
                        app.setTitle(getInput());
                        break;
                    case 3:
                        System.out.println("Enter The Location: ");
                        app.setLocation(getInput());
                        break;
                    case 4:
                        System.out.println("Enter The New Description: ");
                        app.setDesc(getInput());
                        break;
                    case 5:
                        System.out.println("Enter The Duration: ");
                        app.setDuration(getInt(999999999));
                        break;
                    case 6:
                        app.deleteAppointment();
                        System.out.println("Deleted!");
                        throw new QuitException();
                    default:
                        System.out.println("Please select an option in the list....");
                        break;
                }
            }
        } catch (QuitException e){
            System.out.println("Returning...");
        }
    }

    public void getNewAppointmentInfo() {
        try {

            String titleText;
            String descriptionText;
            String locationText;
            Date time = new Date();
            float duration;

            Scanner noteInfoReader = new Scanner(System.in);

            System.out.println("Please enter a title for your appointment:");
            titleText = noteInfoReader.nextLine();
            System.out.println("\nPlease enter a description for your note - use \\n to indicate new lines");
            descriptionText = noteInfoReader.nextLine();
            System.out.println("Please enter a duration for your appointment, in hours:");
            duration = Float.parseFloat(noteInfoReader.nextLine());
            System.out.println("Please enter the location for your appointment");
            locationText = noteInfoReader.nextLine();
            System.out.println("Please enter a date and time for your appointment in the format dd/MM/yyyy HH:mm:ss:");
            try {
                time = ((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(noteInfoReader.nextLine()));
            } catch (Exception e) {
                System.err.println("Error");
            }

            new Appointment(titleText, descriptionText, locationText, time, duration);
        } catch (Exception e) {
            System.err.println("Error with the creation of the appointment...");
        }
    }
}
