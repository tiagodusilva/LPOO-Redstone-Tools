# LPOO_79
The project aimed .... **TODO**

It was developed by [Telmo Baptista](https://github.com/Telmooo) and [Tiago Silva](https://github.com/tiagodusilva) (***T Squad***).

## Table of Contents
1. [Implemented Features](#Implemented-Features)
    1. [Power](#power)
    2. [Tiles](#tiles)
        1. [Generic Tiles](#generic-tiles)
        2. [Specific Tiles](#specific-tiles)
    3. [Circuit](#circuit)
    4. [Menu](#menu)
    5. [Screenshots](#screenshots)
        1. [LanternaMenu](#lanterna-menu)
        2. [Temporary Pre-existing Circuit](temporary-pre-existing-circuit)

2. [Planned Features](#planned-features)
3. [Design](#design)
4. [Known Code Smells and Refactoring Suggestions](#known-code-smells-and-refactoring-suggestions)
5. [Testing](#testing)
6. [Self-evaluation](#self-evaluation)

## Implemented Features
The features already implemented are listed below.

### Power
Our version of the world's electricity, it is the signal propagated on the circuit to emulate real world's [digital electronics](https://en.wikipedia.org/wiki/Digital_electronics), it was inspired by Minecraft's [redstone circuits](https://minecraft.fandom.com/wiki/Redstone_Circuits).

As such, this power system has two modes:
- *Redstone Mode* - The power decays from wire to wire, until it reaches the minimum power level, power level varies between 0 and 15 (*0x0-0xF*).
- *Electric Mode* - The power doesn't decay, it simulates lossless transport, taking two possible values, *ON* or *OFF*.

### Tiles
A tile is the basic component of a circuit, having its own behaviour and functionalities.
#### Generic Tiles
- [Tile](../src/main/java/com/lpoo/redstonetools/model/tile/OrientedTile.java) - The most generic tile, stating the behaviours and functionalities every tile should have.
- [OrientedTile](../src/main/java/com/lpoo/redstonetools/model/tile/OrientedTile.java) - An upgraded version of the generic tile *Tile*, capable of having configurable input and output sides, making possible to a tile receive power from only one specific side and not every side, etc.

#### Specific Tiles
- [NullTile](../src/main/java/com/lpoo/redstonetools/model/tile/NullTile.java) - A filler tile, has no behaviour or functionality, it serves as the default tile of a circuit.
- [ConstantSourceTile](../code/src/main/java/com/lpoo/redstonetools/model/tile/ConstantSourceTile.java) - A tile that provides a constant source of power.
- [WireTile](../src/main/java/com/lpoo/redstonetools/model/tile/WireTile.java) - Main power transporting tile.
- [LeverTile](../src/main/java/com/lpoo/redstonetools/model/tile/LeverTile.java) - An alternating power source tile, the lever functions the same as the constant source but can be toggled on whether outputs power or not.
- [RepeaterTile](../src/main/java/com/lpoo/redstonetools/model/tile/RepeaterTile.java) - An extensor of power, it transform any power received into a maximum strength power signal, as long as the input power signal is higher than the minimum power.  
    This tile is an *OrientedTile* that receives power from one side, and outputs on the opposite side.
- [LogicGateTile](../src/main/java/com/lpoo/redstonetools/model/tile/LogicGateTile.java) - It is an *OrientedTile* that receives power from two opposing sides and outputs from one of the other remaining sides.  
    As a logic gate, its behaviour is dependent on the logic gate it is currently simulating, the possible behaviours are:
    - *AND* Gate - Outputs power if it receives power higher than the minimum power level from both input sides.
    - *OR* Gate - Outputs power if it receives power higher than the minimum power level in any of the input sides.
    - *NAND* Gate - Negates the *AND* gate, behaving on the opposite way of the later.
    - *NOR* Gate - Negates the *OR* gate, behaving on the opposite way of the later.
    - *XOR* Gate - Outputs power if it receives power higher than the minimum power lever in only one of the input sides.
    - *XNOR* Gate - Negates the *XOR* gate, behaving on the opposite way of the later.
- [NotGateTile](../src/main/java/com/lpoo/redstonetools/model/tile/NotGateTile.java) - It is an *OrientedTile* that receives power from one side and outputs on the opposite side, similar to the *RepeaterTile*.  
    It behaves as power inverter, outputs power level if it receives it doesn't receive an higher power level than the minimum from the input, and doesn't output if it receives any power higher than the minimum power level from the input.

### Circuit
The powerhouse of the ~~cell~~ project.  
A circuit is a composition of tiles and handles all the interactions between tiles, insertion and deletion of tiles and the passage of time (*tick*).

### Menu
The welcoming screen.  
It is via the menu that you can create a new circuit or ~~load existing circuits~~ (loading not implemeneted yet, there is a temporary dynamically created circuit if it is chosen this option).

### Screenshots
#### Lanterna Menu
![lanternamenu](./images/screenshots/lanterna/LanternaMenu.png)

#### Existing Tiles
![existingtiles](./images/screenshots/lanterna/ExistingTiles.png)  
From left to right, top to bottom:  
1. *Wire* and demonstration of all possible connections the wire can have.  
2. *Constant Source*, *Lever* (not active), *Lever* (active), *Repeater*, *NOT Gate*, *AND Gate*, *OR Gate*, *NAND Gate*, *NOR Gate*, *XOR Gate*, *XNOR Gate*.
3. Demonstration of decaying power on the wire, changing the intensity of its colour and current selected tile shown with magenta background.

#### Temporary Pre-existing Circuit
(*Note*: Temporary, as the name indicates, just for test purposes and to ease the visualization while file loading isn't implemented)
![preexistingcircuit](./images/screenshots/lanterna/PreExistingCircuit.png)

## Planned Features
- [x] Create circuits
- [ ] Load circuits
- [ ] Save circuits
- [x] CRUD methods on tiles
- [x] Support default combinational logic gates:
 - [x] AND gate
 - [x] OR gate
 - [x] NOT gate
 - [x] NAND gate
 - [x] NOR gate
 - [x] XOR gate
 - [X] XNOR gate
- [ ] Support default sequential gates:
 - [x] Repeater (signal extender)
 - [ ] Comparator (compares strength of two signals)
 - [ ] Counter (emits signal every N pulses received)
 - [ ] Timer (emits signal every N ticks)
- [ ] Support custom gates (reduce circuit into a tile)
- [ ] Support two types of circuit simulation:
 - [x] Variable strength signals (0-15), loosing strength at each wire travelled
 - [ ] Lossless two state signals

## Design

### Model-View-Controller (MVC)
#### Problem in Context
On the early stages of the project, we were trying to design the structure for the *MVC*, but we ended with what we could call the *(MC)V* as the model and the controller were on the same part. This generated a lot of trouble structuring the renderers and then joining it to the model-controller, creating a lot of unnecessary dependencies as the view could trigger changes on the model-controller, and the later would trigger changes on itself to update the model part. This is a clear violation the **Single Responsibility Principle** (SRP).

#### The Pattern
We have applied the same pattern, the architectural pattern *MVC*, but now in a decent way, where the model, view and controller are well isolated and each have its own purpose, diving it into three parts:  
- The model holds the data about the object and some functionalities and behaviours the object is responsible of.  
- The view has the function of rendering the models and receives events from user.  
- The controller provides the model to the view in order to be rendered, handles events sent by the view and manipulates the model according to those events.

#### The Implementation
As the implementation of this pattern involves multiple classes, and it's more an abstract idea it will not be provided specific classes, just the packages where each part is contained. The design implemented is show in the following figure:  
![mvcdesign](./images/designs/mvc/mvc_design.png)

The three parts of the *MVC* can be found in:
- [Model](../src/main/java/com/lpoo/redstonetools/model/)
- [View](../src/main/java/com/lpoo/redstonetools/view/)
- [Controller](../src/main/java/com/lpoo/redstonetools/controller/)

#### Consequences
By applying this pattern in a more decent way, it was easier to structure the rest of the functionalities, resulting in a more elegant structure and well defined responsibilities for each part.

## Known Code Smells and Refactoring Suggestions

## Testing

## Self-evaluation
This project was developed with maximum synergy, using [communication tools](https://discordapp.com/) to plan every feature while constantly reviewing each other code by live programming every time it was possible as well as an extra review of code by using [Github](https://github.com/)'s pull request system. Thus, it can be said each one did 100% of the work!