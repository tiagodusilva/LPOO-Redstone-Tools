package com.lpoo.redstonetools.model.utils;

public enum SideType {
    DEFAULT, INPUT, OUTPUT;

    public boolean isInput() {
        return this == SideType.INPUT;
    }

    public boolean isOutput() {
        return this == SideType.OUTPUT;
    }
}
