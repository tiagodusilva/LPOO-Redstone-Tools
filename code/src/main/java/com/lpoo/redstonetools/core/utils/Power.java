package com.lpoo.redstonetools.core.utils;

public class Power {
    static int mode = 0;

    static public int clamp(int value) {
        return Math.min(Math.max(value, getMin()), getMax());
    }

    static public int decrease(int value) {
        return mode == 0 ? Math.max(value - 1, getMin()) : value;
    }

    public static int getMax() {
        return mode == 0 ? 15 : 1;
    }

    public static int getMin() {
        return 0;
    }

    public static boolean isRedstoneMode() {
        return mode == 0;
    }

    public static boolean isElectricMode() {
        return mode == 1;
    }

    public static void setRedstoneMode() {
        mode = 0;
    }

    public static void setElectricMode() {
        mode = 1;
    }

}