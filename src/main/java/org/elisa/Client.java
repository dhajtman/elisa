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
    private static final List<String> DEFAULT_SERVER_HOSTS = List.of("localhost:5001", "localhost:5002");

    public static void main(String[] args) {
        List<String> serverHosts = args.length > 0 ? Arrays.asList(args) : DEFAULT_SERVER_HOSTS;

        ExecutorService executor = Executors.newFixedThreadPool(serverHosts.size());
        for (String serverHost : serverHosts) {
            String[] parts = serverHost.split(":");
            if (parts.length != 2) {
                LOGGER.severe("Invalid server host format: " + serverHost);
                continue;
            }
            String host = parts[0];
            int port;
            try {
                port = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid port number: " + parts[1]);
                continue;
            }
            executor.execute(() -> readFromServer(host, port));
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

            LOGGER.info("Connected to server on " + host + ":" + port);
            String line;
            while ((line = in.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to server on " + host + ":" + port, e);
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