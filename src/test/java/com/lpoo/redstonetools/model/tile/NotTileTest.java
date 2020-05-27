package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.mockito.Mockito;

public class NotTileTest {

    private NotGateTile notGate;

    private Position expectedNotGatePosition;
/*
    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedNotGatePosition = Mockito.mock(Position.class);
        Mockito.when(expectedNotGatePosition.getX()).thenReturn(1);
        Mockito.when(expectedNotGatePosition.getY()).thenReturn(2);

        this.notGate = new NotGateTile(position);
    }

    @Test
    public void testNotGate() {
        Assert.assertEquals(expectedNotGatePosition.getX(), notGate.getPosition().getX());
        Assert.assertEquals(expectedNotGatePosition.getY(), notGate.getPosition().getY());
        Assert.assertEquals("not_gate", notGate.getName());
        Assert.assertEquals("Outputting : false", notGate.getInfo());
        Assert.assertEquals(TileType.NOT_GATE, notGate.getType());
    }

    @Test
    public void testPower() {
        Assert.assertTrue(notGate.acceptsPower(Side.LEFT));
        Assert.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assert.assertFalse(notGate.acceptsPower(Side.UP));
        Assert.assertFalse(notGate.acceptsPower(Side.DOWN));

        Assert.assertTrue(notGate.outputsPower(Side.RIGHT));
        Assert.assertFalse(notGate.outputsPower(Side.LEFT));
        Assert.assertFalse(notGate.outputsPower(Side.UP));
        Assert.assertFalse(notGate.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.getMin(), notGate.getPower(side));
        }

        // activate not gate
        notGate.setStatus(true);

        for (Side side : Side.values()) {
            if (notGate.outputsPower(side)) {
                Assert.assertEquals(Power.getMax(), notGate.getPower(side));
            } else {
                Assert.assertEquals(Power.getMin(), notGate.getPower(side));
            }
        }
    }

    @Test
    public void testRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertTrue(notGate.acceptsPower(Side.LEFT));
        Assert.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assert.assertFalse(notGate.acceptsPower(Side.UP));
        Assert.assertFalse(notGate.acceptsPower(Side.DOWN));


        Assert.assertFalse(notGate.outputsPower(Side.LEFT));
        Assert.assertTrue(notGate.outputsPower(Side.RIGHT));
        Assert.assertFalse(notGate.outputsPower(Side.UP));
        Assert.assertFalse(notGate.outputsPower(Side.DOWN));

        notGate.rotateRight(circuit);

        Assert.assertFalse(notGate.acceptsPower(Side.LEFT));
        Assert.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assert.assertTrue(notGate.acceptsPower(Side.UP));
        Assert.assertFalse(notGate.acceptsPower(Side.DOWN));


        Assert.assertFalse(notGate.outputsPower(Side.LEFT));
        Assert.assertFalse(notGate.outputsPower(Side.RIGHT));
        Assert.assertFalse(notGate.outputsPower(Side.UP));
        Assert.assertTrue(notGate.outputsPower(Side.DOWN));

        notGate.rotateLeft(circuit); notGate.rotateLeft(circuit);

        Assert.assertFalse(notGate.acceptsPower(Side.LEFT));
        Assert.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assert.assertFalse(notGate.acceptsPower(Side.UP));
        Assert.assertTrue(notGate.acceptsPower(Side.DOWN));


        Assert.assertFalse(notGate.outputsPower(Side.LEFT));
        Assert.assertFalse(notGate.outputsPower(Side.RIGHT));
        Assert.assertTrue(notGate.outputsPower(Side.UP));
        Assert.assertFalse(notGate.outputsPower(Side.DOWN));
    }

    @Test
    public void testRepeaterOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));

        Assert.assertTrue(notGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), notGate.getPower(Side.RIGHT));

        Assert.assertFalse(notGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), notGate.getPower(Side.RIGHT));

        Assert.assertTrue(notGate.onChange(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));

        Assert.assertFalse(notGate.onChange(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));
    }
*/
}
