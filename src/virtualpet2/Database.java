/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 * Database class to manage the connection and interaction with the VirtualPet
 * database. This class includes methods for establishing a database connection,
 * creating tables, and disconnecting from the database when no longer needed.
 *
 * @author vrish
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.drda.NetworkServerControl;
import java.net.InetAddress;

public class Database {

    // Singleton instance for maintaining a single connection to the database throughout the program
    private static Connection connection = null;

    // Database URL, Username, and Password constants for connecting to the VirtualPet database
    private static final String DB_URL = "jdbc:derby://localhost:1527/VirtualPet;create=true";
    private static final String DB_USER = "vrishab";
    private static final String DB_PASSWORD = "password";

    // Start the Derby network server if it's not running
    public static void startServer() {
        try {
            NetworkServerControl serverControl = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
            serverControl.start(null);
            System.out.println("Derby server started");
        } catch (Exception e) {
            System.out.println("Derby server is already running or failed to start: " + e.getMessage());
        }
    }

    /**
     * Method to establish a singleton connection to the database. Only connects
     * if there is no existing connection or if the current connection is
     * closed. Ensures the program does not create multiple connections
     * unnecessarily.
     */
    public static void connect() {
        try {
            // Check if the connection is null or closed, then establish a new connection
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connected to the network database.");
            }
        } catch (SQLException e) {
// Output stack trace for debugging if the connection fails
            System.out.println("Failed to connect to the database.");
        }
    }

    /**
     * Method to retrieve the active database connection. Ensures there is an
     * active connection by calling the connect() method if the current
     * connection is null or closed.
     *
     * @return the established database connection
     */
    public static Connection getConnection() {
        connect(); // Establish or re-establish connection if it's null or closed
        return connection;
    }

    /**
     * Method to safely disconnect from the database. Closes the connection if
     * it's currently open. Important to call this method at the end of the
     * program to free up resources.
     */
    public static void disconnect() {
        try {
            // Check if connection is open before attempting to close it
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database disconnected.");
            }
        } catch (SQLException e) {
            // Output stack trace for debugging if disconnection fails

        }
    }

    /**
     * Method to create the PETS table within the database. Checks if the table
     * already exists before attempting to create it. The table includes fields
     * for pet attributes such as name, type, hunger, fun, sleep, and an owner
     * ID. The owner ID is a foreign key referencing the PLAYERS table to
     * associate each pet with a specific player.
     */
    public static void createPetTable() {
        // Ensure the connection is established before creating the table
        if (connection != null) {
            try {
                // Query to verify if the PETS table already exists
                String checkTableExistsQuery = "SELECT 1 FROM vrishab.pets FETCH FIRST ROW ONLY";
                try ( var stmt = connection.createStatement()) {
                    // If the table exists, this will succeed without exception
                    stmt.executeQuery(checkTableExistsQuery);
                } catch (SQLException e) {
                    // If the table does not exist, create it
                    String createTableSQL = "CREATE TABLE vrishab.pets ("
                            + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                            + // Primary key with auto-generated ID
                            "name VARCHAR(50), "
                            + // Pet name, up to 50 characters
                            "type VARCHAR(50), "
                            + // Pet type, up to 50 characters
                            "hunger INT, "
                            + // Pet's hunger level represented as an integer
                            "fun INT, "
                            + // Pet's fun level represented as an integer
                            "sleep INT, "
                            + // Pet's sleep level represented as an integer
                            "owner INT, "
                            + // Owner ID, references the ID in the PLAYERS table
                            "FOREIGN KEY (owner) REFERENCES vrishab.players(id))"; // Foreign key constraint linking owner to PLAYERS table
                    try ( var stmt = connection.createStatement()) {
                        stmt.execute(createTableSQL); // Execute table creation statement
                        System.out.println("PETS table created with owner association.");
                    }
                }
            } catch (SQLException e) {
// Output error message and stack trace if table creation fails
                System.out.println("Failed to create or verify pet table.");
            }
        } else {
            // Error message if connection is not established
            System.out.println("Connection is null. Cannot create pet table.");
        }
    }

    /**
     * Method to create the PLAYERS table within the database. Checks if the
     * table already exists before attempting to create it. The table includes
     * fields for player attributes, with ID as the primary key.
     */
    public static void createPlayerTable() {
        // Ensure the connection is established before creating the table
        if (connection != null) {
            try {
                // Query to verify if the PLAYERS table already exists
                String checkTableExistsQuery = "SELECT 1 FROM vrishab.players FETCH FIRST ROW ONLY";
                try ( var stmt = connection.createStatement()) {
                    // If the table exists, this will succeed without exception
                    stmt.executeQuery(checkTableExistsQuery);
                } catch (SQLException e) {
                    // If the table does not exist, create it
                    String createTableSQL = "CREATE TABLE vrishab.players ("
                            + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                            + // Primary key with auto-generated ID
                            "name VARCHAR(50))"; // Player name, up to 50 characters
                    try ( var stmt = connection.createStatement()) {
                        stmt.execute(createTableSQL); // Execute table creation statement
                        System.out.println("PLAYERS table created.");
                    }
                }
            } catch (SQLException e) {
// Output error message and stack trace if table creation fails
                System.out.println("Failed to create or verify player table.");
            }
        } else {
            // Error message if connection is not established
            System.out.println("Connection is null. Cannot create player table.");
        }
    }

}
