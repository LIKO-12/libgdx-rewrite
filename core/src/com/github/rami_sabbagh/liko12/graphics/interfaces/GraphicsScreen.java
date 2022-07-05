package com.github.rami_sabbagh.liko12.graphics.interfaces;

/**
 * Allows to get information about the screen, and take screenshots.
 */
public interface GraphicsScreen {

    /**
     * Gets the width of the screen in pixels.
     *
     * @return The width of the screen in pixels.
     */
    int getWidth();

    /**
     * Gets the height of the screen in pixels.
     *
     * @return The height of the screen in pixels.
     */
    int getHeight();

    /**
     * Takes a screenshot of the screen and returns it as an ImageData.
     *
     * @return The screenshot taken.
     */
    ImageData screenshot();

    /**
     * Waits till the screen is applied and shown to the user.
     * Helpful when doing some loading operations.
     */
    void flip();
}
