package com.github.rami_sabbagh.liko12.graphics.interfaces;

/**
 * A function that can be applied on a pixel.
 */
public interface PixelFunction {
    /**
     * Applies the function on the given pixel.
     *
     * @param x     The X coordinates of the pixel.
     * @param y     The Y coordinates of the pixel.
     * @param color The current color of the pixel.
     * @return The new color of the pixel.
     */
    int apply(int x, int y, int color);
}
