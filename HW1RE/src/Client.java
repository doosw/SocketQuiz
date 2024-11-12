import java.io.*;
import java.net.*;

public class Client {
    // Default server address and port
    private static String serverAddress = "localhost";
    private static int port = 1234;

    public static void main(String[] args) {
        loadServerInfo();  // Load server information from file

        // Establish a connection to the server and set up I/O streams
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String serverMessage;
            // Continuously read messages from the server until there are none left
            while ((serverMessage = in.readLine()) != null) {
                // If the server sends a question
                if (serverMessage.startsWith("QUESTION:")) {
                    System.out.println(serverMessage.substring(9));  // Display the question
                    System.out.print("Your answer: ");
                    String answer = userInput.readLine();  // Read user's answer
                    out.println("ANSWER:" + answer);  // Send the answer to the server
                } 
                // If the server sends feedback on the answer
                else if (serverMessage.startsWith("FEEDBACK:")) {
                    System.out.println(serverMessage.substring(9));  // Display feedback (Correct/Incorrect)
                } 
                // If the server sends the final score
                else if (serverMessage.startsWith("SCORE:")) {
                    System.out.println("Your final score: " + serverMessage.substring(6));
                    break;  // End the session after receiving the final score
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load server information from server_info.dat file
    private static void loadServerInfo() {
        File file = new File("server_info.dat");
        // Check if the file exists
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                serverAddress = reader.readLine();  // Read the server address
                port = Integer.parseInt(reader.readLine());  // Read the port number
                System.out.println("Loaded server info from file: " + serverAddress + ":" + port);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading server_info.dat. Using default settings.");  // Use default if error occurs
            }
        } else {
            System.out.println("server_info.dat not found. Using default settings.");  // Default if file not found
        }
    }
}
