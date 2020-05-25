package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.command.EnterStateCommand;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.MenuView;

import java.util.Queue;

public class MenuState extends State {

    private MenuView menuView;

    public MenuState(MainController mainController) {
        super(mainController);

        menuView = mainController.getViewFactory().getMenuView();
    }

    @Override
    public void processEvents() {
        Queue<Event> events = menuView.getEvents();
        while (!events.isEmpty()) {
            try {
                Event event = events.remove();
                switch (event.getInputEvent()) {
                    case ENTER_STATE:
                        new EnterStateCommand(new CircuitState((Circuit) event.getObject(), mainController), mainController).execute();
                        break;
                    case QUIT:
                        this.exit = true;
                        events.clear();
                        return;
                    default:
                        break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render() {
        this.menuView.render();
    }

    @Override
    public void atExit() {
        this.menuView.cleanup();
    }
}
