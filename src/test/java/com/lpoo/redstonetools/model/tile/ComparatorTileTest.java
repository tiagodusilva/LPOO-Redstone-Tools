package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ComparatorTileTest {

    private ComparatorTile comparator;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.comparator = Mockito.mock(ComparatorTile.class, Mockito.withSettings().useConstructor(position).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Assertions.assertEquals(Side.LEFT, comparator.getRear());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testComparator() {
        Assertions.assertEquals(1, comparator.getPosition().getX());
        Assertions.assertEquals(2, comparator.getPosition().getY());
        Assertions.assertEquals(TileType.COMPARATOR, comparator.getType());
        Assertions.assertFalse(comparator.isTickedTile());
        Assertions.assertFalse(comparator.isWire());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testComparatorPower() {
        Assertions.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assertions.assertFalse(comparator.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(comparator.acceptsPower(Side.UP));
        Assertions.assertTrue(comparator.acceptsPower(Side.DOWN));

        Assertions.assertTrue(comparator.outputsPower(Side.RIGHT));
        Assertions.assertFalse(comparator.outputsPower(Side.LEFT));
        Assertions.assertFalse(comparator.outputsPower(Side.UP));
        Assertions.assertFalse(comparator.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), comparator.getPower(side));
        }

        comparator.setOutput(Power.getMax());

        for (Side side : Side.values()) {
            if (comparator.outputsPower(side)) {
                Assertions.assertEquals(Power.getMax(), comparator.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), comparator.getPower(side));
            }
        }

        comparator.setOutput(4);

        for (Side side : Side.values()) {
            if (comparator.outputsPower(side)) {
                Assertions.assertEquals(4, comparator.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), comparator.getPower(side));
            }
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRotate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.doNothing().when(comparator).updateRear();

        Assertions.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assertions.assertFalse(comparator.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(comparator.acceptsPower(Side.UP));
        Assertions.assertTrue(comparator.acceptsPower(Side.DOWN));


        Assertions.assertFalse(comparator.outputsPower(Side.LEFT));
        Assertions.assertTrue(comparator.outputsPower(Side.RIGHT));
        Assertions.assertFalse(comparator.outputsPower(Side.UP));
        Assertions.assertFalse(comparator.outputsPower(Side.DOWN));

        Assertions.assertTrue(comparator.rotateRight(circuit));

        Assertions.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assertions.assertTrue(comparator.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(comparator.acceptsPower(Side.UP));
        Assertions.assertFalse(comparator.acceptsPower(Side.DOWN));


        Assertions.assertFalse(comparator.outputsPower(Side.LEFT));
        Assertions.assertFalse(comparator.outputsPower(Side.RIGHT));
        Assertions.assertFalse(comparator.outputsPower(Side.UP));
        Assertions.assertTrue(comparator.outputsPower(Side.DOWN));

        Mockito.verify(comparator, Mockito.times(1)).updateRear();

        Assertions.assertTrue(comparator.rotateLeft(circuit));
        Assertions.assertTrue(comparator.rotateLeft(circuit));

        Assertions.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assertions.assertTrue(comparator.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(comparator.acceptsPower(Side.UP));
        Assertions.assertTrue(comparator.acceptsPower(Side.DOWN));


        Assertions.assertFalse(comparator.outputsPower(Side.LEFT));
        Assertions.assertFalse(comparator.outputsPower(Side.RIGHT));
        Assertions.assertTrue(comparator.outputsPower(Side.UP));
        Assertions.assertFalse(comparator.outputsPower(Side.DOWN));


        Mockito.verify(comparator, Mockito.times(3)).updateRear();
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateRear() {
        for (Side side : Side.values()) {
            Mockito.when(comparator.outputsPower(side)).thenReturn(false);
        }

        comparator.updateRear();

        Mockito.verify(comparator, Mockito.times(4)).outputsPower(Mockito.any(Side.class));
        Assertions.assertNull(comparator.getRear());

        Mockito.when(comparator.outputsPower(Side.LEFT)).thenReturn(true);

        comparator.updateRear();

        Assertions.assertEquals(Side.RIGHT, comparator.getRear());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnChangeRearNull() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        // make rear null
        comparator.updateRear();

        Assertions.assertFalse(comparator.onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class)));

        Mockito.verify(comparator, Mockito.times(0)).setOutput(Mockito.anyInt());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnChangeComparisonMode() {
        comparator.setSubtractMode(false);
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.outputsPower(Side.RIGHT)).thenReturn(true);

        Mockito.when(comparator.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.DOWN)).thenReturn(true);

        // rear is left
        comparator.updateRear();
        Assertions.assertEquals(Side.LEFT, comparator.getRear());

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMax(), Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.LEFT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.UP));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.DOWN));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 3, Side.LEFT));
        Assertions.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, 2, Side.UP));
        Assertions.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, 3, Side.DOWN));
        Assertions.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 7, Side.DOWN));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(Power.getMax(), comparator.getPower(Side.RIGHT));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnChangeSubtractMode() {
        comparator.setSubtractMode(true);
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.outputsPower(Side.RIGHT)).thenReturn(true);

        Mockito.when(comparator.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.DOWN)).thenReturn(true);

        // rear is left
        comparator.updateRear();
        Assertions.assertEquals(Side.LEFT, comparator.getRear());

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMax(), Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.LEFT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.UP));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.DOWN));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 3, Side.LEFT));
        Assertions.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 2, Side.UP));
        Assertions.assertEquals(1, comparator.getPower(Side.RIGHT));

        Assertions.assertFalse(comparator.onChange(circuit, 1, Side.DOWN));
        Assertions.assertEquals(1, comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 3, Side.DOWN));
        Assertions.assertEquals(0, comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 6, Side.LEFT));
        Assertions.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, 7, Side.DOWN));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(Power.getMax() - 7, comparator.getPower(Side.RIGHT));
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSide(@ForAll int power) {
        Circuit circuit = Mockito.mock(Circuit.class);

        ComparatorTile comparatorSpy = Mockito.spy(comparator);

        Assertions.assertFalse(comparatorSpy.update(circuit, power, Side.RIGHT));

        Mockito.verify(comparatorSpy, Mockito.times(0))
                .onChange(Mockito.eq(circuit), Mockito.eq(power), Mockito.any(Side.class));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.outputsPower(Side.RIGHT)).thenReturn(true);
        comparator.updateRear();
        Assertions.assertEquals(Side.LEFT, comparator.getRear());

        Mockito.when(comparator.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.UP)).thenReturn(true);

        // Test short circuit on power changes
        Mockito.when(comparator.onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class))).thenCallRealMethod().thenReturn(true);

        Assertions.assertFalse(comparator.update(circuit, Power.getMin(), Side.LEFT));
        Assertions.assertFalse(comparator.update(circuit, Power.getMin(), Side.UP));
        Assertions.assertFalse(comparator.update(circuit, Power.getMin(), Side.DOWN));
        Assertions.assertFalse(comparator.update(circuit, Power.getMin(), Side.RIGHT));

        Mockito.verify(comparator, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class));

        Assertions.assertTrue(comparator.update(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertFalse(comparator.update(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertTrue(comparator.update(circuit, Power.getMax() - 2, Side.LEFT));

        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax(), Side.LEFT);
        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax() - 2, Side.LEFT);

        Mockito.when(comparator.onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.eq(Side.UP))).thenCallRealMethod().thenReturn(false);

        Assertions.assertFalse(comparator.update(circuit, Power.getMax(), Side.UP));
        Assertions.assertFalse(comparator.update(circuit, Power.getMax(), Side.UP));
        Assertions.assertFalse(comparator.update(circuit, Power.getMax()-1, Side.UP));

        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax(), Side.UP);
        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax() - 1, Side.UP);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testInteract() {
        Circuit circuit = Mockito.mock(Circuit.class);

        comparator.setSubtractMode(false);

        Assertions.assertTrue(comparator.interact(circuit));
        Assertions.assertTrue(comparator.getSubtractMode());

        Assertions.assertTrue(comparator.interact(circuit));
        Assertions.assertFalse(comparator.getSubtractMode());

        Assertions.assertTrue(comparator.interact(circuit));
        Assertions.assertTrue(comparator.interact(circuit));
        Assertions.assertFalse(comparator.getSubtractMode());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateOnModeChange() {
        comparator.setSubtractMode(false);
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.outputsPower(Side.RIGHT)).thenReturn(true);
        comparator.updateRear();
        Assertions.assertEquals(Side.LEFT, comparator.getRear());

        Mockito.when(comparator.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.UP)).thenReturn(true);

        Assertions.assertTrue(comparator.update(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertFalse(comparator.update(circuit, Power.getMax(), Side.UP));

        Assertions.assertEquals(Power.getMax(), comparator.getPower(Side.RIGHT));

        Assertions.assertTrue(comparator.interact(circuit));

        Assertions.assertTrue(comparator.getSubtractMode());

        Assertions.assertTrue(comparator.update(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));
    }
}
