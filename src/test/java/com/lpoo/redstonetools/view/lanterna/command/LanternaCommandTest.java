package com.lpoo.redstonetools.view.lanterna.command;

import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaCommandTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testMoveViewWindowCommand() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        for (Side side : Side.values()) {
            MoveViewWindowCommand command = new MoveViewWindowCommand(view, side);

            command.execute();
            Mockito.verify(view, Mockito.times(1)).moveViewWindow(side);
        }
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testMoveSelectionCommand() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        for (Side side : Side.values()) {
            MoveSelectionCommand command = new MoveSelectionCommand(view, side);

            command.execute();
            Mockito.verify(view, Mockito.times(1)).moveSelectedTile(side);
        }
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testToggleShowPowerCommand() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        LanternaToggleShowPowerCommand command = new LanternaToggleShowPowerCommand(view);

        command.execute();
        Mockito.verify(view, Mockito.times(1)).toggleShowPower();
    }
}
