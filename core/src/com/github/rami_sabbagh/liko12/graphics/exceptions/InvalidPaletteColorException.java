package com.github.rami_sabbagh.liko12.graphics.exceptions;

public class InvalidPaletteColorException extends RuntimeException {
    public InvalidPaletteColorException(int value) {
        super("Invalid color value (" + value + "). Should be in range [0, 255].");
    }
}
