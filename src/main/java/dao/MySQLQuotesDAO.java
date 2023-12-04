package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import models.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLQuotesDAO {
    private Connection connection = null; //every connection object we create, by default, will be null
    public void createConnection(){
            System.out.println("Connecting to db...");
        try {
            DriverManager.registerDriver(new Driver());  //register a driver
            connection = DriverManager.getConnection( //this opens the connection
                    Config.getUrl(),
                    Config.getUser(),
                    Config.getPassword()
            );
        } catch (SQLException sqlx){
            System.out.println("Error connecting to db..." +sqlx.getMessage());
        }
    }
    public List<Quote> getQuotes(){ //we removed static, cuz static means only one class needs the method
//        Connection connection = null; //removed as part of out refactoring and start of insertQuote. Now its on line 11.
        List<Quote> quotes = new ArrayList<>();
        try {
            //creates a statement object
            Statement statement = connection.createStatement();

            //execute the created statement
            ResultSet resultSet = statement.executeQuery("select * from quotes");
            while(resultSet.next()){
                quotes.add(new Quote(
                        resultSet.getString("author"),
                        resultSet.getString("content")
                        )
                );
            }
        } catch (SQLException sqlx){
            System.out.println(sqlx.getMessage());
        }
        return quotes;
    }

    public void closeConnection(){
        System.out.println("Closing DB connection...");
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException sqlx){
                System.out.println(sqlx.getMessage());
            }
        }

    }

    public long insertQuote(){
        //we need to:
        //1 - create a connection - can be made into one method to be used by multiple methods
        //2 - create and execute statement
        //3 - close the connection - can be made into one method to be used by multiple methods
     return 0;
    }
}
