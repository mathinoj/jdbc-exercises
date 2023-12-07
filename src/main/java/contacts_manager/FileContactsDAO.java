package contacts_manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileContactsDAO {
    private final static String directory = "data";
    private final static String filename = "contacts.txt";
    private final static Path dataFile = Paths.get(directory, filename);
    private Connection connection = null;


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


    public List<Contact> searchContacts(String searchTerm) {
        try {
            List<String> lines = Files.readAllLines(dataFile);
            for (String line: lines) {
                if(line.toLowerCase().startsWith(searchTerm.toLowerCase())){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public void open() {
//
//    }
//
//
//    public void close() {
//        close();
//    }
}
