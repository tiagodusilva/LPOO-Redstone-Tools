package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Struct;

public class ComparatorTileTest {

    private ComparatorTile comparator;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.comparator = Mockito.mock(ComparatorTile.class, Mockito.withSettings().useConstructor(position).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    public void testComparator() {
        Assert.assertEquals(1, comparator.getPosition().getX());
        Assert.assertEquals(2, comparator.getPosition().getY());
        Assert.assertEquals(TileType.COMPARATOR, comparator.getType());
    }

    @Test
    public void testComparatorPower() {
        Assert.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assert.assertFalse(comparator.acceptsPower(Side.RIGHT));
        Assert.assertTrue(comparator.acceptsPower(Side.UP));
        Assert.assertTrue(comparator.acceptsPower(Side.DOWN));

        Assert.assertTrue(comparator.outputsPower(Side.RIGHT));
        Assert.assertFalse(comparator.outputsPower(Side.LEFT));
        Assert.assertFalse(comparator.outputsPower(Side.UP));
        Assert.assertFalse(comparator.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.getMin(), comparator.getPower(side));
        }

        comparator.setOutput(Power.getMax());

        for (Side side : Side.values()) {
            if (comparator.outputsPower(side)) {
                Assert.assertEquals(Power.getMax(), comparator.getPower(side));
            } else {
                Assert.assertEquals(Power.getMin(), comparator.getPower(side));
            }
        }

        comparator.setOutput(4);

        for (Side side : Side.values()) {
            if (comparator.outputsPower(side)) {
                Assert.assertEquals(4, comparator.getPower(side));
            } else {
                Assert.assertEquals(Power.getMin(), comparator.getPower(side));
            }
        }
    }

    @Test
    public void testRotate() {
        Mockito.doNothing().when(comparator).updateRear();

        Assert.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assert.assertFalse(comparator.acceptsPower(Side.RIGHT));
        Assert.assertTrue(comparator.acceptsPower(Side.UP));
        Assert.assertTrue(comparator.acceptsPower(Side.DOWN));


        Assert.assertFalse(comparator.outputsPower(Side.LEFT));
        Assert.assertTrue(comparator.outputsPower(Side.RIGHT));
        Assert.assertFalse(comparator.outputsPower(Side.UP));
        Assert.assertFalse(comparator.outputsPower(Side.DOWN));

        comparator.rotateRight();

        Assert.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assert.assertTrue(comparator.acceptsPower(Side.RIGHT));
        Assert.assertTrue(comparator.acceptsPower(Side.UP));
        Assert.assertFalse(comparator.acceptsPower(Side.DOWN));


        Assert.assertFalse(comparator.outputsPower(Side.LEFT));
        Assert.assertFalse(comparator.outputsPower(Side.RIGHT));
        Assert.assertFalse(comparator.outputsPower(Side.UP));
        Assert.assertTrue(comparator.outputsPower(Side.DOWN));

        Mockito.verify(comparator, Mockito.times(1)).updateRear();

        comparator.rotateLeft(); comparator.rotateLeft();

        Assert.assertTrue(comparator.acceptsPower(Side.LEFT));
        Assert.assertTrue(comparator.acceptsPower(Side.RIGHT));
        Assert.assertFalse(comparator.acceptsPower(Side.UP));
        Assert.assertTrue(comparator.acceptsPower(Side.DOWN));


        Assert.assertFalse(comparator.outputsPower(Side.LEFT));
        Assert.assertFalse(comparator.outputsPower(Side.RIGHT));
        Assert.assertTrue(comparator.outputsPower(Side.UP));
        Assert.assertFalse(comparator.outputsPower(Side.DOWN));


        Mockito.verify(comparator, Mockito.times(3)).updateRear();
    }

    @Test
    public void testUpdateRear() {
        for (Side side : Side.values()) {
            Mockito.when(comparator.outputsPower(side)).thenReturn(false);
        }

        comparator.updateRear();

        Mockito.verify(comparator, Mockito.times(4)).outputsPower(Mockito.any(Side.class));
        Assert.assertNull(comparator.getRear());

        Mockito.when(comparator.outputsPower(Side.LEFT)).thenReturn(true);

        comparator.updateRear();

        Assert.assertEquals(Side.RIGHT, comparator.getRear());
    }

    @Test
    public void testOnChangeRearNull() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        // make rear null
        comparator.updateRear();

        Assert.assertFalse(comparator.onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class)));

        Mockito.verify(comparator, Mockito.times(0)).setOutput(Mockito.anyInt());
    }

    @Test
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
        Assert.assertEquals(Side.LEFT, comparator.getRear());

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.RIGHT));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMax(), Side.RIGHT));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.LEFT));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.UP));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.DOWN));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 3, Side.LEFT));
        Assert.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, 2, Side.UP));
        Assert.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, 3, Side.DOWN));
        Assert.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 7, Side.DOWN));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, Power.getMax(), Side.LEFT));
        Assert.assertEquals(Power.getMax(), comparator.getPower(Side.RIGHT));
    }

    @Test
    public void testComparatorSubtractMode() {
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
        Assert.assertEquals(Side.LEFT, comparator.getRear());

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.RIGHT));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMax(), Side.RIGHT));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.LEFT));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.UP));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, Power.getMin(), Side.DOWN));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 3, Side.LEFT));
        Assert.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 2, Side.UP));
        Assert.assertEquals(1, comparator.getPower(Side.RIGHT));

        Assert.assertFalse(comparator.onChange(circuit, 1, Side.DOWN));
        Assert.assertEquals(1, comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 3, Side.DOWN));
        Assert.assertEquals(0, comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 6, Side.LEFT));
        Assert.assertEquals(3, comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, 7, Side.DOWN));
        Assert.assertEquals(Power.getMin(), comparator.getPower(Side.RIGHT));

        Assert.assertTrue(comparator.onChange(circuit, Power.getMax(), Side.LEFT));
        Assert.assertEquals(Power.getMax() - 7, comparator.getPower(Side.RIGHT));
    }

    @Test
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(comparator.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.outputsPower(Side.RIGHT)).thenReturn(true);
        comparator.updateRear();
        Assert.assertEquals(Side.LEFT, comparator.getRear());

        Mockito.when(comparator.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(comparator.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(comparator.acceptsPower(Side.UP)).thenReturn(true);

        // Test short circuit on sides
        Assert.assertFalse(comparator.update(circuit, Power.getMin(), Side.DOWN));
        Assert.assertFalse(comparator.update(circuit, Power.getMax(), Side.DOWN));

        Assert.assertFalse(comparator.update(circuit, Power.getMin(), Side.RIGHT));
        Assert.assertFalse(comparator.update(circuit, Power.getMax(), Side.RIGHT));

        Mockito.verify(comparator, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class));

        // Test short circuit on power changes
        Mockito.when(comparator.onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class))).thenCallRealMethod().thenReturn(true);

        Assert.assertFalse(comparator.update(circuit, Power.getMin(), Side.LEFT));
        Assert.assertFalse(comparator.update(circuit, Power.getMin(), Side.UP));
        Assert.assertFalse(comparator.update(circuit, Power.getMin(), Side.DOWN));
        Assert.assertFalse(comparator.update(circuit, Power.getMin(), Side.RIGHT));

        Mockito.verify(comparator, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class));

        Assert.assertTrue(comparator.update(circuit, Power.getMax(), Side.LEFT));
        Assert.assertFalse(comparator.update(circuit, Power.getMax(), Side.LEFT));

        Assert.assertTrue(comparator.update(circuit, Power.getMax() - 2, Side.LEFT));

        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax(), Side.LEFT);
        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax() - 2, Side.LEFT);

        Mockito.when(comparator.onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.eq(Side.UP))).thenCallRealMethod().thenReturn(false);

        Assert.assertFalse(comparator.update(circuit, Power.getMax(), Side.UP));
        Assert.assertFalse(comparator.update(circuit, Power.getMax(), Side.UP));
        Assert.assertFalse(comparator.update(circuit, Power.getMax()-1, Side.UP));

        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax(), Side.UP);
        Mockito.verify(comparator, Mockito.times(1)).onChange(circuit, Power.getMax() - 1, Side.UP);
    }

    @Test
    public void testInteract() {
        comparator.setSubtractMode(false);

        Assert.assertTrue(comparator.interact());
        Assert.assertTrue(comparator.getSubtractMode());

        Assert.assertTrue(comparator.interact());
        Assert.assertFalse(comparator.getSubtractMode());

        Assert.assertTrue(comparator.interact());
        Assert.assertTrue(comparator.interact());
        Assert.assertFalse(comparator.getSubtractMode());
    }
}
