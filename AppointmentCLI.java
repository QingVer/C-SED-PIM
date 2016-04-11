import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class AppointmentCLI {
    private Scanner userInputReader;
    @SuppressWarnings("unused") private Appointment appointment;

    public AppointmentCLI(){
    }
    /*
     * Displays a main menu in which the user can go back to the top level
     * menu, create a new note or view his/her notes
     */
    public void showMainAppointmentMenu(){
        //boolean used to check if user wants to quit
        boolean selectionExit = false;
        while(!selectionExit){
            System.out.println("(1) Create an appointment\n(2) View Appointments\n(3) Go back");
            userInputReader = new Scanner(System.in);
            String userInputString = userInputReader.nextLine();

            //checks if user has input is valid
            if(userInputString.length() == 1){
                switch(userInputString.charAt(0)){
                    case '1':
                        getNewAppointmentInfo();
                        break;
                    case '2':
                        listAppointments();
                        break;
                    case '3':
                        selectionExit = true;
                        break;
                    default:
                        System.err.println("Please enter a valid option...");
                        break;
                }
            }else{
                System.err.println("Please enter a valid option...");
            }
        }
    }

    /*
     * Displays all notes listed in the directory in which all notes are store.
     * If notes are empty, it will return to the main menu and if not,
     * user can select a note numerically and get an option menu (selectedNoteCommands())
     * */
    public void listAppointments(){
        if(new File(Appointment.fileDirectory).listFiles().length == 0){
            System.err.println("Directory is empty, please create an appointment first...");
            showMainAppointmentMenu();

        }else{
            System.out.println("Select an appointment via number selection");
            int amountOfApp = (Integer)(new File(Appointment.fileDirectory).listFiles().length);

            for(int i = 0; i < amountOfApp; i++){
                System.out.println("(" + (i+1) + ") Appointment"+(i));
            }

            while(true){
                userInputReader = new Scanner(System.in);
                String userInputString = userInputReader.nextLine();

                //if the input of the user is solely numerical and the part of the list of notes
                if(userInputString.length() == (amountOfApp + "").length() && userInputString.matches("[0-9]+") && Integer.parseInt(userInputString) <= amountOfApp && Integer.parseInt(userInputString) != 0){
                    File file[] = (new File(Appointment.fileDirectory).listFiles());

                    for(File f : file){
                        if(f.getName().endsWith(userInputString)){
                            Appointment a = new Appointment();
                            selectedNoteCommands(a);

                            System.out.println();
                            showMainAppointmentMenu();;
                            break;
                        }
                    }
                }else{
                    System.err.println("Please enter a valid option...");
                }
            }
        }
    }

    /*
     * Displays a menu that allows the user to edit a note,
     * delete a note, update title, update body or both
     * */
    public void selectedNoteCommands(Appointment app){
        while(true){
            System.out.println("(1) Update Start Time\n(2) Update Title\n(3) Update Location\n(4)Update Description\n (\n(5) Update Duration\n (6)Delete Note");

            userInputReader = new Scanner(System.in);
            String userInputString = userInputReader.nextLine();

            if(Character.isDigit(userInputString.charAt(0))){
                switch (Integer.parseInt(userInputString)) {
                    case 1:
                        try {
                            app.setStartDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(userInputString));
                        }catch(Exception e){
                            System.err.println("Error");
                        }
                        break;
                    case 2:
                        app.setTitle(userInputString);
                        break;
                    case 3:
                        app.setLocation(userInputString);
                        break;
                    case 4:
                        app.setDesc(userInputString);
                        break;
                    case 5:
                        app.setDuration(Float.parseFloat(userInputString));
                        break;
                    case 6:
                        app.deleteAppointment();
                        break;
                    default:
                        System.out.println("Please select an option in the list....");
                        break;
                }
            }else{
                System.err.println("Please enter a valid option...");
            }
        }
    }

    public void getNewAppointmentInfo(){
        try {
            String titleText = null;
            String descriptionText = null;
            String locationText = null;
            Date time = null;
            float duration = 0;

            Scanner noteInfoReader = new Scanner(System.in);

            System.out.println("Please enter a title for your appointment:");
            titleText = noteInfoReader.nextLine();
            System.out.println("\nPlease enter a description for your note - use \\n to indicate new lines");
            descriptionText = noteInfoReader.nextLine();
            System.out.println("Please enter a duration for your appointment:");
            duration = Float.parseFloat(noteInfoReader.nextLine());
            System.out.println("Please enter a time for your appointment:");
            try {
                time = ((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(noteInfoReader.nextLine()));
            }catch(Exception e){
                System.err.println("Error");
            }
            noteInfoReader.close();

            appointment = new Appointment(titleText, descriptionText, locationText, time, duration);
        }catch (Exception e){
            System.err.println("Error with the creation of the appointment...");
        }
    }

}
