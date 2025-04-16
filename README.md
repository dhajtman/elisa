## Elisa Project

### Overview
This project implements a system with two servers and one client to process and analyze text files. The servers expose text files (`frankenstein.txt` and `dracula.txt`) over sockets, and the client connects to these servers to read and count the most common words in the files.

### Task
- Two (2) servers (`ServerA` and `ServerB`) read and expose one text file each:
  - `ServerA` serves `frankenstein.txt`.
  - `ServerB` serves `dracula.txt`.
- One (1) client connects to both servers in parallel, reads the data, and counts the occurrences of words.
- The program prints the 5 most common words across both files, along with their total number of occurrences.
- The implementation ensures that very large files are handled efficiently without loading the entire file into memory.

### Features
- **Socket Communication**: Servers and the client communicate using sockets.
- **Parallel Processing**: The client reads data from both servers concurrently.
- **Resource Management**: Files are accessed from the `resources` folder using the class loader.
- **Scalability**: The solution is designed to handle large files efficiently.

### Prerequisites
- **Java 17** or higher
- **Maven** for dependency management and build

### Setup
1. **Build the project:**
   ```bash
   mvn clean install
   ```
2. **Run the servers:**
   - Start `ServerA`:
     ```bash
     java -cp target/elisa-1.0-SNAPSHOT.jar org.elisa.ServerA
     ```
   - Start `ServerB`:
     ```bash
     java -cp target/elisa-1.0-SNAPSHOT.jar org.elisa.ServerB
     ```
3. **Run the client:**
    - With default server hosts (localhost:5001 and localhost:5002)::
     ```bash
     java -cp target/elisa-1.0-SNAPSHOT.jar org.elisa.Client
     ```
   - With custom server hosts:
    ```bash
    java -cp target/elisa-1.0-SNAPSHOT.jar org.elisa.Client <host1:port1> <host2:port2>    ```
    ```
   
### Running Tests
- **Unit Tests**: Run the unit tests using Maven:
  ```bash
  mvn test
  ```
### Output
- The client will output the 5 most common words and their counts after processing both files.
- Example output:
  ```
  Most common words:
  Order: 1 -> word: the / count: 5000
  Order: 2 -> word: and / count: 3000
  Order: 3 -> word: of / count: 2500
  Order: 4 -> word: to / count: 2000
  Order: 5 -> word: a / count: 1500
  ```