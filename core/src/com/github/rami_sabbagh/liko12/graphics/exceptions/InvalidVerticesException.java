package com.github.rami_sabbagh.liko12.graphics.exceptions;

public class InvalidVerticesException extends RuntimeException {
    public InvalidVerticesException(float[] vertices) {
        super(formatMessage(vertices));
    }

    private static String formatMessage(float[] vertices) {
        if (vertices.length % 2 == 1) {
            return "The Y coordinates of the #" + (vertices.length / 2 + 1) + " vertice is missing.";
        } else {
            return "There should be at least 3 vertices.";
        }
    }
}
