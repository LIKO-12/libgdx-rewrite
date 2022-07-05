package com.github.rami_sabbagh.liko12.graphics.implementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Disposable;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGB888;
import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;
import static com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest;

public class GdxFrameBuffer implements Disposable {

    /**
     * The OpenGL framebuffer of the LIKO-12 screen.
     */
    public final FrameBuffer frameBuffer;
    /**
     * The orthographic camera applied on the LIKO-12 drawing operations.
     */
    public final OrthographicCamera camera;

    /**
     * A 1x1 #FFFFFFFF texture.
     */
    public final Texture drawerTexture;
    /**
     * A Polygon sprite batch for the shapes and images drawing operations.
     */
    public final PolygonSpriteBatch batch;

    /**
     * The shapes drawer for LIKO-12 drawing operations.
     */
    public final ShapeDrawer drawer;

    /**
     * The drawing operations offsets.
     */
    public final GdxOffsets offsets;

    /**
     * The transformation matrix of LIKO-12.
     */
    public final Matrix3 transformationMatrix;

    /**
     * The ShaderProgram of LIKO-12.
     */
    public final ShaderProgram effectsShader;
    /**
     * The ShaderProgram for the drawing pattern stencil.
     */
    public final ShaderProgram stencilShader;
    /**
     * The display shader of LIKO-12.
     * Which remaps the pixels values from color ids into their actual values from the palette.
     */
    public final ShaderProgram displayShader;

    /**
     * The colors palette of LIKO-12's display, stored as an array of floats, for the ease of uploading into the shader.
     */
    private final float[] colorsPalette;
    /**
     * Whether the color palette was modified since the last time the palette was uploaded to the shader.
     */
    private boolean colorsPaletteModified;

    /**
     * Creates a new framebuffer of the desired dimensions.
     *
     * @param width  The width of the framebuffer in pixels.
     * @param height The height of the framebuffer in pixels.
     */
    public GdxFrameBuffer(int width, int height) {
        frameBuffer = new FrameBuffer(RGB888, width, height, false, true);
        frameBuffer.getColorBufferTexture().setFilter(Nearest, Nearest); //Set the scaling filter.

        camera = new OrthographicCamera();
        camera.setToOrtho(true, frameBuffer.getWidth(), frameBuffer.getHeight());

        drawerTexture = createWhitePixelTexture();

        batch = new PolygonSpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        transformationMatrix = new Matrix3();

        effectsShader = newEffectsShader();
        updateTransformationMatrix();
        batch.setShader(effectsShader);

        stencilShader = new ShaderProgram(Gdx.files.internal("vertex-shaders/default-shader.glsl"), Gdx.files.internal("fragment-shaders/stencil-shader.glsl"));
        if (!stencilShader.isCompiled())
            throw new IllegalArgumentException("Error compiling shader: " + stencilShader.getLog());

        drawer = new ShapeDrawer(batch, new TextureRegion(drawerTexture));
        drawer.setDefaultSnap(true);

        offsets = new GdxOffsets(); //TODO: Automatic offsets detection.

        colorsPalette = loadColorsPaletteFromImage(Gdx.files.internal("palette.png"));
        displayShader = newDisplayShader();
        updateDisplayShaderPalette();

        float[] remappingPalette = new float[16];
        for (int colorId = 0; colorId < 16; colorId++) {
            remappingPalette[colorId] = colorId / 15.0f;
        }

        effectsShader.bind();
        effectsShader.setUniform1fv("u_remapping", remappingPalette, 0, 16);
    }

    /**
     * Creates a 1x1 #FFFFFFFF texture.
     *
     * @return The created texture.
     */
    private static Texture createWhitePixelTexture() {
        Pixmap pixmap = new Pixmap(1, 1, RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    /**
     * Loads a color palette from an image file.
     *
     * @param file The {@code FileHandle}.
     * @return A floats array containing the colors extracted from the image.
     */
    private static float[] loadColorsPaletteFromImage(FileHandle file) {
        Pixmap pixmap = new Pixmap(file);
        float[] colorsPalette = new float[pixmap.getWidth() * pixmap.getHeight() * 4];

        for (int colorId = 0; colorId < pixmap.getWidth() * pixmap.getHeight(); colorId++) {
            int colorRGBA = pixmap.getPixel(colorId % pixmap.getWidth(), colorId / pixmap.getWidth());
            colorsPalette[colorId * 4] = ((colorRGBA & 0xff000000) >>> 24) / 255f; //color.r
            colorsPalette[colorId * 4 + 1] = ((colorRGBA & 0x00ff0000) >>> 16) / 255f; //color.g
            colorsPalette[colorId * 4 + 2] = ((colorRGBA & 0x0000ff00) >>> 8) / 255f; //color.b
            colorsPalette[colorId * 4 + 3] = 1; //color.a
        }

        pixmap.dispose();
        return colorsPalette;
    }

    private static ShaderProgram newEffectsShader() {
        ShaderProgram effectsShader = new ShaderProgram(Gdx.files.internal("vertex-shaders/matrix-shader.glsl"), Gdx.files.internal("fragment-shaders/effects-shader.glsl"));
        if (!effectsShader.isCompiled()) {
            Gdx.app.error("GLSL", "Failed to compile fragment-shaders/effects-shader.glsl: " + effectsShader.getLog());
            effectsShader.dispose();

            effectsShader = new ShaderProgram(Gdx.files.internal("vertex-shaders/matrix-shader.glsl"), Gdx.files.internal("fragment-shaders/effects-fallback-shader.glsl"));
            if (!effectsShader.isCompiled())
                throw new IllegalArgumentException("Error compiling shader: " + effectsShader.getLog());
        }

        return effectsShader;
    }

    private static ShaderProgram newDisplayShader() {
        ShaderProgram displayShader = new ShaderProgram(Gdx.files.internal("vertex-shaders/default-shader.glsl"), Gdx.files.internal("fragment-shaders/display-shader.glsl"));
        if (!displayShader.isCompiled()) {
            Gdx.app.error("GLSL", "Failed to compile fragment-shaders/display-shader.glsl: " + displayShader.getLog());
            displayShader.dispose();

            displayShader = new ShaderProgram(Gdx.files.internal("vertex-shaders/default-shader.glsl"), Gdx.files.internal("fragment-shaders/display-fallback-shader.glsl"));
            if (!displayShader.isCompiled())
                throw new IllegalArgumentException("Error compiling shader: " + displayShader.getLog());
        }

        return displayShader;
    }

    /**
     * Gets the value of a color in the colors palette, creatine a new Color object.
     *
     * @param colorId The id of the color in the palette.
     * @return The newly created color object.
     */
    public Color getColor(int colorId) {
        return getColor(colorId, new Color());
    }

    /**
     * Gets the value of a color in the colors palette, reusing an existing Color object.
     *
     * @param colorId The id of the color in the palette.
     * @param color   The color object to reuse.
     * @return The reused color object.
     */
    public Color getColor(int colorId, Color color) {
        color.r = colorsPalette[colorId * 4];
        color.g = colorsPalette[colorId * 4 + 1];
        color.b = colorsPalette[colorId * 4 + 2];
        color.a = colorsPalette[colorId * 4 + 3];
        return color;
    }

    /**
     * Sets the value of a color in the colors palette.
     *
     * @param colorId The id of the color in the palette.
     * @param color   The new color.
     */
    public void setColor(int colorId, Color color) {
        colorsPalette[colorId * 4] = color.r;
        colorsPalette[colorId * 4 + 1] = color.g;
        colorsPalette[colorId * 4 + 2] = color.b;
        colorsPalette[colorId * 4 + 3] = color.a;
        colorsPaletteModified = true;
    }

    /**
     * Binds the LIKO-12's framebuffer and allows drawing operations to be made afterwards.
     */
    public void begin() {
        frameBuffer.begin();
        batch.begin();
    }

    /**
     * Unbinds the LIKO-12's framebuffer and drawing operations won't be allowed afterwards.
     */
    public void end() {
        batch.end();
        frameBuffer.end();
    }

    /**
     * Prepares the LIKO-12's framebuffer and display shader for rendering on the screen.
     */
    public void prepare() {
        if (colorsPaletteModified) updateDisplayShaderPalette();
    }

    @Override
    public void dispose() {
        frameBuffer.dispose();
        drawerTexture.dispose();
        batch.dispose();
        effectsShader.dispose();
        stencilShader.dispose();
        displayShader.dispose();
    }

    private void updateDisplayShaderPalette() {
        displayShader.bind();
        displayShader.setUniform4fv("u_palette", colorsPalette, 0, colorsPalette.length);
        colorsPaletteModified = false;
    }

    public void updateTransformationMatrix() {
        effectsShader.bind();
        effectsShader.setUniformMatrix("u_transMatrix", transformationMatrix);
    }
}
