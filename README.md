# WIKI
## Project Overview
This project is a cloud-based quiz game application using Java sockets to implement client-server communication. The server hosts quiz questions, while the client connects to answer them. The objective of this project is to practice network programming by designing client-server communication and implementing a custom protocol.

---

# Protocol Definition
## Protocol Explanation
The client and server use ASCII-based command and response formats for the quiz game:

- **QUESTION:**  
  - The server sends questions to the client in the format `QUESTION:<question content>`.
- **ANSWER:**  
  - The client sends the user’s answer in the format `ANSWER:<answer>`.
- **FEEDBACK:**  
  - The server provides feedback on the answer with `FEEDBACK:Correct` or `FEEDBACK:Incorrect`.
- **SCORE:**  
  - After all questions, the server sends the final score to the client in the format `SCORE:<final score>`, ending the session.

---

# Setup and Configuration
## Environment Setup
1. Java environment is required to run the server and client applications.
2. Configure the connection details (`IP address` and `port number`) in the `server_info.dat` file. If this file is missing, the default `localhost:1234` settings are used.

## How to Run
1. **Run the Server**: Compile and run `Server.java` to start the server.
2. **Run the Client**: Compile and run `Client.java` to connect to the server and start the quiz.

---

# Example Scenarios
## Quiz Game Scenario Example

1. The server waits for the client to connect.
2. Once connected, the server sends the first question in the format `QUESTION:<question content>`.
3. The client responds with the answer using `ANSWER:<answer>`.
4. The server replies with `FEEDBACK:Correct` or `FEEDBACK:Incorrect`.
5. After all questions are answered, the server sends `SCORE:<final score>` to the client and ends the session.

---

# Troubleshooting
- **Connection Issues**: Check that the `server_info.dat` file has the correct IP address and port.
- **Client Response Delays**: Ensure the network is stable and that the server is running.

---

# Sample Quiz Questions

Here are a few example questions used in the quiz game:

1. **What is the capital city of Japan?**  
   - Answer: Tokyo

2. **Who wrote the play 'Romeo and Juliet'?**  
   - Answer: William Shakespeare

3. **What is the chemical symbol for water?**  
   - Answer: H2O
