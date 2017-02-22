package eangeli.stealthwatch;

import java.sql.*;
import java.util.Properties;

/**
 * PostgreSQL<br>
 * Defaults to localhost and port 6543.<br>
 * The database and user must be set or it will throw an exception on connect.
 */
public class PostgreSQL {

    String url = "localhost";
    String port = "6543";
    String db = null;
    String user = null;
    String pw = "";
    Connection connection = null;

    public PostgreSQL url(String url){
        this.url = url;
        return this;
    }

    public PostgreSQL port(String port){
        this.port = port;
        return this;
    }

    public PostgreSQL db(String db){
        this.db = db;
        return this;
    }

    public PostgreSQL user(String user){
        this.user = user;
        return this;
    }

    public PostgreSQL pw(String pw){
        this.pw = pw;
        return this;
    }

    public String getFullUrl(){
        return "jdbc:postgresql://"+url+":"+port+"/"+db;
    }

    public PostgreSQL connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pw);
        if(db==null){
            throw new SQLException("The database (db) must be set.");
        }
        if(user==null){
            throw new SQLException("The user must be set.");
        }
        if(connection==null||connection.isClosed()) {
            connection = DriverManager.getConnection(getFullUrl(), props);
        }
        return this;
    }

    public ResultSet doQuery(String query)throws ClassNotFoundException, SQLException{
        if(connection==null||connection.isClosed()){
            connect();
        }
        Statement q = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        System.out.println("executing query:\n\t"+query);
        return q.executeQuery(query);
    }

    public void close()throws SQLException{
        if(connection!=null&&!connection.isClosed()){
            connection.close();
        }
        connection = null;
    }
}
