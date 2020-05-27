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

    @Test
    public void testNext() {
        SideType defaultType = SideType.DEFAULT;
        SideType inputType = SideType.INPUT;
        SideType outputType = SideType.OUTPUT;

        Assert.assertEquals(inputType, defaultType.next());
        Assert.assertEquals(outputType, inputType.next());
        Assert.assertEquals(defaultType, outputType.next());

        Assert.assertEquals(outputType, defaultType.next().next());
        Assert.assertEquals(defaultType, inputType.next().next());
    }
}
