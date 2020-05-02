package com.lpoo.redstonetools.model.utils;

/**
 *  <h1>Power Level</h1>
 *  Power system in which the circuits operate
 *
 * @author g79
 */
public abstract class Power {
    /**
     * <h1>Power mode</h1>
     * There are two power modes:
     *      - Redstone mode: power level decays over travel distance
     *      - Eletric mode: power level stays constant over the whole travelled distance
     */
    static int mode = 0;

    /**
     * <h1>Guarantees power level is valid</h1>
     * Guarantees power level is between the maximum power level defined and the minimum power level defined (inclusive)
     *
     * @param value Value of power level to be clamped
     * @return  Clamped power level value
     */
    public static int clamp(int value) {
        return Math.min(Math.max(value, getMin()), getMax());
    }

    /**
     * <h1>Decreases power level</h1>
     * On Redstone mode power level will be decrease by one unit until it reaches minimum power level
     * On Eletric mode power level is lossless, so the value isn't decreased maintaining equal
     *
     * @param value Value of power level to be decreased
     * @return  New power level
     */
    public static int decrease(int value) {
        return mode == 0 ? Math.max(value - 1, getMin()) : value;
    }

    /**
     * <h1>Normalizes power level</h1>
     * Transforms power level into a number between 0 and 1 (inclusive) as long as the value passed is within the power levels admitted
     *
     * @param value Value of power level to be normalized
     * @return  Normalized power level
     */
    public static double normalize(int value) {
        return value / (double) getMax();
    }

    /**
     * <h1>Get maximum power level</h1>
     * On Redstone mode maximum power level is 15 (0xF)
     * On Eletric mode maximum power level is 1
     *
     * @return  Maximum power level
     */
    public static int getMax() {
        return mode == 0 ? 15 : 1;
    }

    /**
     * <h1>Get minimum power level</h1>
     * Minimum power level is 0
     *
     * @return Minimum power level
     */
    public static int getMin() {
        return 0;
    }

    /**
     * <h1>Check if it is on Redstone mode</h1>
     *
     * @return  true if on Redstone mode, false otherwise
     */
    public static boolean isRedstoneMode() {
        return mode == 0;
    }

    /**
     * <h1>Check if it is on Eletric mode</h1>
     *
     * @return  true if on Eletric mode, false otherwise
     */
    public static boolean isEletricMode() {
        return mode == 1;
    }

    /**
     * <h1>Set to Redstone mode</h1>
     *
     * @see Power#mode
     */
    public static void setRedstoneMode() {
        mode = 0;
    }

    /**
     * <h1>Set to Eletric mode</h1>
     *
     * @see Power#mode
     */
    public static void setEletricMode() {
        mode = 1;
    }

}
