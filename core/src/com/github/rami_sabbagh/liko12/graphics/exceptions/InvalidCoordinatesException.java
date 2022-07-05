package com.github.rami_sabbagh.liko12.graphics.exceptions;

public class InvalidCoordinatesException extends RuntimeException {
    public InvalidCoordinatesException(int value, int size, boolean yCoordinates) {
        super((yCoordinates ? "Y" : "X") + " coordinates (" + value + ") are out of range [0, " + (size - 1) + "].");
    }
}
