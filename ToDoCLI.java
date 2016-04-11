import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Georgew on 10/04/2016.
 */
public class ToDoCLI {
    private Scanner userInputScanner;
    private ArrayList<ToDoItem> toDoItems;
    public ToDoCLI(Scanner userInputScanner){
        this.userInputScanner = userInputScanner;
        toDoItems = new ArrayList<>();
        loadToDo();
    }

    private void loadToDo(){
        try {
            File toDoDirectory = new File(ToDoItem.fileDirectory);
            File files[] = toDoDirectory.listFiles();
            for (int i = 0; i < files.length; i++) {
                toDoItems.add(new ToDoItem(files[i]));
            }
        } catch (NullPointerException e) {
            System.err.println("No Contacts To Load");
            //e.printStackTrace();
        }
    }

    public void  showMainToDoMenu(){
        try{
            System.out.println("Type Help For Command List");
            while (true) {
                System.out.println("\n##### TODO LIST MENU #####\n" +
                        "Enter Command (Type \"Quit\" At Any Point To Go Back):");
                String input = getInput();
                System.out.println();
                String output = parseCommand(input);
                if (output.equals("EXIT")) {
                    return;
                } else {
                    System.out.println(output);
                }
            }
        } catch (QuitException e){
            System.out.println("Returning To Main Menu....");
        }
    }

    private String getInput() throws QuitException {
        while (true) {
            String input = userInputScanner.nextLine();
            if (input.equals("")) {
                System.out.println("Please Enter Something: ");
            } else if (input.toUpperCase().equals("QUIT")) {
                throw new QuitException();
            } else {
                return input;
            }
        }
    }

    private int getInt(int max) throws QuitException {
        while (true){
            try {
                String input = userInputScanner.nextLine();
                if (input.equals("")) {
                    System.out.println("Please Enter Something: ");
                } else if (input.toUpperCase().equals("QUIT")) {
                    throw new QuitException();
                } else {
                    int id =  Integer.parseInt(input);
                    if(id <= max){
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

    private String parseCommand(String command){
        String commands[] = command.split(" ");
        String output = "FAIL";
        try{
            switch (commands[0].toUpperCase()){
                case "NEW":
                    output = newToDo();
                    break;
                case "LIST":
                    if (commands.length == 1) {
                        listToDo();
                    } else {
                        listToDo(Integer.parseInt(commands[1]));
                    }
                    output = "";
                    break;
                case "EDIT":
                    if(commands.length == 1) {
                        output = editToDo();
                    } else {
                        output = editToDo(Integer.parseInt(commands[1]));
                    }
                    break;
                case "REMOVE":
                    if(commands.length == 1) {
                        output = removeToDo();
                    } else {
                        output = removeToDo(Integer.parseInt(commands[1]));
                    }
                    break;
                case "HELP":
                    help();
                    output = "";
            }
            return output;
        } catch (NumberFormatException e){
            System.out.println("Invalid Number Format");
            return "FAIL";
        }
    }

    private String newToDo(){
        try {
            System.out.println("##### NEW TODO #####\n" +
                    "Enter The Title Of The ToDo: ");
            String title = getInput();
            System.out.println("\nEnter The Body Of The ToDo: ");
            String body = getInput();
            System.out.println("Have You Completed This Task? (y/n)");
            boolean isComplete;
            while (true){
                String input = getInput();
                if(input.toUpperCase().equals("Y")){
                    isComplete = true;
                    break;
                } else if(input.toUpperCase().equals("N")){
                    isComplete = false;
                    break;
                } else {
                    System.out.println("Enter \"y\" or \"n\"");
                }
            }
            toDoItems.add(new ToDoItem(title,body,isComplete));
            listToDo(toDoItems.size() - 1);
            System.out.println("ToDo Created");
            return "SUCCESS";
        } catch (QuitException e){
            System.out.println("Cancelling...");
            return "FAIL";
        }
    }

    private void listToDo(){
        System.out.print("##### TODO's #####");
        for (int i = 0; i < toDoItems.size(); i++) {
            ToDoItem toDo = toDoItems.get(i);
            System.out.print("\n" + (i + 1) + ") " + toDo.getTitleText());
        }
    }

    private void listToDo(int id){
        try {
            String title = toDoItems.get(id - 1).getTitleText();
            String body = toDoItems.get(id - 1).getBodyText();
            boolean isComplete = toDoItems.get(id - 1).getIsComplete();
            System.out.println("##### TODO " + id + " #####");
            System.out.println(title + "\n" + body + "\nComplete: " + isComplete);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ToDo Does Not Exist!");
        }
    }

    private String editToDo(){
        try {
            listToDo();
            System.out.println("Which ToDo Would You Like To Edit: ");
            int id = getInt(toDoItems.size());
            return editToDo(id);
        } catch (QuitException e){
            System.out.println("Cancelling");
            return "FAIL";
        }
    }

    private String editToDo(int id){
        try {
            listToDo(id);
            System.out.println("What Would You Like To Edit:\n" +
                    "1 - Title\n" +
                    "2 - Body\n" +
                    "3 - isComplete\n" +
                    "Enter Your Selection Using The Numbers (1,2,3):");
            int editFieldId = getInt(3);
            switch (editFieldId){
                case 1:
                    System.out.println("Enter The New Title: ");
                    toDoItems.get(id - 1).setTitle(getInput());
                    break;
                case 2:
                    System.out.println("Enter The New Body: ");
                    toDoItems.get(id - 1).setBodyText(getInput());
                    break;
                case 3:
                    toDoItems.get(id - 1).setIsComplete(!toDoItems.get(id - 1).getIsComplete());
                    System.out.println("ToDo Is Now: " + toDoItems.get(id - 1).getIsComplete());
                    break;
            }
            return "SUCCESS";
        } catch (QuitException e){
            System.out.println("Cancelling");
            return "FAIL";
        }
    }

    private String removeToDo(){
        try {
            listToDo();
            System.out.println("Which ToDo Would You Like To Remove: ");
            int id = getInt(toDoItems.size());
            return removeToDo(id);
        } catch (QuitException e){
            System.out.println("Cancelling");
            return "FAIL";
        }
    }

    private String removeToDo(int id){
        ToDoItem toDoItem = toDoItems.get(id - 1);
        toDoItem.deleteNote();
        toDoItems.remove(id - 1);
        System.out.println("Removed!");
        return "SUCCESS";
    }

    private void help(){
        System.out.println("COMMANDS:\n" +
                "NEW - Create A New ToDo Item\n" +
                "EDIT - Edit An Existing ToDo Item\n" +
                "EDIT <ID> - Edit The Given ToDo Item\n" +
                "LIST - Lists All ToDo Items\n" +
                "LIST <ID> - Lists The Given ToDo Item\n" +
                "REMOVE - Removes A Contact\n" +
                "REMOVE <ID> - Removes A Given Contact");
    }
}
