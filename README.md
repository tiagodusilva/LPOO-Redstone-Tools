# RedstoneTools
![Build](https://github.com/FEUP-LPOO/lpoo-2020-g79/workflows/Java%20Gradle%20Build/badge.svg?branch=tests)

## Index
- [Changelog](#changelog)
- [Description](#description)
- [Extensive Report](#extensive-report)
- [Screenshots](#screenshots)
- [Install Instructions](#install-instructions)
- [RedstoneTools Controls](#redstonetools-controls)

## Changelog
[Go to Changelog](./CHANGELOG.md)

## Description
> The project aimed to create an emulation of Minecraft's vanilla [redstone circuits](https://minecraft.fandom.com/wiki/Redstone_Circuits) with additional mechanics from prolific Minecraft Mods, such as [ProjectRed](https://github.com/MrTJP/ProjectRed), [RFTools](https://github.com/McJtyMods/RFTools), [Minecraft Circuit Mod](https://github.com/bubble-07/MinecraftCircuitsMod) and [Super Circuit Maker](https://github.com/amadornes/SuperCircuitMaker), providing an interface to manipulate and simulate circuits.

> Developed by [Telmo Baptista](https://github.com/Telmooo) and [Tiago Silva](https://github.com/tiagodusilva).

## Extensive Report
[Go to Report](./docs/README.md)

## Screenshots
Lanterna Welcome Menu  
![Lanterna Menu Screenshot](./docs/images/screenshots/lanterna/LanternaMenu.png)

#### Existing Tiles
![Existing Tiles Screenshot](./docs/images/screenshots/lanterna/ExistingTiles.png)  
> From left to right, top to bottom:  
1. *Wire* and demonstration of all possible connections the wire can have.  
2. *Constant Source*, *Lever* (not active), *Lever* (active), *Repeater*, *NOT Gate*, *AND Gate*, *OR Gate*, *NAND Gate*, *NOR Gate*, *XOR Gate*, *XNOR Gate*.
3. Demonstration of decaying power on the wire, changing the intensity of its colour and current selected tile shown with magenta background.

#### Example Circuit
![Example Circuit Screenshot](./docs/images/screenshots/lanterna/PreExistingCircuit.png)

## Install Instructions
To install the game it is just needed to download the source code, and compile it and run it using [Gradle](https://gradle.org/) or similar. Make sure you have Java installed and configured. Before running, ensure your system has the [Consolas](https://docs.microsoft.com/en-us/typography/font-list/consolas) font.

## RedstoneTools Controls
While in the menu, you can use the directional arrows to travel around the menu.

While in the circuit, the following controls are available (PT Keyboard Layout):
- `ESC` - exits the program
- `Delete` - remove tile from circuit
- `ArrowUp` - move circuit up
- `ArrowLeft` - move circuit left
- `ArrowDown` - move circuit down
- `ArrowRight` - move circuit right
- `ENTER` - interact with selected tile
- `W` - move selected tile up
- `A` - move selected tile left
- `S` - move selected tile down
- `D` - move selected tile right
- `1` - insert wire
- `2` - insert constant source
- `3` - insert lever
- `4` - insert repeater
- `5` - insert NOT gate
- `6` - insert AND gate
- `7` - insert OR gate
- `8` - insert NAND gate
- `9` - insert NOR gate
- `0` - insert XOR gate
- `'` - insert XNOR gate
- `Q` - rotate tile left
- `E` - rotate tile right
- `P` - show/hide power level values
- `T` - advance circuit tick
