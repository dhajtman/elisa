package org.elisa;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerA {
    private static final Logger LOGGER = Logger.getLogger(ServerA.class.getName());
    private static final int PORT = 5001;
    private static final String FILE_PATH = "/frankenstein.txt";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("ServerA is running on port " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                             ServerA.class.getResourceAsStream(FILE_PATH)));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    LOGGER.info("Client connected to ServerA");
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        out.println(line);
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error handling client connection", e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error starting ServerA", e);
        }
    }
}