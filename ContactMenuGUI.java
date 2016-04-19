import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class ContactMenuGUI {
	ArrayList<Contact> contactList = new ArrayList<>();
	@SuppressWarnings("serial")	ArrayList<String> contactStringFields =  new ArrayList<String>(){{
		add("Name:");
		add("Address:");
		add("Email Address:");
	}};
	@SuppressWarnings("serial")	ArrayList<String> contactIntFields = new ArrayList<String>(){{
		add("Phone Number: ");
	}};

	/**
	 * loads all the contacts from the folder.
	 */
	public ContactMenuGUI(){
		try {
			File contactsDirectory = new File(Contact.fileDirectory);
			File contacts[] = contactsDirectory.listFiles();
			for (int i = 0; i < contacts.length; i++) {
				contactList.add(new Contact(contacts[i]));
			}
		} catch (NullPointerException e) {
			System.err.println("No Contacts To Load");
			//e.printStackTrace();
		}
	}

	/**
	 * shows the main menu using a Button input gui
	 * @see GuiButtonInput
	 */
	public void showGuiMenu(){
		try {
			while (true) {
				ArrayList<String> options = new ArrayList<>();
				options.add("Make New Contact");
				options.add("View/Edit Contacts");
				options.add("Delete Contact");
				GuiButtonInput input = new GuiButtonInput("Contacts Menu", options);
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
						newContactGUI();
						break;
					case 1:
						listContactsGUI();
						break;
					case 2:
						removeContactGUI();
						break;
				}
			}
		} catch (QuitException e){
			//
		}
	}

	/**
	 * Lists all contacts using a button input gui.
	 * @see GuiButtonInput
	 */
	private void listContactsGUI(){
		try {
			while(true) {
				ArrayList<String> contactNames = new ArrayList<>();
				ArrayList<String> contactDetails = new ArrayList<>();

				for (int i = 0; i < contactList.size(); i++) {
					Contact contact = contactList.get(i);
					contactNames.add(contact.viewContact().get(0));
					String contactFields = "";
					for (int j = 1; j < contact.viewContact().size(); j++) {
						contactFields += contact.viewContact().get(j) + "\n";
					}
					contactDetails.add(contactFields);
				}

				GuiButtonInput input = new GuiButtonInput("Contact List", contactNames, contactDetails);
				int selection = contactNames.indexOf(input.getInput());
				editContactGUI(selection);
			}
		} catch (QuitException e) {
			//
		}
	}

	/**
	 * Lists contact names using a button input gui.
	 * Then deletes the selected contact.
	 * @see GuiButtonInput
	 */
	private void removeContactGUI(){
		try {
			while(true) {
				ArrayList<String> contactNames = new ArrayList<>();
				for (int i = 0; i < contactList.size(); i++) {
					contactNames.add(contactList.get(i).viewContact().get(0));
				}
				GuiButtonInput input = new GuiButtonInput("Delete Contact", contactNames);
				if (0 == JOptionPane.showConfirmDialog(input, "Delete " + input.getInput(), "Delete Contact", JOptionPane.YES_NO_OPTION)) {
					removeContact(contactNames.indexOf(input.getInput()));
					break;
				}
			}
		} catch (QuitException e){
			//
		}
	}

	/**
	 * Displays contact info and allows users to change it.
	 * @param id
	 * The contact to edit.
	 * @see
	 * GuiFieldInput
     */
	private void editContactGUI(int id){
		try {
			Contact contact = contactList.get(id);
			ArrayList<String> fields = contact.viewContact();
			ArrayList<String> stringAnswers = new ArrayList<>();
			for (int i = 0; i < contactStringFields.size(); i++) {
				String field = fields.get(i);
				stringAnswers.add(field.split(": ")[1]);
			}
			ArrayList<String> intAnswers = new ArrayList<>();
			for (int i = contactStringFields.size(); i < fields.size(); i++) {
				String field = fields.get(i);
				intAnswers.add(field.split(": ")[1].trim());
			}

			GuiFieldInput input =
			new GuiFieldInput("Edit Contact", contactStringFields, stringAnswers, contactIntFields, intAnswers);
			input.getInput();

			fields = new ArrayList<>();
			for (int i = 0; i < contactStringFields.size(); i++) {
				fields.add(contactStringFields.get(i) + input.getStringAnswers().get(i));
			}
			for (int i = 0; i < contactIntFields.size(); i++) {
				fields.add(contactIntFields.get(i) + input.getIntAnswers().get(i));
			}
			contact.deleteContact();
			contactList.set(id, new Contact(fields));
		} catch (QuitException e){
			//
		}
	}

	/**
	 * Creates a new Contact using a Field Input Gui.
     */
	public void newContactGUI(){
		try {
			GuiFieldInput input = new GuiFieldInput("NEW CONTACT", contactStringFields, contactIntFields);
			input.getInput();
			ArrayList<String> fields = new ArrayList<>();
			for (int i = 0; i < contactStringFields.size(); i++) {
				fields.add(contactStringFields.get(i) + input.getStringAnswers().get(i));
			}
			for (int i = 0; i < contactIntFields.size(); i++) {
				fields.add(contactIntFields.get(i) + input.getIntAnswers().get(i));
			}
			contactList.add(new Contact(fields));
		} catch (QuitException e){
			//
		}
	}

	/**
	 * deletes the given contact.
	 * @param id
	 * the contact to remove.
	 * @return
	 * If the contact was removed successfully
     */
	public boolean removeContact(int id) {
		try {
			if (contactList.get(id).deleteContact()) {
				contactList.remove(id);
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Contact Does Not Exist!");
			return false;
		}
	}
}
