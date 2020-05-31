package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class IOTileTest {

    private IOTile io;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.io = new IOTile(position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testIO() {
        Assertions.assertEquals(1, io.getPosition().getX());
        Assertions.assertEquals(2, io.getPosition().getY());
        Assertions.assertEquals(TileType.IO, io.getType());
        Assertions.assertFalse(io.isWire());
        Assertions.assertFalse(io.isTickedTile());
        Assertions.assertEquals(SideType.DEFAULT, io.getIOType());
        Assertions.assertEquals(Side.UP, io.getIOSide());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        Circuit circuit = Mockito.mock(Circuit.class);

        io.setIOType(SideType.DEFAULT);

        for (Side side : Side.values()) {
            Assertions.assertTrue(io.onChange(circuit, 12, side));
            Assertions.assertFalse(io.acceptsPower(side));
            Assertions.assertFalse(io.outputsPower(side));
            Assertions.assertEquals(Power.getMin(), io.getPower(side));
            Assertions.assertEquals(Power.getMin(), io.getExteriorPower(side));
        }

        io.setIOType(SideType.INPUT);

        for (Side side : Side.values()) {
            Assertions.assertTrue(io.onChange(circuit, 5, side));
            Assertions.assertTrue(io.acceptsPower(side));
            Assertions.assertFalse(io.outputsPower(side));
            Assertions.assertEquals(Power.getMin(), io.getPower(side));
            Assertions.assertEquals(5, io.getExteriorPower(side));
        }

        io.setIOType(SideType.OUTPUT);

        for (Side side : Side.values()) {
            Assertions.assertTrue(io.onChange(circuit, 8, side));
            Assertions.assertFalse(io.acceptsPower(side));
            Assertions.assertTrue(io.outputsPower(side));
            Assertions.assertEquals(8, io.getPower(side));
            Assertions.assertEquals(Power.getMin(), io.getExteriorPower(side));
        }
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSide(@ForAll int power, @ForAll Side side) {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.getSurroundingPower(Mockito.any(Position.class))).thenReturn(power);

        IOTile ioSpy = Mockito.spy(io);

        ioSpy.setIOType(SideType.DEFAULT);
        Assertions.assertFalse(ioSpy.update(circuit, power, side));

        ioSpy.setIOType(SideType.OUTPUT);
        Assertions.assertFalse(ioSpy.update(circuit, power, side));

        Mockito.verify(ioSpy, Mockito.times(0))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        io.setIOType(SideType.INPUT);
        Assertions.assertTrue(io.getIOType().isInput());

        Assertions.assertEquals(Power.getMin(), io.getPower(Side.UP));
        Assertions.assertEquals(Power.getMin(), io.getExteriorPower(Side.UP));

        Mockito.when(circuit.getSurroundingPower(Mockito.any(Position.class))).thenReturn(2);

        Assertions.assertTrue(io.update(circuit, 2, Side.UP));

        Assertions.assertEquals(Power.getMin(), io.getPower(Side.UP));
        Assertions.assertEquals(2, io.getExteriorPower(Side.UP));

        Assertions.assertFalse(io.update(circuit, Power.getMax(), Side.UP));

        Assertions.assertEquals(Power.getMin(), io.getPower(Side.RIGHT));
        Assertions.assertEquals(2, io.getExteriorPower(Side.UP));

        Mockito.when(circuit.getSurroundingPower(Mockito.any(Position.class))).thenReturn(7);

        Assertions.assertTrue(io.update(circuit, 11, Side.UP));

        Assertions.assertEquals(Power.getMin(), io.getPower(Side.LEFT));
        Assertions.assertEquals(7, io.getExteriorPower(Side.UP));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testInteract() {
        Circuit circuit = Mockito.mock(Circuit.class);

        io.setIOType(SideType.DEFAULT);

        Mockito.when(circuit.updateOnIOInteract(Mockito.any(Position.class))).thenReturn(false);

        Assertions.assertFalse(io.interact(circuit));
        Assertions.assertEquals(SideType.DEFAULT, io.getIOType());

        Mockito.verify(circuit, Mockito.times(2)).updateOnIOInteract(Mockito.any(Position.class));

        Mockito.when(circuit.updateOnIOInteract(Mockito.any(Position.class))).thenReturn(true);

        SideType expected = SideType.DEFAULT.next();

        Assertions.assertTrue(io.interact(circuit));
        Assertions.assertEquals(expected, io.getIOType());

        Mockito.verify(circuit, Mockito.times(3)).updateOnIOInteract(Mockito.any(Position.class));

        Assertions.assertTrue(io.interact(circuit));
        Assertions.assertEquals(expected.next(), io.getIOType());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRotate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        io.setIOSide(Side.UP);

        Mockito.when(circuit.updateOnIORotation(Mockito.any(Position.class), Mockito.any(Side.class))).thenReturn(false);

        Assertions.assertFalse(io.rotateLeft(circuit));
        Assertions.assertEquals(Side.UP, io.getIOSide());

        Mockito.verify(circuit, Mockito.times(3)).updateOnIORotation(Mockito.any(Position.class), Mockito.any(Side.class));
        Mockito.verify(circuit, Mockito.times(3)).updateOnIORotation(Mockito.any(Position.class), Mockito.eq(Side.UP));

        Assertions.assertFalse(io.rotateRight(circuit));
        Assertions.assertEquals(Side.UP, io.getIOSide());

        Mockito.verify(circuit, Mockito.times(6)).updateOnIORotation(Mockito.any(Position.class), Mockito.any(Side.class));
        Mockito.verify(circuit, Mockito.times(6)).updateOnIORotation(Mockito.any(Position.class), Mockito.eq(Side.UP));

        Mockito.when(circuit.updateOnIORotation(Mockito.any(Position.class), Mockito.any(Side.class))).thenReturn(true);

        Assertions.assertTrue(io.rotateLeft(circuit));
        Assertions.assertEquals(Side.LEFT, io.getIOSide());

        Mockito.verify(circuit, Mockito.times(7)).updateOnIORotation(Mockito.any(Position.class), Mockito.any(Side.class));
        Mockito.verify(circuit, Mockito.times(7)).updateOnIORotation(Mockito.any(Position.class), Mockito.eq(Side.UP));

        Assertions.assertTrue(io.rotateLeft(circuit));
        Assertions.assertEquals(Side.DOWN, io.getIOSide());

        Mockito.verify(circuit, Mockito.times(8)).updateOnIORotation(Mockito.any(Position.class), Mockito.any(Side.class));
        Mockito.verify(circuit, Mockito.times(1)).updateOnIORotation(Mockito.any(Position.class), Mockito.eq(Side.LEFT));

        Assertions.assertTrue(io.rotateRight(circuit));
        Assertions.assertTrue(io.rotateRight(circuit));
        Assertions.assertTrue(io.rotateRight(circuit));
        Assertions.assertEquals(Side.RIGHT, io.getIOSide());
    }
}
