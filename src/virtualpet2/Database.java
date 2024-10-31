/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrish
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection = null;
    private static final String DB_URL = "jdbc:derby://localhost:1527/VirtualPet";
    private static final String DB_USER = "vrishab";
    private static final String DB_PASSWORD = "password";

    // Establishes a singleton connection if it's null or closed
    public static void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connected to the network database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }

    // Ensures the connection is active before returning it
    public static Connection getConnection() {
        connect(); // Reconnects if the connection is null or closed
        return connection;
    }

    // Method to disconnect from the database (use at the end of the program)
    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database disconnected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public static void createPetTable() {
    if (connection != null) {
        try {
            String checkTableExistsQuery = "SELECT 1 FROM vrishab.pets FETCH FIRST ROW ONLY";
            try (var stmt = connection.createStatement()) {
                stmt.executeQuery(checkTableExistsQuery);
                System.out.println("PETS table already exists.");
            } catch (SQLException e) {
                // Update the id column to be auto-generated
                String createTableSQL = "CREATE TABLE vrishab.pets (" +
                        "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + // Set id to auto-generate
                        "name VARCHAR(50), " +
                        "type VARCHAR(50), " +
                        "hunger INT, " +
                        "fun INT, " +
                        "sleep INT, " +
                        "owner INT, " +
                        "FOREIGN KEY (owner) REFERENCES vrishab.players(id))";
                try (var stmt = connection.createStatement()) {
                    stmt.execute(createTableSQL);
                    System.out.println("PETS table created with owner association.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create or verify pet table.");
        }
    } else {
        System.out.println("Connection is null. Cannot create pet table.");
    }
}


    public static void createPlayerTable() {
        if (connection != null) {
            try {
                String checkTableExistsQuery = "SELECT 1 FROM vrishab.players FETCH FIRST ROW ONLY";
                try (var stmt = connection.createStatement()) {
                    stmt.executeQuery(checkTableExistsQuery);
                    System.out.println("PLAYERS table already exists.");
                } catch (SQLException e) {
                    String createTableSQL = "CREATE TABLE vrishab.players (" +
                            "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                            "name VARCHAR(50))";
                    try (var stmt = connection.createStatement()) {
                        stmt.execute(createTableSQL);
                        System.out.println("PLAYERS table created.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to create or verify player table.");
            }
        } else {
            System.out.println("Connection is null. Cannot create player table.");
        }
    }

    public static void dropAllTables() {
        if (connection != null) {
            try (Statement stmt = connection.createStatement()) {
                // Drop the PETS table first, as it has a foreign key dependency on PLAYERS
                try {
                    stmt.executeUpdate("DROP TABLE vrishab.pets");
                    System.out.println("PETS table dropped.");
                } catch (SQLException e) {
                    System.out.println("PETS table does not exist or cannot be dropped.");
                }

                // Drop the PLAYERS table
                try {
                    stmt.executeUpdate("DROP TABLE vrishab.players");
                    System.out.println("PLAYERS table dropped.");
                } catch (SQLException e) {
                    System.out.println("PLAYERS table does not exist or cannot be dropped.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to drop tables.");
            }
        } else {
            System.out.println("Connection is null. Cannot drop tables.");
        }
    }
}
