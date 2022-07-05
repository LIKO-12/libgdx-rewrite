package com.github.rami_sabbagh.liko12.graphics.exceptions;

public class InvalidColorException extends RuntimeException {
    public InvalidColorException(int color, int maxColors) {
        super("Invalid color (" + color + "). Value should be in range [0," + (maxColors - 1) + "].");
    }
}
