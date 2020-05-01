package com.lpoo.redstonetools.model.utils;

import org.junit.Assert;
import org.junit.Test;

public class SideTest {

    @Test
    public void testSideOpposite() {
        Assert.assertEquals(Side.UP, (Side.DOWN).opposite());
        Assert.assertEquals(Side.DOWN, (Side.UP).opposite());
        Assert.assertEquals(Side.LEFT, (Side.RIGHT).opposite());
        Assert.assertEquals(Side.RIGHT, (Side.LEFT).opposite());
    }

}
