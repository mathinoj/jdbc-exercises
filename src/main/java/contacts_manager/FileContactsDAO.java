package contacts_manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileContactsDAO implements ContactsDAO{
    private final static String directory = "data";
    private final static String filename = "contacts.txt";
    private final static Path dataFile = Paths.get(directory, filename);

    @Override
    public List<Contact> fetchContacts() {
        try {
            List<String> lines = Files.readAllLines(dataFile);
//            List<Contact> lines = Files.lines()
            for (String line: lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long insertContact(Contact contact) {
        return 0;
    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public List<Contact> searchContacts(String searchTerm) {
        return null;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
