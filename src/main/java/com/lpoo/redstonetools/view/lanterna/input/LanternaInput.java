package com.lpoo.redstonetools.view.lanterna.input;

import com.googlecode.lanterna.input.KeyStroke;
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

    LanternaCircuitView lanternaCircuitView;

    public LanternaInput(LanternaCircuitView lanternaCircuitView) {
        this.lanternaCircuitView = lanternaCircuitView;
    }

    @Override
    public void run() {
        super.run();

        boolean moveView = false;

        while (!isInterrupted()) {

            if (lanternaCircuitView.inMenu()) {
                Thread.yield();
                continue;
            }

            KeyStroke key = null;
            try {
                key = lanternaCircuitView.getScreen().readInput();
                switch (key.getKeyType()) {
                    case Character:
                        switch (key.getCharacter()) {
                            case 'z':
                                moveView = !moveView;
                                break;
                            case '+':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADVANCE_TICK, null));
                                break;
                            case 'q':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ROTATE_LEFT, lanternaCircuitView.getSelectedTile().clone()));
                                break;
                            case 'e':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ROTATE_RIGHT, lanternaCircuitView.getSelectedTile().clone()));
                                break;
                            case 'w':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new WireTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'x':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new CrossWireTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 's':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new ConstantSourceTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'l':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LeverTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'r':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new RepeaterTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '1':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new NotGateTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case '2':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new ANDGateStrategy())));
                                break;
                            case '3':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new ORGateStrategy())));
                                break;
                            case '4':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new NANDGateStrategy())));
                                break;
                            case '5':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new NORGateStrategy())));
                                break;
                            case '6':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new XORGateStrategy())));
                                break;
                            case '7':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(lanternaCircuitView.getSelectedTile().clone(), new XNORGateStrategy())));
                                break;
                            case 'c':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new ComparatorTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'n':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new CounterTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 't':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new TimerTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'i':
                                lanternaCircuitView.pushEvent(new Event(InputEvent.ADD_TILE, new IOTile(lanternaCircuitView.getSelectedTile().clone())));
                                break;
                            case 'p':
                                new LanternaToggleShowPowerCommand(lanternaCircuitView).execute();
                                break;
                            case 'g':
                                lanternaCircuitView.showSaveCircuitMenu();
                                break;
                            case 'o':
                                lanternaCircuitView.showInsertCustomMenu(lanternaCircuitView.getSelectedTile().clone());
                                break;
                            case 'h':
                                lanternaCircuitView.showHelpMenu();
                                break;
                            case 'f':
                                lanternaCircuitView.showTileInfo(lanternaCircuitView.getSelectedTile().clone());
                                break;
                            case 'd':
                                lanternaCircuitView.showSetDelayMenu(lanternaCircuitView.getSelectedTile().clone());
                                break;
                            default:
                                break;
                        }
                        break;
                    case ArrowUp:
                        if (moveView)
                            new MoveViewWindowCommand(lanternaCircuitView, Side.DOWN).execute();
                        else
                            new MoveSelectionCommand(lanternaCircuitView, Side.UP).execute();
                        break;
                    case ArrowLeft:
                        if (moveView)
                            new MoveViewWindowCommand(lanternaCircuitView, Side.RIGHT).execute();
                        else
                            new MoveSelectionCommand(lanternaCircuitView, Side.LEFT).execute();
                        break;
                    case ArrowDown:
                        if (moveView)
                            new MoveViewWindowCommand(lanternaCircuitView, Side.UP).execute();
                        else
                            new MoveSelectionCommand(lanternaCircuitView, Side.DOWN).execute();
                        break;
                    case ArrowRight:
                        if (moveView)
                            new MoveViewWindowCommand(lanternaCircuitView, Side.LEFT).execute();
                        else
                            new MoveSelectionCommand(lanternaCircuitView, Side.RIGHT).execute();
                        break;
                    case Enter:
                        lanternaCircuitView.pushEvent(new Event(InputEvent.INTERACT, lanternaCircuitView.getSelectedTile().clone()));
                        break;
                    case Insert:
                        lanternaCircuitView.showInsertMenu(lanternaCircuitView.getSelectedTile().clone());
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
