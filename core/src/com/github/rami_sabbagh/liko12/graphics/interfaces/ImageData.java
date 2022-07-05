package com.github.rami_sabbagh.liko12.graphics.interfaces;

import com.badlogic.gdx.utils.Disposable;

/**
 * Represents the pixel data of an image, which can be modified, but not drawn into the screen.
 */
public interface ImageData extends Disposable {
    /**
     * Gets the width of the imageData.
     *
     * @return The width of the image in pixels.
     */
    int getWidth();

    /**
     * Gets the height of the imageData.
     *
     * @return The height of the image in pixels.
     */
    int getHeight();

    /**
     * Gets the color of a pixel in the imageData.
     *
     * @param x The X coordinates of the pixel.
     * @param y The Y coordinates of the pixel.
     * @return The color of the pixel.
     */
    int getPixel(int x, int y);

    /**
     * Sets the color of a pixel in the imageData.
     *
     * @param x     The X coordinates of the pixel.
     * @param y     The Y coordinates of the pixel.
     * @param color The new color of the pixel.
     */
    void setPixel(int x, int y, int color);

    /**
     * Applies a {@code PixelFunction} on all the pixels of the imageData.
     *
     * @param mapper The {@code PixelFunction} to apply on all the pixels.
     */
    void mapPixels(PixelFunction mapper);

    /**
     * Pastes the content of another imageData into this imageData.
     *
     * @param source    The source imageData, can't be null!
     * @param destX     The destination's top-left corner X coordinates to paste the image at. Defaults to 0.
     * @param destY     The destination's top-left corner Y coordinates to paste the image at. Defaults to 0.
     * @param srcX      The X coordinates of the region to paste from the source imageData. Defaults to 0.
     * @param srcY      The Y coordinates of the region to paste from the source imageData. Defaults to 0.
     * @param srcWidth  The width of the region to paste from the source imageData in pixels. Defaults to the source imageData's width.
     * @param srcHeight The height of the region to paste from the source imageData in pixels. Defaults to the source imageData's height.
     */
    void paste(ImageData source, Integer destX, Integer destY, Integer srcX, Integer srcY, Integer srcWidth, Integer srcHeight);

    /**
     * Creates a drawable Image from this ImageData.
     * The content of the created Image can be updated using {@code Image.refresh()}.
     *
     * @return The created drawable Image.
     */
    Image toImage();

    /**
     * Encodes the imageData into a PNG image, using the current active colorPalette.
     *
     * @return The encoded PNG binary data as a bytes array.
     */
    byte[] export();
}
