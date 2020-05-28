package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Tag("model") @net.jqwik.api.Tag("model")
public class LeverTileTest {

    private LeverTile lever;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.lever = new LeverTile(position);
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testLever() {
        Assertions.assertEquals(1, lever.getPosition().getX());
        Assertions.assertEquals(2, lever.getPosition().getY());
        Assertions.assertEquals(TileType.LEVER, lever.getType());
        Assertions.assertFalse(lever.isWire());
        Assertions.assertFalse(lever.isTickedTile());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testLeverInteract() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertFalse(lever.isActivated());
        Assertions.assertTrue(lever.interact(circuit));
        Assertions.assertTrue(lever.isActivated());
        Assertions.assertTrue(lever.interact(circuit));
        Assertions.assertFalse(lever.isActivated());
    }

    @Provide
    Arbitrary<Integer> evenPositiveInt() {
        return Arbitraries.integers().between(0, 5000).filter(n -> n % 2 == 0);
    }

    @Property
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testInteractEvenTimes(@ForAll("evenPositiveInt") int interacts) {
        Circuit circuit = Mockito.mock(Circuit.class);

        boolean previous_status = lever.isActivated();

        for (int i = 0; i < interacts; i++) {
            Assertions.assertTrue(lever.interact(circuit));
        }

        Assertions.assertEquals(previous_status, lever.isActivated());
    }

    @Provide
    Arbitrary<Integer> oddPositiveInt() {
        return Arbitraries.integers().between(0, 5000).filter(n -> n % 2 != 0);
    }

    @Property
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testInteractOddTimes(@ForAll("oddPositiveInt") @Positive int interacts) {
        Circuit circuit = Mockito.mock(Circuit.class);

        boolean previous_status = lever.isActivated();

        for (int i = 0; i < interacts; i++) {
            Assertions.assertTrue(lever.interact(circuit));
        }

        Assertions.assertNotEquals(previous_status, lever.isActivated());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testLeverPower() {
        Circuit circuit = Mockito.mock(Circuit.class);

        for (Side side : Side.values()) {
            Assertions.assertFalse(lever.acceptsPower(side));
            Assertions.assertTrue(lever.outputsPower(side));
            Assertions.assertEquals(Power.getMin(), lever.getPower(side));
        }

        lever.interact(circuit);

        for (Side side : Side.values()) {
            Assertions.assertFalse(lever.acceptsPower(side));
            Assertions.assertTrue(lever.outputsPower(side));
            Assertions.assertEquals(Power.getMax(), lever.getPower(side));
        }
    }
}
