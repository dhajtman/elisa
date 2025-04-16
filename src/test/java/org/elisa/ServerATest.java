package org.elisa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ServerATest {
    private static ExecutorService executor;

    @BeforeAll
    static void setUp() throws IOException {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> ServerA.main(null)); // Start the server
    }

    @AfterAll
    static void tearDown() {
        executor.shutdownNow(); // Stop the server
    }

    @Test
    void testServerAResponse() throws IOException {
        try (Socket socket = new Socket("localhost", ServerA.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String line = in.readLine();
            assertFalse(line.isEmpty());
            String s = TestUtils.getFirstLineOf(ServerA.FILE_PATH);
            assertEquals(s, line);
        }
    }
}