package com.github.rami_sabbagh.liko12.graphics.interfaces;

/**
 * Allows to draw shapes on the screen.
 */
public interface GraphicsPainter {

    /**
     * Gets the active color.
     *
     * @return The active color.
     */
    int getColor();

    /**
     * Sets the active color.
     *
     * @param color The active color.
     */
    void setColor(int color);

    /**
     * Clears the screen and fills it with a specific color.
     *
     * @param color The color to fill the screen with. Defaults to the active color.
     */
    void clear(Integer color);

    /**
     * Draws a point into the screen.
     *
     * @param x     The X coordinates of the point.
     * @param y     The Y coordinates of the point.
     * @param color The color of the point. Defaults to the active color.
     */
    void point(float x, float y, Integer color);

    /**
     * Draws a line into the screen.
     *
     * @param x1    The X coordinates of the line's start.
     * @param y1    The Y coordinates of the line's start.
     * @param x2    The X coordinates of the line's end.
     * @param y2    The Y coordinates of the line's end.
     * @param color The color of the line. Defaults to the active color.
     */
    void line(float x1, float y1, float x2, float y2, Integer color);

    /**
     * Draws a triangle into the screen.
     *
     * @param x1     The X coordinates of the triangle's first vertex.
     * @param y1     The Y coordinates of the triangle's first vertex
     * @param x2     The X coordinates of the triangle's second vertex.
     * @param y2     The Y coordinates of the triangle's second vertex.
     * @param x3     The X coordinates of the triangle's third vertex.
     * @param y3     The Y coordinates of the triangle's third vertex.
     * @param filled Whether to fill the triangle or only outline it. Defaults to false (outlined).
     * @param color  The color of the triangle. Defaults to the active color.
     */
    void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Boolean filled, Integer color);

    /**
     * Draws a rectangle into the screen.
     *
     * @param x      The X coordinates of the rectangle's top-left corner.
     * @param y      The Y coordinates of the rectangle's top-left corner.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param filled Whether to fill the rectangle or only outline it. Defaults to false (outlined).
     * @param color  The color of the rectangle. Defaults to the active color.
     */
    void rectangle(float x, float y, float width, float height, Boolean filled, Integer color);

    /**
     * Draws a filled polygon into the screen using the active color.
     *
     * @param vertices The vertices of the polygon, must contain at least 3 vertices, in sequence "x1, y1, x2, y2, ...".
     */
    void polygon(float... vertices);

    /**
     * Draws a circle on the screen.
     *
     * @param x      The X coordinates of the circle's center.
     * @param y      The Y coordinates of the circle's center.
     * @param radius The radius of the circle.
     * @param filled Whether to fill the circle or only outline it. Defaults to false (outlined).
     * @param color  The color of the circle. Defaults to the active color.
     */
    void circle(float x, float y, float radius, Boolean filled, Integer color);

    /**
     * Draws an ellipse on the screen.
     *
     * @param x       The X coordinates of the ellipse's center.
     * @param y       The Y coordinates of the ellipse's center.
     * @param radiusX The radius of the ellipse along the X-axis (half the ellipse's width).
     * @param radiusY The radius of the ellipse along the Y-axis (half the ellipse's height).
     * @param filled  Whether to fill the ellipse or only outline it. Defaults to false (outlined).
     * @param color   The color of the ellipse. Defaults to the active color.
     */
    void ellipse(float x, float y, float radiusX, float radiusY, Boolean filled, Integer color);
}
