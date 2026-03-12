package com.valentin.event.mgmt.be;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageUtilsTest {
    @Test void testGetMessage() {
        assertEquals("Hello      World!", MessageUtils.getMessage());
    }
}
