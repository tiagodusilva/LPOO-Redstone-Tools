package com.lpoo.redstonetools.model.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SideTypeTest {
    @Test
    public void testSideTypeCheck() {
        Assertions.assertFalse((SideType.DEFAULT).isInput());
        Assertions.assertTrue((SideType.INPUT).isInput());
        Assertions.assertFalse((SideType.OUTPUT).isInput());

        Assertions.assertFalse((SideType.DEFAULT).isOutput());
        Assertions.assertFalse((SideType.INPUT).isOutput());
        Assertions.assertTrue((SideType.OUTPUT).isOutput());
    }

    @Test
    public void testNext() {
        SideType defaultType = SideType.DEFAULT;
        SideType inputType = SideType.INPUT;
        SideType outputType = SideType.OUTPUT;

        Assertions.assertEquals(inputType, defaultType.next());
        Assertions.assertEquals(outputType, inputType.next());
        Assertions.assertEquals(defaultType, outputType.next());

        Assertions.assertEquals(outputType, defaultType.next().next());
        Assertions.assertEquals(defaultType, inputType.next().next());
    }
}
