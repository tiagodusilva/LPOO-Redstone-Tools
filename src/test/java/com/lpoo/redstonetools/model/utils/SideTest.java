package com.lpoo.redstonetools.model.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("model")
public class SideTest {
    @Test
    @Tag("unit-test") @Tag("fast")
    public void testSideOpposite() {
        Assertions.assertEquals(Side.DOWN, (Side.UP).opposite());
        Assertions.assertEquals(Side.UP, (Side.DOWN).opposite());
        Assertions.assertEquals(Side.RIGHT, (Side.LEFT).opposite());
        Assertions.assertEquals(Side.LEFT, (Side.RIGHT).opposite());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testSideAtRight() {
        Assertions.assertEquals(Side.RIGHT, (Side.UP).atRight());
        Assertions.assertEquals(Side.LEFT, (Side.DOWN).atRight());
        Assertions.assertEquals(Side.UP, (Side.LEFT).atRight());
        Assertions.assertEquals(Side.DOWN, (Side.RIGHT).atRight());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testSideAtLeft() {
        Assertions.assertEquals(Side.LEFT, (Side.UP).atLeft());
        Assertions.assertEquals(Side.RIGHT, (Side.DOWN).atLeft());
        Assertions.assertEquals(Side.DOWN, (Side.LEFT).atLeft());
        Assertions.assertEquals(Side.UP, (Side.RIGHT).atLeft());
    }
}
