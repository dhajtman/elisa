package org.elisa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ServerBTest {
    private static ExecutorService executor;

    @BeforeAll
    static void setUp() throws IOException {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> ServerB.main(null)); // Start the server
    }

    @AfterAll
    static void tearDown() {
        executor.shutdownNow(); // Stop the server
    }

    @Test
    void testServerBResponse() throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", ServerB.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String line = in.readLine();
            assertFalse(line.isEmpty());
            // Check if the first line matches the expected content
            String s = TestUtils.getFirstLineOf(ServerB.FILE_PATH);
            assertEquals(s, line);
        }
    }
}