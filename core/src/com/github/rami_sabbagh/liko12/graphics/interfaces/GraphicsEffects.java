package com.github.rami_sabbagh.liko12.graphics.interfaces;

import com.badlogic.gdx.graphics.Color;

/**
 * Allows to perform some graphics effects.
 */
public interface GraphicsEffects {

    /**
     * Sets the clipping region of the drawing operations.
     *
     * @param x      The X coordinates of the top-left corner of the clipping area.
     * @param y      The Y coordinates of the top-left corner of the clipping area.
     * @param width  The width of the clipping area.
     * @param height The height of the clipping area.
     */
    void setClip(int x, int y, int width, int height);

    /**
     * Sets the drawing operations pattern, or clears it.
     * The image would be repeated over the screen, and treated as a mask.
     * Any non-zero colored pixels would be drawn on, and any zero colored pixels would remain untouched.
     *
     * @param pattern The drawing pattern image. Defaults for no pattern.
     */
    void setDrawingPattern(Image pattern);

    /**
     * Gets the 3x3 transformation matrix affecting the drawing operations.
     *
     * @return A <a href="https://duckduckgo.com/?t=ffab&q=row-major+array&ia=web">row-major</a> 9 floats array
     * containing the values of the 3x3 matrix.
     */
    float[] getMatrix();

    /**
     * Sets the 3x3 transformation matrix affecting the drawing operations.
     *
     * @param matrix A <a href="https://duckduckgo.com/?t=ffab&q=row-major+array&ia=web">row-major</a> 9 floats array
     *               containing the values of the 3x3 matrix.
     */
    void setMatrix(float[] matrix);

    /**
     * Sets the RGB values of a palette color.
     *
     * @param color The palette's color to set.
     * @param r     The red channel value [0-255].
     * @param g     The green channel value [0-255].
     * @param b     The blue channel value [0-255].
     */
    void setPaletteColor(int color, int r, int g, int b);

    /**
     * Gets the RGB values of a palette color.
     *
     * @param color The palette's color to get.
     * @return The RGB values of the requested color.
     */
    Color getPaletteColor(int color);

    /**
     * Resets the palette color to it's initial RGB values.
     *
     * @param color The palette's color to reset.
     */
    void resetPaletteColor(int color);

    /**
     * Sets the color remapping for all drawing operations.
     *
     * @param from The color to replace.
     * @param to   The color which will replace 'from'.
     */
    void remapColor(int from, int to);

    /**
     * Makes a specific color transparent (invisible) when drawing an image.
     *
     * @param color The color to make transparent.
     */
    void makeColorTransparent(int color);

    /**
     * Makes a specific color opaque (visible) when drawing an image.
     *
     * @param color The color to make opaque.
     */
    void makeColorOpaque(int color);

}
