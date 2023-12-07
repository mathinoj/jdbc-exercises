package contacts_manager;

import com.mysql.cj.jdbc.Driver;
import com.sun.source.tree.TryTree;
import config.Config;
import dao.MySQLAlbumsException;
import models.Album;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQLContactsDAO implements ContactsDAO{
    private Connection connection = null;

    public MySQLContactsDAO() {
        open();
    }

    @Override
    public List<Contact> fetchContacts() {
                System.out.println("Name | Phone number\n" +
                "---------------");
        List<Contact> contacts = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from contacts");
             while(rs.next()){
                 Contact contact = new Contact(rs.getString("name"), rs.getString("phone") + '\n');

                 contact.setId(rs.getLong("id"));
                 contacts.add(contact);
//                 System.out.println("c " +contact);
//                 System.out.println("c++ " +contacts);
            }
        } catch (SQLException e){
            throw new RuntimeException("Error executing query: " +e.getMessage() +"!!!");
        }
        return contacts;
//        return null;
    }

    @Override
    public long insertContact(Contact contact) {
                String contactLine = String.format("%s|%s", contact.getFullName(), contact.getPhoneNumber());
        try {
            PreparedStatement st = connection.prepareStatement(
                    "insert into contacts " +
                            " (name, phone) " +
                            " values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, contact.getFullName());
            st.setString(2, contact.getPhoneNumber());
            st.executeUpdate();
            ResultSet keys = st.getGeneratedKeys();
            keys.next();

            long newId = keys.getLong(1);
            return newId;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
//        return 0;
    }

    @Override
    public void deleteByName(String name) {
        List<Contact> contacts = new ArrayList<>();
        Contact contact = null;
        try{
            PreparedStatement st = connection.prepareStatement("delete from contacts " +
            " where name = ? ");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Contact> searchContacts(String searchTerm) {
        return null;
    }

    public void open() {
//        public void createConnection() throws MySQLAlbumsException {
        System.out.printf("Trying to connect... ");
        try {
            //TODO: create the connection and assign it to the instance variable
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUser(),
                    Config.getPassword()
            );
            System.out.println("connection created.");
        } catch (SQLException e) {
            throw new MySQLAlbumsException("connection failed!!!");
        }
    }



    public void close() {
        if(connection == null) {
            System.out.println("Connection aborted.");
            return;
        }
        try {
            //TODO: close the connection
            connection.close();
            System.out.println("Connection closed.");
        } catch(SQLException e) {
            // ignore this
        }
    }
}
