package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.view.MenuView;

import java.io.IOException;

public class LanternaMenuView extends MenuView {

    private final Screen screen;
    private final WindowBasedTextGUI textGUI;

    public LanternaMenuView(Screen screen, LanternaMenuBuilder lanternaMenuBuilder) {
        this.screen = screen;
        textGUI = lanternaMenuBuilder.getTextGUI();

        lanternaMenuBuilder.addStartingMenu(
                (circuit) -> pushEvent(new Event(InputEvent.ENTER_STATE, circuit)),
                () -> pushEvent(new Event(InputEvent.QUIT, null)),
                () -> {}
        );
    }

    @Override
    public void render() {
        screen.doResizeIfNecessary();
        try {
            textGUI.processInput();
            textGUI.updateScreen();
        } catch (IOException e) {
            this.pushEvent(new Event(InputEvent.QUIT, null));
//            e.printStackTrace();
        }
    }

    @Override
    public void cleanup() {
        this.events.clear();
    }
}
