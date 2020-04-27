package com.lpoo.redstonetools.view.lanterna.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.command.MoveSelectionCommand;
import com.lpoo.redstonetools.controller.command.MoveViewWindowCommand;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.circuit.LanternaCircuitView;

import java.io.IOException;

public class LanternaInput extends Thread {

    Screen screen;
    LanternaCircuitView lanternaCircuitView;

    public LanternaInput(LanternaCircuitView lanternaCircuitView) {
        this.lanternaCircuitView = lanternaCircuitView;
    }

    @Override
    public void run() {
        super.run();

        while (true) {

            KeyStroke key = null;
            try {
                key = lanternaCircuitView.getScreen().readInput();
                switch (key.getKeyType()) {
                    case Character:
                        switch (key.getCharacter()) {
                            case 'w':
                                new MoveSelectionCommand(lanternaCircuitView, Side.UP).execute();
                                break;
                            case 'a':
                                new MoveSelectionCommand(lanternaCircuitView, Side.LEFT).execute();
                                break;
                            case 's':
                                new MoveSelectionCommand(lanternaCircuitView, Side.DOWN).execute();
                                break;
                            case 'd':
                                new MoveSelectionCommand(lanternaCircuitView, Side.RIGHT).execute();
                                break;
                            default:
                                break;
                        }
                        break;
                    case ArrowUp:
                        new MoveViewWindowCommand(lanternaCircuitView, Side.DOWN).execute();
                        break;
                    case ArrowLeft:
                        new MoveViewWindowCommand(lanternaCircuitView, Side.RIGHT).execute();
                        break;
                    case ArrowDown:
                        new MoveViewWindowCommand(lanternaCircuitView, Side.UP).execute();
                        break;
                    case ArrowRight:
                        new MoveViewWindowCommand(lanternaCircuitView, Side.LEFT).execute();
                        break;
                    case Insert:
                        break;
                    case Delete:
                        break;
                    case Escape:
                        break;
                    case EOF:
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (isInterrupted()) break;
        }
    }
}
