package com.github.rami_sabbagh.liko12.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.rami_sabbagh.liko12.LIKO12;

import static com.badlogic.gdx.Files.FileType.Internal;

public class DesktopLauncher {
    private static final int LIKO_SCALE = 5;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("LIKO-12");
        config.setWindowedMode(192 * LIKO_SCALE, 128 * LIKO_SCALE);
        config.setResizable(true);
        config.setWindowIcon(Internal, "icon-16x16.png");
        new Lwjgl3Application(new LIKO12(), config);
    }
}
