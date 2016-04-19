import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class NoteMenuGUI {
	private ArrayList<Note> notes = new ArrayList<>();

	/**
	 * Constructor.
	 * Loads all notes stored in the folders.
	 */
	public NoteMenuGUI(){
		try {
			File directory = new File(Note.fileDirectory);
			File notes[] = directory.listFiles();
			for (int i = 0; i < notes.length; i++) {
				this.notes.add(new Note(notes[i]));
			}
		} catch (NullPointerException e) {
			System.err.println("No Notes To Load");
			//e.printStackTrace();
		}
	}

	/**
	 * Shows the main menu through a Button GUI
	 * @see GuiButtonInput
	 */
	public void showGuiMenu(){
		try {
			while (true) {
				ArrayList<String> options = new ArrayList<>();
				options.add("Make New Note");
				options.add("View/Edit Notes");
				options.add("Delete Note");
				GuiButtonInput input = new GuiButtonInput("Notes Menu", options);
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
						newNoteGUI();
						break;
					case 1:
						listNotesGUI();
						break;
					case 2:
						removeNoteGUI();
						break;
				}
			}
		} catch (QuitException e){
			//
		}
	}

	/**
	 * Using a GuiDateInput creates a new Note
	 * @see
	 * GuiDateInput
	 */
	private void newNoteGUI(){
		try {
			ArrayList<String> stringFields = new ArrayList<>();
			stringFields.add("Title:");
			stringFields.add("Description:\0");
			GuiFieldInput input = new GuiFieldInput("NEW CONTACT", stringFields, new ArrayList<String>());
			input.getInput();
			String title = input.getStringAnswers().get(0);
			String body = input.getStringAnswers().get(1);
			notes.add(new Note(title, body));
		} catch (QuitException e){
			//
		}
	}

	/**
	 * Lists all notes through a button gui.
	 * @see
	 * GuiButtonInput
	 */
	private void listNotesGUI(){
		try {
			while(true) {
				ArrayList<String> noteTitles = new ArrayList<>();
				ArrayList<String> noteBodies = new ArrayList<>();

				for (int i = 0; i < notes.size(); i++) {
					Note note = notes.get(i);
					noteTitles.add(note.getTitleText());
					noteBodies.add(note.getBodyText());
				}
				GuiButtonInput input = new GuiButtonInput("Note List", noteTitles, noteBodies);
				int selection = noteTitles.indexOf(input.getInput());
				editNoteGUI(selection);
			}
		} catch (QuitException e) {
			//
		}
	}

	/**
	 * Displays and allows user to make changes to the Note
	 * with given Id using a fieldInput gui.
	 * @param id
	 * The Note to edit.
     */
	private void editNoteGUI(int id){
		try {
			Note note = notes.get(id);
			ArrayList<String> labels = new ArrayList<>();
			labels.add("Title:");
			labels.add("Body:\0");
			ArrayList<String> content = new ArrayList<>();
			content.add(note.getTitleText());
			content.add(note.getBodyText());

			GuiFieldInput input =
					new GuiFieldInput("Edit Note", labels, content);
			input.getInput();
			String title = input.getStringAnswers().get(0);
			String body = input.getStringAnswers().get(1);
			note.deleteNote();
			notes.set(id, new Note(title, body));
		} catch (QuitException e){
			//
		}
	}

	/**
	 * Displays notes using a button input gui and
	 * deletes the selected Note.
	 */
	private void removeNoteGUI(){
		try {
			while(true) {
				ArrayList<String> noteTitles = new ArrayList<>();
				for (int i = 0; i < notes.size(); i++) {
					noteTitles.add(notes.get(i).getTitleText());
				}
				GuiButtonInput input = new GuiButtonInput("Delete Note", noteTitles);
				if (0 == JOptionPane.showConfirmDialog(input, "Delete " + input.getInput(), "Delete Note", JOptionPane.YES_NO_OPTION)) {
					int id = noteTitles.indexOf(input.getInput());
					notes.get(id).deleteNote();
					notes.remove(id);
					break;
				}
			}
		} catch (QuitException e){
			//
		}
	}

}