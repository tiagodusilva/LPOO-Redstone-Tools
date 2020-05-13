package com.lpoo.redstonetools.view.lanterna.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.tile.strategy.*;
import com.lpoo.redstonetools.view.lanterna.command.LanternaToggleShowPowerCommand;
import com.lpoo.redstonetools.view.lanterna.command.MoveSelectionCommand;
import com.lpoo.redstonetools.view.lanterna.command.MoveViewWindowCommand;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;

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
                            case 't':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADVANCE_TICK, null));
                                break;
                            case 'q':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ROTATE_LEFT, lanternaCircuitView.getSelectedTile().clone()));
                                break;
                            case 'e':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ROTATE_RIGHT, lanternaCircuitView.getSelectedTile().clone()));
                                break;
                            case '1':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new WireTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '2':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new ConstantSourceTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '3':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LeverTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '4':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new RepeaterTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '5':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new NotGateTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '6':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new ANDGateStrategy())));
                                break;
                            case '7':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new ORGateStrategy())));
                                break;
                            case '8':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new NANDGateStrategy())));
                                break;
                            case '9':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new NORGateStrategy())));
                                break;
                            case '0':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new XORGateStrategy())));
                                break;
                            case '\'':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new XNORGateStrategy())));
                                break;
                            case '+':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new TimerTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'p':
                                new LanternaToggleShowPowerCommand(lanternaCircuitView).execute();
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
                    case Enter:
                        lanternaCircuitView.pushEvent(new Event(InputEvent.INTERACT, lanternaCircuitView.getSelectedTile().clone()));
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
                break;
            }

        }

    }
}
