package dao;

import config.Config;
import models.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAlbumsDAO {
    // initialize the connection to null so we know whether or not to close it when done
    private Connection connection = null;

    // we can do simple testing using the class' main method
//    public static void main(String[] args) {
//
//
//        MySQLAlbumsDAO albumDao = new MySQLAlbumsDAO();
//
//        try {
//            albumDao.createConnection();
//
//            System.out.println("Using the connection...");
//            int numAlbums = albumDao.getTotalAlbums();
//            System.out.println("Total # of album records: " + numAlbums);
//        } catch(MySQLAlbumsException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            albumDao.closeConnection();
//        }
//
//    }



    public void createConnection() throws MySQLAlbumsException {
        System.out.printf("Trying to connect... ");
        try {
            //TODO: create the connection and assign it to the instance variable
//            DriverManager.registerDriver(new Driver());
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

    public int getTotalAlbums() throws MySQLAlbumsException {
        int count = 0;
        try {
            //TODO: fetch the total number of albums from the albums table and assign it to the local variable

            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select count(*) from albums");
            ResultSet resultSet = statement.executeQuery("select * from albums");
//            while (resultSet.next()) {
            resultSet.next();
            count = resultSet.getInt(1);
//                count++;
//            }
        } catch (SQLException e) {
            throw new MySQLAlbumsException("Error executing query: " + e.getMessage() + "!!!");
        }
        return count;
    }


    public void closeConnection() {
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

//    public void closeConnection(){
//        System.out.println("Closing DB connection...");
//        if(connection != null){
//            try{
//                connection.close();
//            }catch(SQLException sqlx){
//                System.out.println(sqlx.getMessage());
//            }
//        }
//
//    }