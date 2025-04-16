package org.elisa;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerA {
    private static final Logger LOGGER = Logger.getLogger(ServerA.class.getName());
    public static final int PORT = 5001;
    public static final String FILE_PATH = "/frankenstein.txt";

    public static void main(String[] args) {
        if (ServerA.class.getResource(FILE_PATH) == null) {
            LOGGER.severe("File not found: " + FILE_PATH);
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("ServerA is running on port " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                             ServerA.class.getResourceAsStream(FILE_PATH)));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    LOGGER.info("Client connected to ServerA from " + clientSocket.getInetAddress());
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        out.println(line);
                    }
                    LOGGER.info("Finished sending file to client");
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error handling client connection", e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error starting ServerA", e);
        }
    }
}