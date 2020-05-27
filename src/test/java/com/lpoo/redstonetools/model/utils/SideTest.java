package com.lpoo.redstonetools.model.utils;

import org.junit.Assert;
import org.junit.Test;

public class SideTest {
    @Test
    public void testSideOpposite() {
        Assert.assertEquals(Side.DOWN, (Side.UP).opposite());
        Assert.assertEquals(Side.UP, (Side.DOWN).opposite());
        Assert.assertEquals(Side.RIGHT, (Side.LEFT).opposite());
        Assert.assertEquals(Side.LEFT, (Side.RIGHT).opposite());
    }

    @Test
    public void testSideAtRight() {
        Assert.assertEquals(Side.RIGHT, (Side.UP).atRight());
        Assert.assertEquals(Side.LEFT, (Side.DOWN).atRight());
        Assert.assertEquals(Side.UP, (Side.LEFT).atRight());
        Assert.assertEquals(Side.DOWN, (Side.RIGHT).atRight());
    }

    @Test
    public void testSideAtLeft() {
        Assert.assertEquals(Side.LEFT, (Side.UP).atLeft());
        Assert.assertEquals(Side.RIGHT, (Side.DOWN).atLeft());
        Assert.assertEquals(Side.DOWN, (Side.LEFT).atLeft());
        Assert.assertEquals(Side.UP, (Side.RIGHT).atLeft());
    }
}
