package com.lpoo.redstonetools.view.lanterna.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.view.lanterna.command.MoveSelectionCommand;
import com.lpoo.redstonetools.view.lanterna.command.MoveViewWindowCommand;
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

        while (!isInterrupted()) {

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
                            case 'i':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.INTERACT, lanternaCircuitView.getSelectedTile().clone()));
                                break;
                            case 't':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADVANCE_TICK, null));
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
                        lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new NullTile(lanternaCircuitView.getSelectedTile().clone())));
                        break;
                    case Escape:
                    case EOF:
                        lanternaCircuitView.pushEvent(new Event(InputEvent.QUIT, null));
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (RuntimeException e) {
                System.out.println("Ur mom gey");
                e.printStackTrace();
            }

        }

        System.out.println("Fun is over");
    }
}
