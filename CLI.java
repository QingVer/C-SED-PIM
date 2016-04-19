import java.util.Scanner;

/**
 * Created by Georgew on 12/04/2016.
 */
public abstract class CLI {
    //Variables
    private Scanner scanner;
    public CLI(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * Gets a string input from the user.
     * @return
     * The users input
     * @throws QuitException
     * Thrown if the user enters "QUIT"
     */
    protected String getInput() throws QuitException {
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("")) {
                System.out.println("Please Enter Something: ");
            } else if (input.toUpperCase().equals("QUIT")) {
                throw new QuitException();
            } else {
                return input;
            }
        }
    }

    /**
     * Gets an integer input from the user.
     * @param max
     * The input has to be <= max
     * @return
     * The users inputed int
     * @throws QuitException
     * Thrown when the user enters quit.
     */
    protected int getInt(int max) throws QuitException {
        while (true){
            try {
                String input = scanner.nextLine();
                if (input.equals("")) {
                    System.out.println("Please Enter Something: ");
                } else if (input.toUpperCase().equals("QUIT")) {
                    throw new QuitException();
                } else {
                    int id =  Integer.parseInt(input);
                    if(id <= max && id > 0){
                        return id;
                    } else {
                        System.err.println("Not A Valid Selection");
                    }
                }
            } catch (NumberFormatException e){
                System.err.println("Not Valid Number");
            }
        }
    }
}
