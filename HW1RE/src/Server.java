import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    // Server port number
    private static final int PORT = 1234;

    // List of quiz questions
    private static final List<String> questions = Arrays.asList(
            "What is the largest planet in our solar system?",     
            "Who wrote the play 'Romeo and Juliet'?",              
            "What is the capital city of Japan?",                
            "What is the chemical symbol for water?",           
            "How many continents are there?",                       
            "Who painted the Mona Lisa?",                            
            "What is the smallest country in the world?",                  
            "What is the square root of 64?",                              
            "Who developed the theory of relativity?",                 
            "Which planet is known as the Red Planet?",                   
            "What is the largest mammal in the world?",                         
            "How many colors are in a rainbow?",                                    
            "What is the capital of Australia?",                                 
            "In which year did the Titanic sink?",                               
            "Who is known as the father of computers?",                           
            "What is the hardest natural substance on Earth?",                    
            "How many bones are in the human body?",                               
            "What is the tallest mountain in the world?",                           
            "Who discovered penicillin?",                                          
            "What is the longest river in the world?"      
    );

    // List of correct answers for each question, with multiple acceptable answers separated by commas
    private static final List<String> answers = Arrays.asList(
            "Jupiter",
            "William Shakespeare,Shakespeare",
            "Tokyo",
            "H2O",
            "7",
            "Leonardo da Vinci,Da Vinci",
            "Vatican City",
            "8",
            "Albert Einstein,Einstein",
            "Mars",
            "Blue Whale",
            "7",
            "Canberra",
            "1912",
            "Charles Babbage,Babbage",
            "Diamond",
            "206",
            "Mount Everest,Everest",
            "Alexander Fleming,Fleming",
            "Amazon River,Amazon"
    );

    public static void main(String[] args) {
        // Start the server socket and listen on the specified port
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");

            // Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Handle each client in a separate thread
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ClientHandler class handles each client connection
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private int score = 0; // Track client's score

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            // Set up input and output streams for client communication
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                // Send each question to the client and get the response
                for (int i = 0; i < questions.size(); i++) {
                    out.println("QUESTION:" + questions.get(i)); // Send question to client
                    String response = in.readLine(); // Read client's response

                    // Check if the response is correct and provide feedback
                    if (response != null && checkAnswer(response, answers.get(i))) {
                        score++; // Increase score for a correct answer
                        out.println("FEEDBACK:Correct");
                    } else {
                        out.println("FEEDBACK:Incorrect");
                    }
                }
                // Send the final score to the client
                out.println("SCORE:" + score);
                clientSocket.close(); // Close the client connection
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Method to check if the client's answer is correct
        // Supports multiple correct answers separated by commas
        private boolean checkAnswer(String response, String correctAnswers) {
            String userAnswer = response.replace("ANSWER:", "").trim(); // Remove "ANSWER:" prefix from client response
            String[] possibleAnswers = correctAnswers.split(","); // Split multiple correct answers
            for (String answer : possibleAnswers) {
                // Compare user's answer with each possible correct answer (case insensitive)
                if (userAnswer.equalsIgnoreCase(answer.trim())) {
                    return true;
                }
            }
            return false; // Return false if no match is found
        }
    }
}
