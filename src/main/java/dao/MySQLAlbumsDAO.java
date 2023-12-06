package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import models.Album;
import models.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAlbumsDAO extends MySQLDAO{
    // initialize the connection to null so we know whether or not to close it when done
    private Connection connection = null;

    public void createConnection() throws MySQLAlbumsException {
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

    private Album makeAlbumFromDB(ResultSet record) throws MySQLAlbumsException {
        Album album = new Album();
        try{
            album.setId(record.getLong("id"));
            album.setName(record.getString("name"));
            album.setArtist(record.getString("artist"));
            album.setReleaseDate(record.getInt("release_date"));
            album.setSales(record.getDouble("sales"));
            album.setGenre(record.getString("genre"));
        }catch (SQLException e) {
            throw new MySQLAlbumsException("Error executing query: " + e.getMessage() + "!!!");
        }
        return album;
    }

    public int getTotalAlbums() throws MySQLAlbumsException {
        int count = 0;
        try {
            //TODO: fetch the total number of albums from the albums table and assign it to the local variable

            Statement statement = connection.createStatement();

            //OTHER WAY
//            ResultSet resultSet = statement.executeQuery("select count(*) from albums");
//            resultSet.next();
//            count = resultSet.getInt(1);

            ResultSet resultSet = statement.executeQuery("select * from albums");
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            throw new MySQLAlbumsException("Error executing query: " + e.getMessage() + "!!!");
        }
        return count;
    }

    public List<Album> fetchAlbums() {
        List<Album> albums = new ArrayList<>();
        // TODO: write your code here

        try {
            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from albums");
            while (resultSet.next()) {
                albums.add(makeAlbumFromDB(resultSet));
            }
        } catch (SQLException e) {
            throw new MySQLAlbumsException("Error executing query: " + e.getMessage() + "!!!");
        }

        return albums;
    }

    public Album fetchAlbumById(long id) {
        Album album = null;

        // TODO: write your code here
        try{
            PreparedStatement st = connection.prepareStatement("select * from albums" + " where id = ?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();

            album = new Album();
            album.setId(rs.getLong("id"));
            album.setArtist(rs.getString("artist"));
            album.setName(rs.getString("name"));
            album.setReleaseDate(rs.getInt("release_date"));
            album.setSales(rs.getDouble("sales"));
            album.setGenre(rs.getString("genre"));

        } catch (SQLException e) {
            throw new MySQLAlbumsException("Error executing query: " + e.getMessage() + "!!!");
        }


        return album;
    }

    // Note that insertAlbum should return the id that MySQL creates for the new inserted album record
    public long insertAlbum(Album album) throws MySQLAlbumsException {
        long id = 0;

        // TODO: write your code here
        try {
        PreparedStatement st = connection.prepareStatement("insert into albums " +
             " (artist, name, release_date, sales, genre) " +
             " values(?, ?, ?, ?, ?) ",
             Statement.RETURN_GENERATED_KEYS);
            st.setString(1, album.getArtist());
            st.setString(2, album.getName());
            st.setInt(3, album.getReleaseDate());
            st.setDouble(4, album.getSales());
            st.setString(5, album.getGenre());

            st.executeUpdate();
            ResultSet keys = st.getGeneratedKeys();
            keys.next();

            long newId = keys.getLong(1);
            return newId;


        } catch(SQLException e){
            throw new RuntimeException(e);
        }
//        return id;
    }

    public void updateAlbum(Album album) throws MySQLAlbumsException {

        // TODO: write your code here
        try{
            PreparedStatement st = connection.prepareStatement("update albums " +
                    " set artist = ? " +
                    " , name = ? " +
                    " , release_date = ? " +
                    " , sales = ? " +
                    " , genre = ? " +
                    "where id = ? ");
            st.setString(1, album.getArtist());
            st.setString(2, album.getName());
            st.setInt(3, album.getReleaseDate());
            st.setDouble(4, album.getSales());
            st.setString(5, album.getGenre());
            st.setLong(6, album.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAlbumById(long id) throws MySQLAlbumsException {

        // TODO: write your code here
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement(
                    "delete from albums " +
                    " where id = ? ");
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
