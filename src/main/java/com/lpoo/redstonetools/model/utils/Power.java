package com.lpoo.redstonetools.model.utils;

/**
 *  <h1>Power Level</h1>
 *  Power system in which the circuits operate
 *
 * @author g79
 */
public abstract class Power {

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
        return Math.max(value - 1, getMin());
    }

    /**
     * <h1>Returns true if power level is On</h1>
     * @param value Value to be verified
     * @return True when power != getMin()
     *      False otherwise
     */
    public static boolean isOn(int value) { return !isOff(value); }

    /**
     * <h1>Returns true if power level is Off</h1>
     * @param value Value to be verified
     * @return True when power == getMin()
     *      False otherwise
     */
    public static boolean isOff(int value) { return value == getMin(); }

    /**
     *<h1>Compares if a power is On and the other is Off</h1>
     * @param a Power value 1
     * @param b Power value 2
     * @return True when a is On and b is Off, or vice-versa
     */
    public static boolean differentStates(int a, int b) {
        return (isOn(a) && isOff(b)) || (isOff(a) && isOn(b));
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
     * Maximum power level is 15 (0xF)
     *
     * @return  Maximum power level
     */
    public static int getMax() {
        return 15;
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

}
