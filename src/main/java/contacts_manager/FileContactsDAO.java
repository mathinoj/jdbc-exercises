package contacts_manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileContactsDAO implements ContactsDAO{
    private final static String directory = "data";
    private final static String filename = "contacts.txt";
    private final static Path dataFile = Paths.get(directory, filename);

    @Override
    public List<Contact> fetchContacts() {
        System.out.println("Name | Phone number\n" +
                "---------------");
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
        String contactLine = String.format("%s|%s", contact.getFullName(), contact.getPhoneNumber());
        try {
            Files.write(
                    dataFile,
                    Arrays.asList(contactLine), // list with one item
                    StandardOpenOption.APPEND
            );
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteByName(String name) {
        try {
            List<String> lines = Files.readAllLines(dataFile);
            List<String> writeLines = new ArrayList<>();

            for (String line: lines) {
                if(!line.toUpperCase().startsWith(name.toUpperCase())){
                    writeLines.add(line);
                }
            }

            Files.write(dataFile, writeLines);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
