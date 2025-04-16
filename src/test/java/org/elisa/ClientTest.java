package org.elisa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientTest {

    @Test
    void testClientInitialization() {
        Client client = new Client();
        assertNotNull(client, "Client should be initialized");
    }
}