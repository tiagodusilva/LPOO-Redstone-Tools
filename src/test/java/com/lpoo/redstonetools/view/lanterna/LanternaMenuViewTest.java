package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;

public class LanternaMenuViewTest {

    private Screen screen;
    private MultiWindowTextGUI textGUI;
    private LanternaMenuBuilder builder;

    private LanternaMenuView view;

    @BeforeEach
    public void setup() {
        this.screen = Mockito.mock(Screen.class);

        TerminalSize terminalSize = Mockito.mock(TerminalSize.class);

        Mockito.when(screen.getTerminalSize()).thenReturn(terminalSize);
        Mockito.when(terminalSize.getRows()).thenReturn(20);
        Mockito.when(terminalSize.getColumns()).thenReturn(30);

        this.textGUI = Mockito.mock(MultiWindowTextGUI.class);
        this.builder = Mockito.mock(LanternaMenuBuilder.class);
        Mockito.when(builder.getTextGUI()).thenReturn(this.textGUI);


        this.view = new LanternaMenuView(screen, builder);
        Assertions.assertTrue(view.getEvents().isEmpty());
        Mockito.verify(builder, Mockito.times(1))
                .addStartingMenu(Mockito.any(), Mockito.any(Runnable.class), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuNoException() {

        Assumptions.assumeTrue(view.getEvents().isEmpty());

        view.render();

        Assertions.assertTrue(view.getEvents().isEmpty());

        try {
            Mockito.verify(textGUI, Mockito.times(1)).processInput();
            Mockito.verify(textGUI, Mockito.times(1)).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(screen, Mockito.times(1)).doResizeIfNecessary();
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuOnExceptionProcessInput() {

        Assumptions.assumeTrue(view.getEvents().isEmpty());

        IOException exception = Mockito.mock(IOException.class);

        try {
            Mockito.when(textGUI.processInput()).thenThrow(exception);
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.render();

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.QUIT, view.getEvents().peek().getInputEvent());

        try {
            Mockito.verify(textGUI, Mockito.times(1)).processInput();
            Mockito.verify(textGUI, Mockito.times(0)).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(screen, Mockito.times(1)).doResizeIfNecessary();
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuOnExceptionUpdateScreen() {

        Assumptions.assumeTrue(view.getEvents().isEmpty());

        IOException exception = Mockito.mock(IOException.class);

        try {
            Mockito.doThrow(exception).when(textGUI).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.render();

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.QUIT, view.getEvents().peek().getInputEvent());

        try {
            Mockito.verify(textGUI, Mockito.times(1)).processInput();
            Mockito.verify(textGUI, Mockito.times(1)).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(screen, Mockito.times(1)).doResizeIfNecessary();
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testCleanup() {
        Assumptions.assumeTrue(view.getEvents().isEmpty());

        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);
        Event e3 = Mockito.mock(Event.class);
        Event e4 = Mockito.mock(Event.class);

        view.getEvents().add(e1);
        view.getEvents().add(e2);
        view.getEvents().add(e3);
        view.getEvents().add(e4);

        Assertions.assertEquals(4, view.getEvents().size());

        view.cleanup();

        Assertions.assertTrue(view.getEvents().isEmpty());
    }

}
