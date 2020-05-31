package com.lpoo.redstonetools.controller.circuit;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.RepeaterTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.TimerTile;
import com.lpoo.redstonetools.model.tile.WireTile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CircuitControllerTest {

    private CircuitController controller;

    @BeforeEach
    public void setup() {
        this.controller = Mockito.mock(CircuitController.class, Mockito.withSettings().useConstructor());
    }

    @Test
    @Tag("controller")
    @Tag("model") @Tag("integration-test") @Tag("slow")
    public void testSaveLoadCircuit() {
        File testDir;
        Random rng = new Random();

        do {
            testDir = new File(Integer.toString(rng.nextInt()));
        } while(testDir.exists());

        testDir.mkdir();

        Circuit circuit = new Circuit(20, 20);

        circuit.addTile(new TimerTile(new Position(1, 0)));
        circuit.addTile(new WireTile(new Position(2, 0)));
        circuit.addTile(new RepeaterTile(new Position(3, 0)));

        File testFile = new File(testDir, "test.ser");

        testDir.deleteOnExit();
        testFile.deleteOnExit();

        try {
            Assertions.assertTrue(CircuitController.saveCircuit(circuit, testFile.getPath()));

            Assertions.assertTrue(testFile.exists());

            Circuit loaded = CircuitController.loadCircuit(testFile.getPath());

            Assertions.assertNotNull(loaded);

            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    Assertions.assertEquals(circuit.getTile(x, y).getType(), loaded.getTile(x, y).getType());
                    Assertions.assertEquals(circuit.getTile(x, y).getInfo(), loaded.getTile(x, y).getInfo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Methods should've not thrown any exception");
        }
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testGetNewController() {
        Mockito.when(controller.getNewController()).thenCallRealMethod();

        Assertions.assertTrue(controller.getNewController() instanceof CircuitController);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testAddTileUnsuccessful() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getPosition()).thenReturn(position);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller)
                .addTile(Mockito.any(Circuit.class), Mockito.any(Tile.class));

        // When tile isn't added
        Mockito.when(circuit.addTile(tile)).thenReturn(false);

        controller.addTile(circuit, tile);
        Mockito.verify(circuit, Mockito.times(1)).addTile(tile);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);
        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testAddTileSuccessful() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).addTile(Mockito.any(Circuit.class), Mockito.any(Tile.class));

        // When tile is added
        Mockito.when(circuit.addTile(tile)).thenReturn(true);

        controller.addTile(circuit, tile);
        Mockito.verify(circuit, Mockito.times(1)).addTile(tile);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);
        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);

        Tile subCircuit = Mockito.mock(Tile.class);
        Mockito.when(subCircuit.getPosition()).thenReturn(position);
        Mockito.when(subCircuit.getType()).thenReturn(TileType.CIRCUIT);

        Mockito.when(circuit.addTile(subCircuit)).thenReturn(true);

        controller.addTile(circuit, subCircuit);
        Mockito.verify(circuit, Mockito.times(1)).addTile(subCircuit);
        Mockito.verify(subCircuit, Mockito.times(1)).update(circuit);
        Mockito.verify(controller, Mockito.times(2)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(1)).updateSubCircuit(circuit, subCircuit);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testInteractNoTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);

        // Interaction doesn't trigger update
        Mockito.when(tile.interact(circuit)).thenReturn(false);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).interact(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.interact(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).interact(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, position);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testInteractTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);
        // Interaction triggers update
        Mockito.when(tile.interact(circuit)).thenReturn(true);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller)
                .interact(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.interact(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).interact(circuit);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);
        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);

        Tile subCircuit = Mockito.mock(Tile.class);
        Mockito.when(circuit.getTile(position)).thenReturn(subCircuit);
        Mockito.when(subCircuit.getType()).thenReturn(TileType.CIRCUIT);

        Mockito.when(subCircuit.interact(circuit)).thenReturn(true);

        controller.interact(circuit, position);
        Mockito.verify(circuit, Mockito.times(2)).getTile(position);
        Mockito.verify(subCircuit, Mockito.times(1)).interact(circuit);
        Mockito.verify(subCircuit, Mockito.times(1)).update(circuit);
        Mockito.verify(controller, Mockito.times(2)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(1)).updateSubCircuit(circuit, subCircuit);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testAdvanceTick() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile1 = Mockito.mock(Tile.class);
        Tile tile2 = Mockito.mock(Tile.class);
        Circuit tile3 = Mockito.mock(Circuit.class);
        Position position1 = Mockito.mock(Position.class);
        Position position2 = Mockito.mock(Position.class);
        Position position3 = Mockito.mock(Position.class);

        CircuitController subController = Mockito.mock(CircuitController.class);
        Mockito.when(controller.getNewController()).thenReturn(subController);

        Mockito.when(tile1.getType()).thenReturn(TileType.WIRE);
        Mockito.when(tile2.getType()).thenReturn(TileType.CIRCUIT);
        Mockito.when(tile3.getType()).thenReturn(TileType.CIRCUIT);

        Set<Position> tickedTiles = new HashSet<>();
        tickedTiles.add(position1); tickedTiles.add(position2); tickedTiles.add(position3);

        Mockito.when(circuit.getTickedTiles()).thenReturn(tickedTiles);
        Mockito.when(circuit.getTile(position1)).thenReturn(tile1);
        Mockito.when(circuit.getTile(position2)).thenReturn(tile2);
        Mockito.when(circuit.getTile(position3)).thenReturn(tile3);

        // tile 1 triggers update on next tick
        Mockito.when(tile1.nextTick()).thenReturn(true);
        // tile 2 doesn't trigger update on next tick
        Mockito.when(tile2.nextTick()).thenReturn(false);
        // tile 3 triggers update on next tick
        Mockito.when(tile3.nextTick()).thenReturn(true);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).advanceTick(Mockito.any(Circuit.class));

        controller.advanceTick(circuit);

        Mockito.verify(circuit, Mockito.times(1)).nextTick();
        Mockito.verify(circuit, Mockito.times(1)).getTickedTiles();
        Mockito.verify(circuit, Mockito.times(1)).getTile(position1);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position2);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position3);

        Mockito.verify(tile1, Mockito.times(1)).nextTick();
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position1);

        Mockito.verify(tile2, Mockito.times(1)).nextTick();
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, position2);

        Mockito.verify(tile3, Mockito.times(1)).nextTick();
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position3);

        Mockito.verify(subController, Mockito.times(1)).advanceTick(tile3);
        Mockito.verify(subController, Mockito.times(1)).advanceTick(Mockito.any(Circuit.class));
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateAllNeighbourTiles() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.outputsPower(Side.UP)).thenReturn(true);
        Mockito.when(tile.outputsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.RIGHT)).thenReturn(true);

        for (Side side : Side.values()) {
            Mockito.when(tile.getPower(side)).thenReturn(Power.getMin());
        }

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller)
                .updateAllNeighbourTiles(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.updateAllNeighbourTiles(circuit, position);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.UP);
        Mockito.verify(controller, Mockito.times(1)).updateNeighbourTile(circuit, position, Power.getMin(), Side.UP);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.LEFT);
        Mockito.verify(controller, Mockito.times(0)).updateNeighbourTile(circuit, position, Power.getMin(), Side.LEFT);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.DOWN);
        Mockito.verify(controller, Mockito.times(0)).updateNeighbourTile(circuit, position, Power.getMin(), Side.DOWN);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.RIGHT);
        Mockito.verify(controller, Mockito.times(1)).updateNeighbourTile(circuit, position, Power.getMin(), Side.RIGHT);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateNeighbourTile() {
        Circuit circuit = Mockito.mock(Circuit.class);

        CircuitController subController = Mockito.mock(CircuitController.class);
        Mockito.when(controller.getNewController()).thenReturn(subController);

        Position toUpdate = Mockito.mock(Position.class);

        Position outOfBoundsPos = Mockito.mock(Position.class);

        Position position = Mockito.mock(Position.class);
        Circuit tile = Mockito.mock(Circuit.class);

        Mockito.when(toUpdate.getNeighbour(Mockito.any(Side.class))).thenReturn(outOfBoundsPos);
        Mockito.when(toUpdate.getNeighbour(Side.LEFT)).thenReturn(position);

        Mockito.when(circuit.isInBounds(outOfBoundsPos)).thenReturn(false);
        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Mockito.doCallRealMethod().when(controller)
                .updateNeighbourTile(Mockito.any(Circuit.class), Mockito.any(Position.class), Mockito.anyInt(), Mockito.any(Side.class));

        // Test short circuit on out of bounds
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.UP);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.DOWN);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.RIGHT);

        Mockito.verify(circuit, Mockito.times(3)).isInBounds(outOfBoundsPos);
        Mockito.verify(circuit, Mockito.times(0)).getTile(Mockito.any(Position.class));
        Mockito.verify(tile, Mockito.times(0)).getType();
        Mockito.verify(tile, Mockito.times(0)).isWire();
        Mockito.verify(tile, Mockito.times(0))
                .update(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));
        Mockito.verify(controller, Mockito.times(0))
                .updateAllNeighbourTiles(Mockito.any(Circuit.class), Mockito.any(Position.class));
        Mockito.verify(subController, Mockito.times(0))
                .forceUpdateIO(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));


        // Test short circuit on empty tile
        Mockito.when(tile.getType()).thenReturn(TileType.NULL);

        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.LEFT);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).getType();
        Mockito.verify(tile, Mockito.times(0)).isWire();
        Mockito.verify(tile, Mockito.times(0))
                .update(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));
        Mockito.verify(controller, Mockito.times(0))
                .updateAllNeighbourTiles(Mockito.any(Circuit.class), Mockito.any(Position.class));
        Mockito.verify(subController, Mockito.times(0))
                .forceUpdateIO(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        // Test update on wire tile
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);
        Mockito.when(tile.isWire()).thenReturn(true);

            // on update not trigger
        Mockito.when(tile.update(circuit, 13, Side.RIGHT)).thenReturn(false);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.LEFT);

        Mockito.verify(tile, Mockito.times(1)).isWire();
        Mockito.verify(tile, Mockito.times(1)).update(circuit, 13, Side.RIGHT);
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, position);
        Mockito.verify(subController, Mockito.times(0)).forceUpdateIO(tile, 13, Side.RIGHT);

            // on update trigger
        Mockito.when(tile.update(circuit, 13, Side.RIGHT)).thenReturn(true);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.LEFT);

        Mockito.verify(tile, Mockito.times(2)).isWire();
        Mockito.verify(tile, Mockito.times(2)).update(circuit, 13, Side.RIGHT);
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position);
        Mockito.verify(subController, Mockito.times(0)).forceUpdateIO(tile, 13, Side.RIGHT);

        // Test update on non-wire and non-circuit tile
        Mockito.when(tile.getType()).thenReturn(TileType.TIMER);
        Mockito.when(tile.isWire()).thenReturn(false);

            // on update not trigger
        Mockito.when(tile.update(circuit, 13, Side.RIGHT)).thenReturn(false);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.LEFT);

        Mockito.verify(tile, Mockito.times(3)).isWire();
        Mockito.verify(tile, Mockito.times(3)).update(circuit, 13, Side.RIGHT);
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position);
        Mockito.verify(subController, Mockito.times(0)).forceUpdateIO(tile, 13, Side.RIGHT);

            // on update trigger
        Mockito.when(tile.update(circuit, 13, Side.RIGHT)).thenReturn(true);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.LEFT);

        Mockito.verify(tile, Mockito.times(4)).isWire();
        Mockito.verify(tile, Mockito.times(4)).update(circuit, 13, Side.RIGHT);
        Mockito.verify(controller, Mockito.times(2)).updateAllNeighbourTiles(circuit, position);
        Mockito.verify(subController, Mockito.times(0)).forceUpdateIO(tile, 13, Side.RIGHT);

        // Test update on circuit tile (trigger updates on subcircuit)
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);
        Mockito.when(tile.isWire()).thenReturn(false);

        Mockito.when(tile.update(circuit, 13, Side.RIGHT)).thenReturn(true);
        controller.updateNeighbourTile(circuit, toUpdate, 13, Side.LEFT);

        Mockito.verify(tile, Mockito.times(5)).isWire();
        Mockito.verify(tile, Mockito.times(5)).update(circuit, 13, Side.RIGHT);
        Mockito.verify(controller, Mockito.times(3)).updateAllNeighbourTiles(circuit, position);
        Mockito.verify(subController, Mockito.times(1)).forceUpdateIO(tile, 13, Side.RIGHT);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testForceUpdateIO() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Position position = Mockito.mock(Position.class);

        Tile io = Mockito.mock(Tile.class);
        Mockito.when(io.getPosition()).thenReturn(position);
        Mockito.when(io.getType()).thenReturn(TileType.IO);

        Tile notIO = Mockito.mock(Tile.class);
        Mockito.when(io.getPosition()).thenReturn(position);
        Mockito.when(io.getType()).thenReturn(TileType.IO);

        Mockito.when(circuit.getIO(Side.LEFT)).thenReturn(io);
        Mockito.when(circuit.getIO(Side.RIGHT)).thenReturn(notIO);
        Mockito.when(circuit.getIO(Side.UP)).thenReturn(notIO);
        Mockito.when(circuit.getIO(Side.DOWN)).thenReturn(notIO);

        Mockito.doCallRealMethod().when(controller)
                .forceUpdateIO(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        controller.forceUpdateIO(circuit, 13, Side.RIGHT);
        controller.forceUpdateIO(circuit, Power.getMin(), Side.UP);
        controller.forceUpdateIO(circuit, Power.getMax(), Side.DOWN);

        Mockito.verify(circuit, Mockito.times(0)).getIO(Side.LEFT);
        Mockito.verify(circuit, Mockito.times(1)).getIO(Side.RIGHT);
        Mockito.verify(circuit, Mockito.times(1)).getIO(Side.UP);
        Mockito.verify(circuit, Mockito.times(1)).getIO(Side.DOWN);

        Mockito.verify(notIO, Mockito.times(3)).getType();
        Mockito.verify(notIO, Mockito.times(0))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, position);

        controller.forceUpdateIO(circuit, 5, Side.LEFT);

        Mockito.verify(circuit, Mockito.times(1)).getIO(Side.LEFT);
        Mockito.verify(notIO, Mockito.times(3)).getType();
        Mockito.verify(notIO, Mockito.times(0))
                .onChange(circuit, 5, Side.LEFT);
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position);


    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateSubCircuit() {
        Mockito.doCallRealMethod().when(controller).updateSubCircuit(Mockito.any(Circuit.class), Mockito.any(Tile.class));

        Circuit circuit = Mockito.mock(Circuit.class);

        Tile neighbour = Mockito.mock(Tile.class);
        Mockito.when(neighbour.getPower(Side.LEFT)).thenReturn(5);
        Mockito.when(neighbour.getPower(Side.RIGHT)).thenReturn(9);
        Mockito.when(neighbour.getPower(Side.UP)).thenReturn(Power.getMin());
        Mockito.when(neighbour.getPower(Side.DOWN)).thenReturn(Power.getMax());

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(neighbour);

        Position position = Mockito.mock(Position.class);
        Position left = Mockito.mock(Position.class);
        Position right = Mockito.mock(Position.class);
        Position up = Mockito.mock(Position.class);
        Position down = Mockito.mock(Position.class);
        Mockito.when(position.getNeighbour(Side.LEFT)).thenReturn(left);
        Mockito.when(position.getNeighbour(Side.RIGHT)).thenReturn(right);
        Mockito.when(position.getNeighbour(Side.UP)).thenReturn(up);
        Mockito.when(position.getNeighbour(Side.DOWN)).thenReturn(down);

        Circuit subCircuit = Mockito.mock(Circuit.class);
        Mockito.when(subCircuit.getPosition()).thenReturn(position);
        Mockito.when(subCircuit.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(subCircuit.acceptsPower(Side.RIGHT)).thenReturn(false);
        Mockito.when(subCircuit.acceptsPower(Side.UP)).thenReturn(false);
        Mockito.when(subCircuit.acceptsPower(Side.DOWN)).thenReturn(true);

        CircuitController subController = Mockito.mock(CircuitController.class);

        Mockito.when(controller.getNewController()).thenReturn(subController);

        // Test update non non circuit tile
        Mockito.when(subCircuit.getType()).thenReturn(TileType.COMPARATOR);
        controller.updateSubCircuit(circuit, subCircuit);

        Mockito.verify(controller, Mockito.times(0)).getNewController();
        Mockito.verify(subCircuit, Mockito.times(0)).getPosition();
        Mockito.verify(subCircuit, Mockito.times(0)).acceptsPower(Mockito.any(Side.class));
        Mockito.verify(position, Mockito.times(0)).getNeighbour(Mockito.any(Side.class));
        Mockito.verify(circuit, Mockito.times(0)).getTile(Mockito.any(Position.class));
        Mockito.verify(neighbour, Mockito.times(0)).getPower(Mockito.any(Side.class));
        Mockito.verify(subController, Mockito.times(0))
                .forceUpdateIO(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        // Test update on circuit tile
        Mockito.when(subCircuit.getType()).thenReturn(TileType.CIRCUIT);
        controller.updateSubCircuit(circuit, subCircuit);

        Mockito.verify(controller, Mockito.times(1)).getNewController();

        Mockito.verify(subCircuit, Mockito.times(1)).getPosition();

        // Left side
        Mockito.verify(subCircuit, Mockito.times(1)).acceptsPower(Side.LEFT);
        Mockito.verify(position, Mockito.times(1)).getNeighbour(Side.LEFT);
        Mockito.verify(circuit, Mockito.times(1)).getTile(left);
        Mockito.verify(neighbour, Mockito.times(1)).getPower(Side.RIGHT);
        Mockito.verify(subController, Mockito.times(1))
                .forceUpdateIO(subCircuit, 9, Side.LEFT);

        // Right side
        Mockito.verify(subCircuit, Mockito.times(1)).acceptsPower(Side.RIGHT);
        Mockito.verify(position, Mockito.times(0)).getNeighbour(Side.RIGHT);
        Mockito.verify(circuit, Mockito.times(0)).getTile(right);
        Mockito.verify(neighbour, Mockito.times(0)).getPower(Side.LEFT);
        Mockito.verify(subController, Mockito.times(0))
                .forceUpdateIO(subCircuit, 5, Side.RIGHT);

        // Up side
        Mockito.verify(subCircuit, Mockito.times(1)).acceptsPower(Side.UP);
        Mockito.verify(position, Mockito.times(0)).getNeighbour(Side.UP);
        Mockito.verify(circuit, Mockito.times(0)).getTile(up);
        Mockito.verify(neighbour, Mockito.times(0)).getPower(Side.DOWN);
        Mockito.verify(subController, Mockito.times(0))
                .forceUpdateIO(subCircuit, Power.getMax(), Side.UP);

        // Down side
        Mockito.verify(subCircuit, Mockito.times(1)).acceptsPower(Side.DOWN);
        Mockito.verify(position, Mockito.times(1)).getNeighbour(Side.DOWN);
        Mockito.verify(circuit, Mockito.times(1)).getTile(down);
        Mockito.verify(neighbour, Mockito.times(1)).getPower(Side.UP);
        Mockito.verify(subController, Mockito.times(1))
                .forceUpdateIO(subCircuit, Power.getMin(), Side.DOWN);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testNotifyNeighbourTiles() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Position neighbour = Mockito.mock(Position.class);
        Tile neighbourTile = Mockito.mock(Tile.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);
        for (Side side : Side.values()) {
            Mockito.when(position.getNeighbour(side)).thenReturn(neighbour);

            Mockito.when(tile.getPower(side)).thenReturn(Power.getMin());
            Mockito.when(neighbourTile.getPower(side)).thenReturn(Power.getMax());
        }

        Mockito.when(circuit.getTile(neighbour)).thenReturn(neighbourTile);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller)
                .notifyNeighbourTiles(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.notifyNeighbourTiles(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);

        for (Side side : Side.values()) {
            Mockito.verify(position, Mockito.times(1)).getNeighbour(side);
            Mockito.verify(controller, Mockito.times(1)).updateNeighbourTile(circuit, position, Power.getMin(), side);
            Mockito.verify(controller, Mockito.times(0)).updateNeighbourTile(circuit, position, Power.getMax(), side);
        }

        Mockito.verify(circuit, Mockito.times(4)).getTile(neighbour);
        Mockito.verify(neighbourTile, Mockito.times(4)).updateConnections(circuit);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void rotateTileLeftOutOfBounds() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(false);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.rotateLeft(circuit)).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);

        Mockito.doCallRealMethod().when(controller).rotateTileLeft(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(0)).getTile(position);
        Mockito.verify(tile, Mockito.times(0)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void rotateTileLeftNoTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);
        // rotation doesn't trigger updates
        Mockito.when(tile.rotateLeft(circuit)).thenReturn(false);

        Mockito.doCallRealMethod().when(controller).rotateTileLeft(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void rotateTileLeftTriggersUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // rotation triggers updates
        Mockito.when(tile.rotateLeft(circuit)).thenReturn(true);

        Mockito.doCallRealMethod().when(controller).rotateTileLeft(Mockito.any(Circuit.class), Mockito.any(Position.class));

        // update on non circuit tile
        Mockito.when(tile.getType()).thenReturn(TileType.COMPARATOR);

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(1)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);

        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);

        // update on circuit tile
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(2)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(2)).getTile(position);
        Mockito.verify(tile, Mockito.times(2)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(2)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(2)).update(circuit);

        Mockito.verify(controller, Mockito.times(2)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(1)).updateSubCircuit(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void rotateTileRightOutOfBounds() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(false);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.rotateRight(circuit)).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);

        Mockito.doCallRealMethod().when(controller).rotateTileRight(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(0)).getTile(position);
        Mockito.verify(tile, Mockito.times(0)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void rotateTileRightNoTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);
        // rotation doesn't trigger updates
        Mockito.when(tile.rotateRight(circuit)).thenReturn(false);

        Mockito.doCallRealMethod().when(controller).rotateTileRight(Mockito.any(Circuit.class), Mockito.any(Position.class));

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void rotateTileRightTriggersUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // rotation triggers updates
        Mockito.when(tile.rotateRight(circuit)).thenReturn(true);

        Mockito.doCallRealMethod().when(controller).rotateTileRight(Mockito.any(Circuit.class), Mockito.any(Position.class));

        // on non circuit tile
        Mockito.when(tile.getType()).thenReturn(TileType.COMPARATOR);

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(1)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);

        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);

        // update on circuit tile
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(2)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(2)).getTile(position);
        Mockito.verify(tile, Mockito.times(2)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(2)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(2)).update(circuit);

        Mockito.verify(controller, Mockito.times(2)).notifyNeighbourTiles(circuit, position);
        Mockito.verify(controller, Mockito.times(1)).updateSubCircuit(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testSetDelay() {
        Mockito.doCallRealMethod().when(controller)
                .setDelay(Mockito.any(Circuit.class), Mockito.any(Position.class), Mockito.anyLong());

        Circuit circuit = Mockito.mock(Circuit.class);

        Position position = Mockito.mock(Position.class);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // on out of bounds
        Mockito.when(circuit.isInBounds(position)).thenReturn(false);
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);

        controller.setDelay(circuit, position, 5);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(0)).getTile(position);
        Mockito.verify(tile, Mockito.times(0)).setDelay(Mockito.anyInt());
        Mockito.verify(tile, Mockito.times(0)).update(circuit);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);

        // on non circuit tile
        Mockito.when(circuit.isInBounds(position)).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.COMPARATOR);

        controller.setDelay(circuit, position, 2);

        Mockito.verify(circuit, Mockito.times(2)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).setDelay(2);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);
        Mockito.verify(controller, Mockito.times(0)).updateSubCircuit(circuit, tile);
        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);

        // on circuit tile
        Mockito.when(circuit.isInBounds(position)).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.CIRCUIT);

        controller.setDelay(circuit, position, 9);

        Mockito.verify(circuit, Mockito.times(3)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(2)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).setDelay(9);
        Mockito.verify(tile, Mockito.times(2)).update(circuit);
        Mockito.verify(controller, Mockito.times(1)).updateSubCircuit(circuit, tile);
        Mockito.verify(controller, Mockito.times(2)).notifyNeighbourTiles(circuit, position);

    }
}
