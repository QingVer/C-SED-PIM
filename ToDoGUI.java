import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Creates a ToDoGui that can be called at any time.
 * @author George Andrews
 * @version 1.0, Sprint 3
 * @see ToDoItem
 */
public class ToDoGUI {
    private ArrayList<ToDoItem> toDos = new ArrayList<>();

    /**
     * Loads all ToDoItems from the folder
     */
    public ToDoGUI(){
        try {
            File directory = new File(ToDoItem.fileDirectory);
            File toDos[] = directory.listFiles();
            for (int i = 0; i < toDos.length; i++) {
                this.toDos.add(new ToDoItem(toDos[i]));
            }
        } catch (NullPointerException e) {
            System.err.println("No Contacts To Load");
            //e.printStackTrace();
        }
    }

    /**
     * Shows the main menu
     * @see GuiButtonInput
     */
    public void showGuiMenu(){
        try {
            while (true) {
                ArrayList<String> options = new ArrayList<>();
                options.add("Make New TODO");
                options.add("View/Edit TODOs");
                options.add("Delete TODO");
                GuiButtonInput input = new GuiButtonInput("TODOs Menu", options);
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
                        newToDoGUI();
                        break;
                    case 1:
                        listToDosGUI();
                        break;
                    case 2:
                        removeToDoGUI();
                        break;
                }
            }
        } catch (QuitException e){
            //
        }
    }

    /**
     * Gets the user to create a new contact
     * @see GuiFieldInput
     */
    private void newToDoGUI(){
        try {
            ArrayList<String> stringFields = new ArrayList<>();
            stringFields.add("Title:");
            stringFields.add("Description:\0");
            GuiFieldInput input = new GuiFieldInput("NEW TODO", stringFields, new ArrayList<String>());
            input.getInput();
            String title = input.getStringAnswers().get(0);
            String body = input.getStringAnswers().get(1);
            boolean done = 0 == JOptionPane.showConfirmDialog(input, "Is The Task Completed?", "Edit TODO", JOptionPane.YES_NO_OPTION);
            toDos.add(new ToDoItem(title, body, done));
        } catch (QuitException e){
            //
        }
    }

    /**
     * Lists all ToDoItems
     * @see GuiButtonInput
     */
    private void listToDosGUI(){
        try {
            while(true) {
                ArrayList<String> toDoTitles = new ArrayList<>();
                ArrayList<String> toDoBodies = new ArrayList<>();

                for (int i = 0; i < toDos.size(); i++) {
                    ToDoItem toDo = toDos.get(i);
                    toDoTitles.add(toDo.getTitleText());
                    toDoBodies.add(toDo.getBodyText() + "\nTask Complete: " + toDo.getIsComplete());
                }
                GuiButtonInput input = new GuiButtonInput("TODO List", toDoTitles, toDoBodies);
                int selection = toDoTitles.indexOf(input.getInput());
                editToDoGUI(selection);
            }
        } catch (QuitException e) {
            //
        }
    }

    /**
     * Edits the given ToDoItem
     * @param id
     * The ToDoItem to edit
     * @see GuiFieldInput
     */
    private void editToDoGUI(int id){
        try {
            ToDoItem toDo = toDos.get(id);
            ArrayList<String> labels = new ArrayList<>();
            labels.add("Title:");
            labels.add("Body:\0");
            ArrayList<String> content = new ArrayList<>();
            content.add(toDo.getTitleText());
            content.add(toDo.getBodyText());

            GuiFieldInput input =
                    new GuiFieldInput("Edit ToDo", labels, content);
            input.getInput();
            String title = input.getStringAnswers().get(0);
            String body = input.getStringAnswers().get(1);
            boolean done = 0 == JOptionPane.showConfirmDialog(input, "Is The Task Completed?", "Edit TODO", JOptionPane.YES_NO_OPTION);
            toDo.deleteNote();
            toDos.set(id, new ToDoItem(title, body, done));
        } catch (QuitException e){
            //
        }
    }

    /**
     * removes the selected contact.
     * @see GuiButtonInput
     */
    private void removeToDoGUI(){
        try {
            while(true) {
                ArrayList<String> ToDoTitles = new ArrayList<>();
                for (int i = 0; i < toDos.size(); i++) {
                    ToDoTitles.add(toDos.get(i).getTitleText());
                }
                GuiButtonInput input = new GuiButtonInput("Delete TODO", ToDoTitles);
                if (0 == JOptionPane.showConfirmDialog(input, "Delete " + input.getInput(), "Delete TODO", JOptionPane.YES_NO_OPTION)) {
                    int id = ToDoTitles.indexOf(input.getInput());
                    toDos.get(id).deleteNote();
                    toDos.remove(id);
                    break;
                }
            }
        } catch (QuitException e){
            //
        }
    }

}