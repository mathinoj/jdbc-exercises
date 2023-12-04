package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import dao.MySQLQuotesDAO;
import models.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuotesTest {
    public static void main(String[] args) {
        MySQLQuotesDAO quotesDAO = new MySQLQuotesDAO(); //this is a data access object
        quotesDAO.createConnection();
        List<Quote> quotesFromDb = quotesDAO.getQuotes();
        for(Quote quote : quotesFromDb){
//            System.out.println(quote);
            System.out.println(quote.getAuthor() + " quoted this: " + "\""+quote.getContent()+"\"");
        }
        quotesDAO.closeConnection();
    }
}
