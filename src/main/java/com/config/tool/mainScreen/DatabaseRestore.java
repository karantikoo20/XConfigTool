package com.config.tool.mainScreen;

// Import necessary packages and classes
import com.config.tool.util.DatabaseConfig; // New utility class for dynamic configuration
import com.config.tool.util.DatabaseConnection; // Custom class for database connection management

import java.io.BufferedReader; // For reading input/output from the process
import java.io.IOException; // Exception handling for IO operations
import java.io.InputStreamReader; // To read command line input/output
import java.sql.Connection; // For database connection
import java.sql.PreparedStatement; // For executing parameterized SQL statements
import java.sql.ResultSet; // For storing result set from a query
import java.sql.SQLException; // Exception handling for SQL operations
import java.util.Scanner; // For reading user input from the console

public class DatabaseRestore {

    /**
     * This class provides methods to interact with a database to:
     * - Fetch employee details by ID
     * - Backup the EMPLOYEES table
     * - Restore the EMPLOYEES table from a backup
     * - Drop the EMPLOYEES table if it exists
     * - Execute shell commands for database export and import
     */

    /**
     * Fetches and prints the details of an employee from the database based on the given employee ID.
     * @param scanner A Scanner object for reading input from the console.
     */
    private static void fetchEmployeeDetails(Scanner scanner) {
        // Prompt the user to enter an employee ID
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();

        // SQL query to fetch employee details based on the given ID
        String sql = "SELECT first_name, last_name, email FROM employees WHERE employee_id = ?";

        // Declare DatabaseConnection and Connection objects for managing the database connection
        DatabaseConnection dbConn = null;
        Connection conn = null;

        try {
            // Initialize the database connection
            dbConn = new DatabaseConnection();
            conn = dbConn.getConnection();

            // Prepare and execute the SQL query using a PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set the employee ID in the prepared statement
                pstmt.setInt(1, employeeId);  // Use setInt for integer parameter

                // Execute the query and store the result in a ResultSet
                ResultSet rs = pstmt.executeQuery();

                // Check if a record is found and print the employee details
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    System.out.println("Employee Details:");
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("Email: " + email);
                } else {
                    // Print a message if no employee is found with the given ID
                    System.out.println("Employee with ID " + employeeId + " does not exist.");
                }
            } catch (SQLException e) {
                // Handle SQL exceptions during query execution
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions related to database connection or class loading
            e.printStackTrace();
        } finally {
            // Ensure the database connection is closed properly
            if (dbConn != null) {
                dbConn.closeConnection();
            }
        }
    }

    /**
     * Backs up the EMPLOYEES table using the Oracle 'exp' utility to a specified file path.
     * @param backupFilePath The file path where the backup will be stored.
     */
    static void backupEmployeesTable(String backupFilePath) {
        // Get database connection details from dynamic configuration
        String dbUsername = DatabaseConfig.getUsername();
        String dbPassword = DatabaseConfig.getPassword();
        String dbUrl = DatabaseConfig.getDatabaseUrl();

        // Print a message indicating the start of the backup process
        System.out.println("Starting backup of EMPLOYEES table to " + backupFilePath);

        // Command to export only the EMPLOYEES table using the Oracle 'exp' utility
        // Assuming you want to export the table without specifying a schema prefix
        String expCommand = String.format("exp %s/%s@%s TABLES=EMPLOYEES FILE=%s", dbUsername, dbPassword, dbUrl, backupFilePath);

        // Execute the export command
        executeCommand(expCommand);
    }

    /**
     * Restores the EMPLOYEES table from a specified backup file using the Oracle 'imp' utility.
     * @param restoreFilePath The file path from which the restore will be performed.
     */
    static void restoreEmployeesTable(String restoreFilePath) {
        // Get database connection details from dynamic configuration
        String dbUsername = DatabaseConfig.getUsername();
        String dbPassword = DatabaseConfig.getPassword();
        String dbUrl = DatabaseConfig.getDatabaseUrl();

        // Drop the existing table if it exists, to prevent conflicts during import
        dropTableIfExists();

        // Print a message indicating the start of the restoration process
        System.out.println("Starting restore of EMPLOYEES table from " + restoreFilePath);

        // Command to import only the EMPLOYEES table using the Oracle 'imp' utility
        // Adjust FROMUSER and TOUSER as needed if you want to move data between schemas
        String impCommand = String.format("imp %s/%s@%s FROMUSER=%s TOUSER=%s TABLES=EMPLOYEES FILE=%s IGNORE=Y", dbUsername, dbPassword, dbUrl, dbUsername, dbUsername, restoreFilePath);

        // Execute the import command
        executeCommand(impCommand);
    }

    /**
     * Drops the EMPLOYEES table if it exists in the database.
     */
    private static void dropTableIfExists() {
        // SQL command to drop the EMPLOYEES table
        String sql = "DROP TABLE EMPLOYEES";

        // Declare DatabaseConnection and Connection objects for managing the database connection
        DatabaseConnection dbConn = null;
        Connection conn = null;

        try {
            // Initialize the database connection
            dbConn = new DatabaseConnection();
            conn = dbConn.getConnection();

            // Prepare and execute the SQL command to drop the table
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.execute();
                // Print a message if the table is dropped successfully
                System.out.println("Table EMPLOYEES dropped successfully.");
            } catch (SQLException e) {
                // Handle SQL exceptions if the table does not exist or cannot be dropped
                System.out.println("Table EMPLOYEES does not exist or cannot be dropped.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions related to database connection or class loading
            e.printStackTrace();
        } finally {
            // Ensure the database connection is closed properly
            if (dbConn != null) {
                dbConn.closeConnection();
            }
        }
    }

    /**
     * Executes a given command in the system shell and prints its output to the console.
     * @param command The command to be executed.
     */
    private static void executeCommand(String command) {
        try {
            // Create a ProcessBuilder to execute the command in the system shell (cmd.exe for Windows)
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/c", command);
            // Redirect the error stream to the standard output stream to capture all output
            processBuilder.redirectErrorStream(true);

            // Start the process and capture the output
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                // Read and print each line of output from the command execution
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the process to complete
            process.waitFor();
            // Print a success message after the command is executed
            System.out.println("Command executed successfully.");
        } catch (IOException | InterruptedException e) {
            // Handle exceptions related to input/output operations and process interruptions
            e.printStackTrace();
        }
    }
}
