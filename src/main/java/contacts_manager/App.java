package contacts_manager;

import dao.MySQLAlbumsDAO;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ContactsDAO contactsDAO = new MySQLContactsDAO();

//        contactsDAO.createConnection();

        Input input = new Input();

        while(true){
            System.out.println("1. View contacts.\n" +
                    "2. Add a new contact.\n" +
                    "3. Search a contact by name.\n" +
                    "4. Delete an existing contact by name.\n" +
                    "5. Exit.\n" +
                    "Enter an option (1, 2, 3, 4 or 5):");
            int option = input.getInt(1, 5);
            switch (option){
                case 1:
                    List<Contact> contacts = contactsDAO.fetchContacts();
                    System.out.println(contacts);
                    break;
//                    FileUtils.readFile();
//                    break;
                case 2:
                    String fn = input.getString("Give me the full name");
                    String phone = input.getString("Give me the phone number");
                    Contact contact = new Contact(fn, phone);
                    long newId = contactsDAO.insertContact(contact);
                    contact.setId(newId);
//                    FileUtils.writeContactToFile(contact);
                    break;
                case 3:
                    String term = input.getString("Give me the name to search");
                    contactsDAO.searchContacts(term);
//                    FileUtils.search(term);
                    break;
                case 4:
                    String aNumber = input.getString("Give me the name to delete");
//                    FileUtils.delete(aNumber);
                    contactsDAO.deleteByName(aNumber);
                    System.out.println(aNumber+ " has been deleted.");
                    break;
                case 5:
                    System.exit(0);
            }

        }


    }
}
