package org.elisa;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final Map<String, Integer> wordCount = new ConcurrentHashMap<>();
    private static final int[] SERVER_PORTS = {5001, 5002};
    private static final String SERVER_HOST = "localhost";

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(SERVER_PORTS.length);

        for (int port : SERVER_PORTS) {
            executor.execute(() -> readFromServer(SERVER_HOST, port));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        printTopWords();
    }

    private static void readFromServer(String host, int port) {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            LOGGER.info("Connected to server on port " + port);
            String line;
            while ((line = in.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to server on port " + port, e);
        }
    }

    private static void processLine(String line) {
        String[] words = line.toLowerCase().replaceAll("[^a-z\\s]", "").split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCount.merge(word, 1, Integer::sum);
            }
        }
    }

    private static void printTopWords() {
        LOGGER.info("Calculating top 5 most common words...");
        List<Map.Entry<String, Integer>> top = wordCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5).toList();

        ListIterator<Map.Entry<String, Integer>> iter = top.listIterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            LOGGER.info("Order: " + iter.nextIndex() + " ->  word: " + entry.getKey() + " / count:" + entry.getValue());
        }
    }
}