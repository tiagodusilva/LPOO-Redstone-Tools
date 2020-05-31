package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.circuit.Circuit;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.function.Consumer;

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

        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Consumer<Circuit> c = invocation.getArgument(0);
            c.accept(circuit);
            Runnable r = invocation.getArgument(1);
            r.run();
            return null;
        }).when(builder).addStartingMenu(Mockito.any(), Mockito.any(Runnable.class), Mockito.any(Runnable.class));

        this.view = new LanternaMenuView(screen, builder);

        Mockito.verify(builder, Mockito.times(1))
                .addStartingMenu(Mockito.any(), Mockito.any(Runnable.class), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testStartingMenu() {
        Assertions.assertEquals(2, view.getEvents().size());

        Assertions.assertEquals(InputEvent.ENTER_STATE, view.getEvents().peek().getInputEvent());
        Assertions.assertTrue(view.getEvents().peek().getObject() instanceof Circuit);

        view.getEvents().remove();

        Assertions.assertEquals(InputEvent.QUIT, view.getEvents().peek().getInputEvent());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuNoException() {
        view.cleanup();
        Assertions.assertTrue(view.getEvents().isEmpty());

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
        view.cleanup();

        Assertions.assertTrue(view.getEvents().isEmpty());

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
        view.cleanup();

        Assertions.assertTrue(view.getEvents().isEmpty());

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
}
