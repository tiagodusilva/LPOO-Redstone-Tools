package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.state.State;

public class EnterStateCommand implements Command {

    private final State state;
    private final MainController mainController;

    public EnterStateCommand(State state, MainController mainController) {
        this.state = state;
        this.mainController = mainController;
    }

    @Override
    public void execute() {
        mainController.changeState(state);
    }
}
