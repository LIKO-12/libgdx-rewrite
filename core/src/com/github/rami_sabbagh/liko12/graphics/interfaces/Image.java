package com.github.rami_sabbagh.liko12.graphics.interfaces;

import com.badlogic.gdx.utils.Disposable;

/**
 * Represents a drawable image, which can be drawn to the screen.
 */
public interface Image extends Disposable {
    /**
     * Gets the width of the image.
     *
     * @return The width of the image in pixels.
     */
    int getWidth();

    /**
     * Gets the height of the image.
     *
     * @return The height of the image in pixels.
     */
    int getHeight();

    /**
     * Draws the image on the screen.
     *
     * @param x         The X coordinates of the top-left image's corner.
     * @param y         The Y coordinates of the top-left image's corner.
     * @param rotation  The rotation of the image in radians. Defaults to 0.
     * @param scaleX    The scale of the image on the X-axis. Defaults to 1.
     * @param scaleY    The scale of the image on the Y-axis. Defaults to 1.
     * @param srcX      The X coordinates of the region to draw from the image. Defaults to 0.
     * @param srcY      The Y coordinates of the region to draw from the image. Defaults to 0.
     * @param srcWidth  The width of the region to draw from the image in pixels. Defaults to the image's width.
     * @param srcHeight The height of the region to draw from the image in pixels. Defaults to the image's height.
     */
    void draw(float x, float y, Float rotation, Float scaleX, Float scaleY, Integer srcX, Integer srcY, Integer srcWidth, Integer srcHeight);

    /**
     * Updates the image's content from the ImageData used to create the image.
     * <b>Note:</b> the image won't be refreshed if the source imageData was garbageCollected/disposed.
     */
    void refresh();

}
