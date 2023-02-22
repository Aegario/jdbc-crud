package JDBC;

import java.sql.*;

enum Sex {
    M,
    F,
}

public class JDBC {
    private static String DB_URI = "jdbc:postgresql://localhost:5432/jdbc-test";
    private static Connection conn;
    private static Statement stmt;

    static {
        try {
            conn = DriverManager.getConnection(DB_URI);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JDBC jdbcInstance = new JDBC();
        // jdbcInstance.createNewClient("Donald", 24, Sex.M);
        //  jdbcInstance.getClient(3);
        jdbcInstance.closeConnections();
    }

    public void initializeDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void createNewClient(String name, int age, Sex sex) {
        String insertQuery = "insert into client (name, age, sex)" +
                "values ('" + name + "', " + age + ", '" + sex + "')";

        try {
            int rs = stmt.executeUpdate(insertQuery);
            System.out.println("ResultSet: " + rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateClient(int id, String name, int age, Sex sex) {
        String updateQuery = "update client " +
                "set name = '" + name + "', age = " + age + ", sex = '" + sex +
                "' where client.id = " + id + ";";

        try {
            int rs = stmt.executeUpdate(updateQuery);
            System.out.println("ResultSet: " + rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void deleteClient(int id) {
        String deleteQuery = "delete from client " +
                "where client.id = " + id + ";";

        try {
            int deletedRows = stmt.executeUpdate(deleteQuery);
            System.out.println("deleted rows number: " + deletedRows);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void getClient(int id) {
        String selectQuery = "select * from client " +
                "where id = " + id + ";";

        try {
            ResultSet rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
                System.out.println(rs.getString("sex"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void closeConnections() {
        try {
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
