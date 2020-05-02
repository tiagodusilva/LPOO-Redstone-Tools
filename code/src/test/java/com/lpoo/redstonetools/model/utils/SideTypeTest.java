package com.lpoo.redstonetools.model.utils;

import org.junit.Assert;
import org.junit.Test;

public class SideTypeTest {

    @Test
    public void testSideTypeCheck() {
        Assert.assertFalse((SideType.DEFAULT).isInput());
        Assert.assertTrue((SideType.INPUT).isInput());
        Assert.assertFalse((SideType.OUTPUT).isInput());

        Assert.assertFalse((SideType.DEFAULT).isOutput());
        Assert.assertFalse((SideType.INPUT).isOutput());
        Assert.assertTrue((SideType.OUTPUT).isOutput());
    }
}
