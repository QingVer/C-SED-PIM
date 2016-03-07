import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactMenuCLI {
	private Scanner userInputScanner;
	ArrayList<Contact> contactList = new ArrayList<Contact>();
	ArrayList<String> contactFields = new ArrayList<String>() {{
		add("Name");
	}};
	
	public ContactMenuCLI(Scanner userInputScanner){
		this.userInputScanner = userInputScanner;
		loadContacts();
		showMainContactMenu();
	}

	public void showMainContactMenu() {
		try {
			System.out.println("Type Help For Command List");
			while (true) {
				System.out.println("Enter Command (Type \"Quit\" At Any Point To Go Back):");
				String input = getInput();
				System.out.println();
				String output = parseCommand(input);
				if (output.equals("EXIT")) {
					return;
				} else {
					System.out.println(output);
				}
			}
		} catch (QuitException e) {
			System.out.println("Returning To Menu...");
		}
	}
	
	public void listContacts(){
		System.out.print("##### CONTACTS #####");
		for (int i = 0; i < contactList.size(); i++) {
			Contact contact = contactList.get(i);
			System.out.print("\n" + (i + 1) + ") " + contact.viewContact().get(0));
		}
	}

	public void listContact(int id) {
		try {
			ArrayList contactInfo = contactList.get(id).viewContact();
			System.out.println("##### CONTACT #####");
			for (int i = 0; i < contactInfo.size(); i++) {
				System.out.println(contactInfo.get(i));
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Contact Does Not Exist!");
		}
	}

	public void loadContacts() {
		try {
			File contactsDirectory = new File(Contact.getContactsDirectory());
			File contacts[] = contactsDirectory.listFiles();
			for (int i = 0; i < contacts.length; i++) {
				contactList.add(new Contact(contacts[i]));
			}
		} catch (NullPointerException e) {
			System.err.println("No Contacts To Load");
			e.printStackTrace();
		}
	}

	public String parseCommand(String command) {
		String commands[] = command.split(" ");
		String output = "FAIL";
		switch (commands[0].toUpperCase()) {
			case "EDITCONTACT":
				output = editContactCLI();
				break;
			case "NEWFIELD":
				newContactCLI();
				break;
			case "NEW":
				output = newContactCLI();
				break;
			case "HELP":
				output = "Help - Lists All Commands" +
						"New - Creates A New Contact\n" +
						"NewField - Creates A New Field For A Contact" +
						"Edit - Change Field Contents For A Contact\n" +
						"List - Prints All Contacts Names\n" +
						"List <id> - Print All Contact Details For Given ID\n" +
						"Remove - Removes A Contact\n" +
						"Remove <id> - Removes Given Contact\n" +
						"RemoveField - Removes A Field From A Contact\n";
				break;
			case "LIST":
				if (commands.length == 1) {
					listContacts();
				} else {
					listContact(Integer.parseInt(commands[1]));
				}
				output = "";
				break;
			case "REMOVE":
				if (commands.length == 1) {
					output = removeContactCLI();
				} else {
					if (removeContact(Integer.parseInt(commands[1]))) {
						output = "SUCCESS";
					}
				}
				break;
			case "REMOVEFIELD":
				removeFieldCLI();
				break;
		}
		return output;
	}

	public boolean editContact(int id, String nameOfField, String newContents) {
		try {
			Contact contact = contactList.get(id);
			return contact.modifyField(nameOfField, newContents);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Field Doesn't Exist!");
			return false;
		}
	}

	public String editContactCLI() {
		try {
			listContacts();
			while (true) {
				System.out.print("\n\nWhich Contact Would You Like To Change (ID): ");
				int id = Integer.parseInt(getInput());
				if (id <= contactList.size()) {
					System.out.println();
					listContact(id);
					System.out.print("\nEnter Field Name To Edit/Add: ");
					String fieldName = getInput();
					System.out.print("\nEnter The Content For The Field: ");
					String fieldContent = getInput();
					boolean contactUpdated = editContact(id, fieldName, fieldContent);
					if (contactUpdated) {
						return "SUCCESS";
					} else {
						if (addField(id, fieldName, fieldContent)) {
							return "SUCCESS";
						}
					}
					return "FAIL";
				} else {
					System.out.println("\nInvalid ID");
				}
			}
		} catch (QuitException e) {
			return "CANCELLED";
		}
	}

	public void newContact(ArrayList<String> initialFields) {
		contactList.add(new Contact(initialFields));
	}

	public String newContactCLI() {
		try {
			ArrayList<String> fields = new ArrayList<String>();
			boolean finished = false;
			System.out.println("###### Mandatory Fields ######\n");
			for (int i = 0; i < contactFields.size(); i++) {
				System.out.println("Enter The Content For \"" + contactFields.get(i) + "\" :");
				String field = contactFields.get(i) + ":" + getInput();
				fields.add(field);
			}
			System.out.print("\n##### Custom Fields #####\n\nSkip Adding Custom Fields? (y/n): ");
			while (!finished) {
				boolean complete = false;
				while (!complete) {
					String input = getInput();
					if (input.equals("y")) {
						finished = true;
						complete = true;
					} else if (input.equals("n")) {
						complete = true;
					} else {
						System.out.println("Enter y or n");
					}
				}
				if (!finished) {
					System.out.print("Enter Field Name: ");
					String fieldName = getInput();
					System.out.print("Enter Field Content: ");
					String fieldContent = getInput();
					fields.add(fieldName + ":" + fieldContent);
					System.out.print("Finished (y/n):");
				}
			}
			newContact(fields);
			listContact(contactList.size() - 1);
			return "SUCCESS";
		} catch (QuitException e) {
			return "CANCELLED";
		}
	}

	public ArrayList<String> viewContact(int index) {
		try {
			return contactList.get(index).viewContact();
		} catch (NullPointerException e) {
			System.err.print("No Contact With That ID");
			return null;
		}
	}

	public boolean addField(int id, String fieldName, String fieldContent) {
		return contactList.get(id).addField(fieldName, fieldContent);
	}

	private String addFieldCLI() {
		try {
			System.out.print("\nWhich Contact Would You Like To Edit (ID):");
			int id = Integer.parseInt(getInput());
			System.out.print("\nWhat Is The New Field Called: ");
			String fieldName = getInput();
			System.out.print("\nEnter Field Content: ");
			String fieldContent = getInput();
			if (addField(id, fieldName, fieldContent)) {
				return "SUCCESS";
			} else {
				return "FAIL";
			}
		} catch (QuitException e) {
			return "CANCELLED";
		}
	}

	public boolean removeContact(int id) {
		try {
			if (contactList.get(id - 1).deleteContact()) {
				contactList.remove(id - 1);
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Contact Does Not Exist!");
			return false;
		}
	}

	private String removeContactCLI() {
		try {
			System.out.print("\nWhich Contact Would You Like To Remove (ID):");
			int id = Integer.parseInt(getInput());
			return removeContact(id) + "";
		} catch (QuitException e) {
			return "CANCELLED";
		}
	}

	private boolean removeField(int id, String fieldName) {
		try {
			return contactList.get(id).removeField(fieldName);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Contact Does Not Exist!");
			return false;
		}
	}

	private String removeFieldCLI() {
		try {
			System.out.print("\nWhich Contact Would You Like To Remove A Field From (ID):");
			int id = Integer.parseInt(getInput());
			listContact(id);
			System.out.print("\nWhich Field Would You Like To Remove: ");
			String fieldName = getInput();
			if (removeField(id, fieldName)) {
				return "SUCCESS";
			} else {
				return "FAIL";
			}
		} catch (QuitException e) {
			return "CANCELLED";
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
}
