package com.lpoo.redstonetools.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ExceptionTest {

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testInvalidCircuitException() {
        InvalidCircuitException exception = new InvalidCircuitException("testing circuit message");

        Assertions.assertEquals("testing circuit message", exception.getMessage());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testInvalidConfigException() {
        InvalidConfigException exception = new InvalidConfigException("testing config message");

        Assertions.assertEquals("testing config message", exception.getMessage());
    }
}
