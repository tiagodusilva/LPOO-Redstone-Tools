# LPOO_79

## Table of Contents
1. [Implemented Features](#Implemented-Features)

# Features
- [ ] Create/load circuits
- [ ] CRUD methods on tiles
- [ ] Support default combinational logic gates:
 - [ ] AND gate
 - [ ] OR gate
 - [ ] NOT gate
 - [ ] NAND gate
 - [ ] NOR gate
 - [ ] XOR gate
 - [ ] XNOR gate
- [ ] Support default sequential gates:
 - [ ] Repeater (signal extender)
 - [ ] Comparator (compares strength of two signals)
 - [ ] Counter (emits signal every N pulses received)
 - [ ] Timer (emits signal every N ticks)
- [ ] Support custom gates (reduce circuit into a tile)
- [ ] Support two types of circuit simulation:
 - [ ] Variable strength signals (0-15), loosing strength at each wire travelled
 - [ ] Lossless two state signals

## Implemented Features
The features already implemented are listed below.

### Tiles
A tile is the basic component of a circuit, having its own behaviour and functionalities.
#### Generic Tiles
- [OrientedTile](../code/src/main/java/com/lpoo/redstonetools/model/tile/OrientedTile.java) - type of tile

#### Specific Tiles
- [NullTile](../code/src/main/java/com/lpoo/redstonetools/model/tile/NullTile.java) - A filler tile, has no behaviour or functionality, it serves as the default tile of a circuit.
- [ConstantSourceTile](../code/src/main/java/com/lpoo/redstonetools/model/tile/ConstantSourceTile.java) - A tile that provides a constant source of power.
- [WireTile](../code/src/main/java/com/lpoo/redstonetools/model/tile/WireTile.java) - Main power transporting tile.  
  The wire has two modes
- [LeverTile](../code/src/main/java/com/lpoo/redstonetools/model/tile/LeverTile.java) - a


## Design

## Known code smells and refactoring suggestions

## Testing

## Self-evaluation
