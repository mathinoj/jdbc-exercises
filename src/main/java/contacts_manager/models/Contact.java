package contacts_manager.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Contact {
    // note that id is not used in the original Java 2 Contacts Manager
    // we have added it here so that it will transition better to a rel. db
    private long id;
    private String fullName;
    private String phoneNumber;

    public Contact(String fullName, String phoneNumber) {
        this.id = 1;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Contact() {

    }

    @Override
    public String toString() {
//        return fullName + " | " + phoneNumber +'\n';
        return fullName + " | " + phoneNumber;
    }


//    @Override
//    public String toString() {
//        return "Contact{" +
//                "id=" + id +
//                ", fullName='" + fullName + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                '}';
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


//    @Override
//    public List<Contact> fetchContacts() {
//        System.out.println("Name | Phone number\n" +
//                "---------------");
//        List<Contact> contacts = new ArrayList<>();
//        try {
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery("select * from contacts");
//            while(rs.next()){
//                Contact contact = new Contact(rs.getString("name"), rs.getString("phone") + '\n');
//
//                contact.setId(rs.getLong("id"));
//                contacts.add(contact);
//            }
//        } catch (SQLException e){
//            throw new RuntimeException("Error executing query: " +e.getMessage() +"!!!");
//        }
//        return contacts;
//    }