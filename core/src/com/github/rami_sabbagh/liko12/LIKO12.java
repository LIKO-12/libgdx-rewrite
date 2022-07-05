package com.github.rami_sabbagh.liko12;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.rami_sabbagh.liko12.graphics.implementation.GdxFrameBuffer;
import com.github.rami_sabbagh.liko12.graphics.implementation.GdxGraphics;
import com.github.rami_sabbagh.liko12.graphics.interfaces.Image;
import com.github.rami_sabbagh.liko12.graphics.interfaces.ImageData;
import com.github.rami_sabbagh.liko12.graphics.interfaces.PixelFunction;

import static com.badlogic.gdx.Input.Buttons.LEFT;

public class LIKO12 extends ApplicationAdapter {
    Viewport viewport;

    GdxFrameBuffer gdxFrameBuffer;
    GdxGraphics graphics;
    SpriteBatch batch;
    ImageData testImageData;
    Image testImage;

    ImageData patternImageData;
    Image patternImage;

    Vector2 inputVec;
    float[] transformation = {
            1, 0, 1,
            0, 1, 1,
            0, 0, 1
    };
    Matrix3 identityMatrix;
    Matrix3 tempMatrix;
    Matrix3 transMatrix;

    @Override
    public void create() {
        viewport = new FitViewport(192, 128);
        gdxFrameBuffer = new GdxFrameBuffer(192, 128);
        graphics = new GdxGraphics(gdxFrameBuffer);

        batch = new SpriteBatch();
        batch.setShader(gdxFrameBuffer.displayShader);

        testImageData = graphics.newImageData(128, 16);
        testImageData.mapPixels(new PixelFunction() {
            @Override
            public int apply(int x, int y, int color) {
                return (x + y) % 16;
            }
        });
        testImage = testImageData.toImage();

        patternImageData = graphics.newImageData(4, 4);
        patternImageData.mapPixels(new PixelFunction() {
            @Override
            public int apply(int x, int y, int color) {
                return (x + y) % 2;
            }
        });
        patternImage = patternImageData.toImage();

        inputVec = new Vector2();
        identityMatrix = new Matrix3();
        tempMatrix = new Matrix3();
        transMatrix = new Matrix3(transformation);
    }

    void renderBuffer() {
        gdxFrameBuffer.begin();

        graphics.makeColorTransparent(0);

        inputVec.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() - 1);
        viewport.unproject(inputVec);
        inputVec.x = (float) Math.floor(inputVec.x);
        inputVec.y = (float) Math.floor(inputVec.y);

        if (Gdx.input.isButtonJustPressed(LEFT) || Gdx.app.getType() == Application.ApplicationType.Android && Gdx.input.isTouched()) {
            ImageData imageData = graphics.screenshot();

            testImageData.dispose();
            testImage.dispose();

            testImageData = imageData;
            testImage = testImageData.toImage();

            if (Gdx.input.getX() < Gdx.graphics.getWidth()/2) {
                graphics.setMatrix(identityMatrix.getValues());
            } else {
                tempMatrix.set(graphics.getMatrix());
                tempMatrix.mul(transMatrix);
                graphics.setMatrix(tempMatrix.getValues());
            }
        }

        graphics.clear(0);
        graphics.setColor(7);

        graphics.setClip((int) inputVec.x - 16, (int) inputVec.y - 16, 32, 32);
//        graphics.setClip((int) inputVec.x, (int) inputVec.y, 32, 32);

        for (int x = 0; x < graphics.getWidth(); x += 2)
            graphics.line(x, 0, x, graphics.getHeight() - 1, 1);
        for (int y = 0; y < graphics.getHeight(); y += 2)
            graphics.line(0, y, graphics.getWidth() - 1, y, 1);

        graphics.setClip(0, 0, -1, -1);

        graphics.point(0, 0, null);
        graphics.point(191, 0, null);
        graphics.point(0, 127, null);
        graphics.point(191, 127, null);
//        hasan was here

        //graphics.line(2, 2, 8, 2, 8);

        graphics.rectangle(2, 2, 5 * 16 + 2, 5 + 2, true, 0);

        graphics.setDrawingPattern(patternImage);
        for (int i = 0; i < 16; i++) graphics.rectangle(3 + i * 5, 3, 5, 5, true, i);
        graphics.setDrawingPattern(null);

        int scrollOffset = (int) (System.currentTimeMillis()/100 % 32);
        scrollOffset = Math.abs(16 - scrollOffset);
        testImage.draw(2, 10, null, null, null, 0, scrollOffset, null, null);

        graphics.point(inputVec.x, inputVec.y, 15);
//        graphics.circle(inputVec.x, inputVec.y, 8, true, 15);

        //graphics.line(96, 64, inputVec.x, inputVec.y, 8);

        gdxFrameBuffer.end();
    }

    @Override
    public void render() {
        renderBuffer();
        viewport.apply();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gdxFrameBuffer.prepare();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(gdxFrameBuffer.frameBuffer.getColorBufferTexture(), 0, 0, 192, 128, 0, 0, 1, 1);
        batch.end();
    }

    @Override
    public void dispose() {
        gdxFrameBuffer.dispose();
        batch.dispose();

        testImageData.dispose();
        testImage.dispose();

        patternImageData.dispose();
        patternImage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
